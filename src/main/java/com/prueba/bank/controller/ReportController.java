package com.prueba.bank.controller;

import com.prueba.bank.dto.VistaCuentaDeCliente;
import com.prueba.bank.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/reports")
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
public class ReportController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Operation(
            summary = "Generar reporte de cuentas de cliente en PDF",
            description = "Genera un reporte en formato PDF para un cliente específico basado en su cédula.",
            tags = {"Reportes"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo PDF generado con éxito",
                    content = @Content(mediaType = "application/pdf",
                            schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping(value = "/clientes-cuentas/export/pdf/{clienteCedula}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> generarReportePdfClientesCuentas(@PathVariable String clienteCedula) {
        try {
            List<VistaCuentaDeCliente> vistaCuentaClientes = clienteRepository.findCuentaByClienteCedula(clienteCedula);

            if (vistaCuentaClientes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado con la cédula: " + clienteCedula);
            }

            InputStream jasperStream = new ClassPathResource("templates/report/Report3.jasper").getInputStream();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vistaCuentaClientes);

            Map<String, Object> parameters = new HashMap<>();

            VistaCuentaDeCliente cliente = vistaCuentaClientes.get(0);
            parameters.put("tipoCuenta", cliente.getTipoCuenta());
            parameters.put("fechaCreacion", cliente.getFechaCreacion());
            parameters.put("saldo", cliente.getSaldo());
            parameters.put("numeroCuenta", cliente.getNumeroCuenta());
            parameters.put("nombres", cliente.getNombres());
            parameters.put("cedula", cliente.getCedula());
            parameters.put("estado", cliente.getEstadoCuenta());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parameters, dataSource);

            byte[] reportContent = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "certificado-bancario-" + clienteCedula + ".pdf");

            return new ResponseEntity<>(reportContent, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte: " + e.getMessage());
        }
    }

}



