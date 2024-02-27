package com.prueba.bank.util;

public enum EstadoCuenta {
    ACTIVA, // Representa una cuenta que está en uso activo
    BLOQUEADA, // Representa una cuenta que ha sido bloqueada por algún motivo, como seguridad
    INACTIVA, // Representa una cuenta que no ha sido utilizada durante un período extendido
    PENDIENTE, // Representa una cuenta que está en proceso de ser aprobada o activada
    CERRADA // Representa una cuenta que ha sido cerrada definitivamente

}
