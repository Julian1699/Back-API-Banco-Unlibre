package com.prueba.bank.controller;

import com.prueba.bank.domain.Cliente;
import com.prueba.bank.dto.VistaCuentaDeCliente;
import com.prueba.bank.reports.ClienteExporterExcel;
import com.prueba.bank.reports.VistaUCExporterExcel;
import com.prueba.bank.service.ClienteService;
import com.prueba.bank.repository.ClienteRepository;
import com.prueba.bank.util.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/cliente")
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
public class ClienteController {

    @Autowired
    private final ClienteService clienteService;
    @Autowired
    private final ClienteRepository clienteRepository;

    @Operation(
            summary = "Obtener todos los Clientes", description = "Devuelve una lista de todos los Clientes en la base de datos.",
            tags = {"Clientes"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes recuperada con éxito"),
            @ApiResponse(responseCode = "404", description = "No se encontraron clientes en la base de datos"),
            @ApiResponse(responseCode = "400", description = "Solicitud Incorrecta")
    })
    @GetMapping("/all")
    public ResponseEntity<?> getAllClientes() {
        try {
            List<Cliente> clientes = clienteService.getAllClientes();
            if (clientes.isEmpty()) {
                return new ResponseEntity<>(Message.EMPTY, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(clientes);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }

    @Operation(
            summary = "Crear un cliente nuevo",
            description = "Agrega un nuevo cliente a la base de datos.",
            tags = {"Clientes","Login"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente agregado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud Incorrecta")
    })
    @PostMapping("/post")
    public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
        try {
            Cliente savedCliente = clienteService.saveCliente(cliente);
            return new ResponseEntity<>(Message.CUSTOMER_SAVED, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el cliente: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "Generar reporte Excel de clientes",
            description = "Genera un reporte en formato Excel de todos los clientes en la base de datos.",
            tags = {"Exportar"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte de clientes generado con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/export/excel")
    public ResponseEntity<?> generarReporteExcelClientes(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=clientes_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);

            List<Cliente> clientes = clienteService.getAllClientes(); // Asegúrate de que este método exista y devuelva una lista de clientes

            ClienteExporterExcel exporterExcel = new ClienteExporterExcel(clientes);
            exporterExcel.export(response);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte de clientes: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    @Operation(
            summary = "Generar reporte Excel de clientes y cuentas",
            description = "Genera un reporte en formato Excel que combina información de clientes y sus cuentas correspondientes.",
            tags = {"Exportar"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte de clientes y cuentas generado con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/clientes-cuentas/export/excel")
    public ResponseEntity<?> generarReporteExcelClientesCuentas(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=clientes_cuentas_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);

            List<VistaCuentaDeCliente> vistaCuentaClientes = clienteRepository.viewReport();

            VistaUCExporterExcel exporterExcel = new VistaUCExporterExcel(vistaCuentaClientes);
            exporterExcel.export(response);

            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el reporte de clientes y cuentas: " + e.getMessage());
        }
    }


}
