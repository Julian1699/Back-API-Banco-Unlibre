package com.prueba.bank.controller;

import com.prueba.bank.domain.Cuenta;
import com.prueba.bank.dto.CuentaDto;
import com.prueba.bank.service.CuentaService;
import com.prueba.bank.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/cuenta")
@AllArgsConstructor
@SecurityScheme(name = "Bearer Auth",
        description = "Descripción de autenticación JWT",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "Bearer Auth")
@Tag(name = "API", description = "Todos los verbos Http-Rest")
public class CuentaController {

    @Autowired
    private final CuentaService cuentaService;
    @Operation(
            summary = "Obtener todas las cuentas",
            description = "Devuelve una lista de todas las cuentas disponibles en la base de datos.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cuentas recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontraron cuentas en la base de datos"),
            @ApiResponse(responseCode = "400", description = "Solicitud Incorrecta")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllCuentas() {
        try {
            List<Cuenta> cuentas = cuentaService.getAllCuentas(); // Asumiendo que el método se llama getAllCuentas
            if (cuentas.isEmpty()) {
                return new ResponseEntity<>(Message.EMPTY, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(cuentas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al recuperar las cuentas: " + e.getMessage());
        }
    }
    @Operation(
            summary = "Agregar una nueva cuenta",
            description = "Agrega una nueva cuenta a la base de datos para un cliente existente.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta agregada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud Incorrecta")
    })
    @PostMapping("/post")
    public ResponseEntity<?> addCuenta(@RequestBody CuentaDto cuentaDto) {
        try {
            Cuenta savedCuenta = cuentaService.addCuenta(cuentaDto.getClienteId(), cuentaDto.getTipoCuenta(), cuentaDto.getClaveSeguridad());
            return new ResponseEntity<>(Message.COUNT_SAVED, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la cuenta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(
            summary = "Actualizar una cuenta existente",
            description = "Actualiza los detalles de una cuenta existente en la base de datos.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuenta); // Asegúrate de que el método se llama updateCuenta
            return new ResponseEntity<>(Message.COUNT_UPDATED, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con ID: " + id);
        }
    }
    @Operation(
            summary = "Eliminar una cuenta existente",
            description = "Elimina una cuenta existente en la base de datos. Este proceso es irreversible y debe usarse con precaución.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.deleteCuenta(id); // Asegúrate de que el método se llama deleteCuenta
            return new ResponseEntity<>(Message.COUNT_DELETED, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con ID: " + id);
        }
    }
    @Operation(
            summary = "Obtener una cuenta existente por su ID",
            description = "Recupera los detalles de una cuenta específica en la base de datos utilizando su identificador único (ID). Este endpoint es útil para obtener información detallada de una cuenta individual.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta obtenida con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.findById(id);
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(Message.COUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
    @Operation(
            summary = "Buscar cuentas por número o características asociadas",
            description = "Este endpoint permite buscar cuentas utilizando un término de búsqueda que puede coincidir con cualquier parte del número de cuenta o detalles asociados, facilitando la localización de cuentas específicas dentro del sistema.",
            tags = {"Cuentas"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada y obtenida con éxito"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada con los criterios de búsqueda proporcionados")
    })
    @GetMapping("/search/{search}")
    public ResponseEntity<?> getBySearch(@PathVariable String search) {
        try {
            Cuenta cuenta = cuentaService.findAllChars(search);
            return ResponseEntity.ok(cuenta);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada con la búsqueda: " + search);
        }
    }
}
