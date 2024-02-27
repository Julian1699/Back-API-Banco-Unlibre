package com.prueba.bank.reports;

import com.prueba.bank.domain.Cliente;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClienteExporterExcel {

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private List<Cliente> clientes;

    public ClienteExporterExcel(List<Cliente> clientes) {
        this.clientes = clientes;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {

        sheet = workbook.createSheet("Clientes");

        Row row = sheet.createRow(0);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "ID", style);
        createCell(row, 1, "Cédula", style);
        createCell(row, 2, "Nombres", style);
        createCell(row, 3, "Fecha Nacimiento", style);
        createCell(row, 4, "Número Telefónico", style);
        createCell(row, 5, "Email", style);
        createCell(row, 6, "Dirección", style);
        createCell(row, 7, "Ciudad Residencia", style);
        createCell(row, 8, "Actividad Económica", style);
        createCell(row, 9, "Profesión", style);
        createCell(row, 10, "Tipo Trabajo", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue(((LocalDate) value).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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

        for (Cliente cliente : clientes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, cliente.getId(), style);
            createCell(row, columnCount++, cliente.getCedula(), style);
            createCell(row, columnCount++, cliente.getNombres(), style);
            createCell(row, columnCount++, cliente.getFechaNacimiento(), style);
            createCell(row, columnCount++, cliente.getNumeroTelefonico(), style);
            createCell(row, columnCount++, cliente.getEmail(), style);
            createCell(row, columnCount++, cliente.getDireccion(), style);
            createCell(row, columnCount++, cliente.getCiudadResidencia(), style);
            createCell(row, columnCount++, cliente.getActividadEconomica(), style);
            createCell(row, columnCount++, cliente.getProfesion(), style);
            createCell(row, columnCount++, cliente.getTipoTrabajo(), style);
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