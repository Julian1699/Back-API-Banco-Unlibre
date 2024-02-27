package com.prueba.bank.repository;

import com.prueba.bank.domain.Cliente;
import com.prueba.bank.domain.Cuenta;
import com.prueba.bank.dto.VistaCuentaDeCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(value = "SELECT * FROM cliente WHERE cedula LIKE %:search%", nativeQuery = true)
    Optional<Cliente> findByCedula(@Param("search") String search);
    @Query(value = "SELECT " +
            "cl.cedula AS cedula, " +
            "cl.nombres AS nombres, " +
            "cl.numero_telefonico AS numeroTelefonico, " +
            "cu.numero_cuenta AS numeroCuenta, " +
            "cu.tipo_cuenta AS tipoCuenta, " +
            "cu.estado_cuenta AS estadoCuenta, " +
            "cu.saldo AS saldo, " +
            "cu.fecha_creacion AS fechaCreacion " +
            "FROM cliente cl " +
            "INNER JOIN cuenta cu ON cl.id = cu.cliente_id", nativeQuery = true)
    List<VistaCuentaDeCliente> viewReport();

    @Query(value = "SELECT " +
            "cl.cedula AS cedula, " +
            "cl.nombres AS nombres, " +
            "cl.numero_telefonico AS numeroTelefonico, " +
            "cu.numero_cuenta AS numeroCuenta, " +
            "cu.tipo_cuenta AS tipoCuenta, " +
            "cu.estado_cuenta AS estadoCuenta, " +
            "cu.saldo AS saldo, " +
            "cu.fecha_creacion AS fechaCreacion " +
            "FROM cliente cl INNER JOIN cuenta cu ON cl.id = cu.cliente_id " +
            "WHERE cl.cedula = :clienteCedula", nativeQuery = true)
    List<VistaCuentaDeCliente> findCuentaByClienteCedula(@Param("clienteCedula") String clienteCedula);

}