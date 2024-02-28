package com.prueba.bank.dto;
import java.util.Date;

public interface VistaCuentaDeCliente {
    String getCedula();
    String getNombres();
    String getNumeroTelefonico();
    String getNumeroCuenta();
    String getTipoCuenta(); // Enum como String
    String getEstadoCuenta();
    double getSaldo();
    Date getFechaCreacion();
}
