package com.prueba.bank.repository;

import com.prueba.bank.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    @Query(value = "SELECT * FROM cuenta WHERE " +
            "CONCAT(numero_cuenta, saldo, estado_cuenta, fecha_creacion) LIKE %:search%", nativeQuery = true)
    List<Cuenta> findAllBySearch(@Param("search") String search);

}