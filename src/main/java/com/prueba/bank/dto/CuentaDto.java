package com.prueba.bank.dto;

import com.prueba.bank.util.TipoCuenta;
import lombok.Data;

@Data
public class CuentaDto {

    private Long clienteId;
    private TipoCuenta tipoCuenta;
    private String claveSeguridad;

}
