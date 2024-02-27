package com.prueba.bank.domain;

import com.prueba.bank.util.EstadoCuenta;
import com.prueba.bank.util.TipoCuenta;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_cuenta", unique = true)
    private String numeroCuenta;
    @Column(name = "clave_seguridad")
    private String claveSeguridad;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta")
    private EstadoCuenta estadoCuenta;
    @Column(name = "saldo")
    private double saldo;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;
    @CreationTimestamp
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
