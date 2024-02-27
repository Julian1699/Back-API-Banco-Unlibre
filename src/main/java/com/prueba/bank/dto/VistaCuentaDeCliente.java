package com.prueba.bank.dto;
import java.util.Date;

public interface VistaCuentaDeCliente {
    String getCedula();
    String getNombres();
    String getNumeroTelefonico();
    String getNumeroCuenta();
    String getTipoCuenta(); // Enum como String
    String getEstadoCuenta(); // Retorna el estado de la cuenta como String
    double getSaldo();
    Date getFechaCreacion(); // O String, dependiendo de cómo quieras formatear la fecha
}
