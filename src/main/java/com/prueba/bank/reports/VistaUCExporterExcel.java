package com.prueba.bank.reports;

import com.prueba.bank.dto.VistaCuentaDeCliente;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.util.List;

public class VistaUCExporterExcel {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<VistaCuentaDeCliente> vistaCuentaClientes;

    public VistaUCExporterExcel(List<VistaCuentaDeCliente> vistaCuentaClientes) {
        this.vistaCuentaClientes = vistaCuentaClientes;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Clientes y Cuentas");

        Row row = sheet.createRow(0);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Cédula", style);
        createCell(row, 1, "Nombres", style);
        createCell(row, 2, "Número Telefónico", style);
        createCell(row, 3, "Número Cuenta", style);
        createCell(row, 4, "Tipo Cuenta", style);
        createCell(row, 5, "Estado Cuenta", style);
        createCell(row, 6, "Saldo", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value ? "Sí" : "No");
        } else if (value instanceof Double || value instanceof Integer) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (VistaCuentaDeCliente vista : vistaCuentaClientes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, vista.getCedula(), style);
            createCell(row, columnCount++, vista.getNombres(), style);
            createCell(row, columnCount++, vista.getNumeroTelefonico(), style);
            createCell(row, columnCount++, vista.getNumeroCuenta(), style);
            createCell(row, columnCount++, vista.getTipoCuenta(), style);
            createCell(row, columnCount++, vista.getEstadoCuenta(), style);
            createCell(row, columnCount++, vista.getSaldo(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}

