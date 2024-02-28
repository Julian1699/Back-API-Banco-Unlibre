package com.prueba.bank.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cedula", unique = true)
    private String cedula;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    @Column(name = "numero_telefonico")
    private String numeroTelefonico;
    @Column(name = "email")
    private String email;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "ciudad_residencia")
    private String ciudadResidencia;
    @Column(name = "actividad_economica")
    private String actividadEconomica;
    @Column(name = "profesion")
    private String profesion;
    @Column(name = "tipo_trabajo")
    private String tipoTrabajo;

}
