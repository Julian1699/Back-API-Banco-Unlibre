package com.prueba.bank.service;

import com.prueba.bank.domain.Cliente;
import com.prueba.bank.domain.Cuenta;
import com.prueba.bank.repository.ClienteRepository;
import com.prueba.bank.repository.CuentaRepository;
import com.prueba.bank.util.EstadoCuenta;
import com.prueba.bank.util.TipoCuenta;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
public class CuentaService {

    @Autowired
    private final CuentaRepository cuentaRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder; // Asegúrate de tener un constructor o @Autowired para esto

    public Cuenta addCuenta(Long clienteId, TipoCuenta tipoCuenta, String claveSeguridad) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + clienteId));

        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(cliente);
        cuenta.setNumeroCuenta(generarNumeroCuenta());
        cuenta.setClaveSeguridad(passwordEncoder.encode(claveSeguridad)); // Encriptar la clave proporcionada
        cuenta.setEstadoCuenta(EstadoCuenta.ACTIVA);
        cuenta.setSaldo(0.0);
        cuenta.setTipoCuenta(tipoCuenta);

        return cuentaRepository.save(cuenta);
    }
/*
    public Cuenta addCuenta(Long clienteId, TipoCuenta tipoCuenta) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente not found with id: " + clienteId));

        Cuenta cuenta = new Cuenta();
        cuenta.setCliente(cliente);
        cuenta.setNumeroCuenta(generarNumeroCuenta());
        cuenta.setClaveSeguridad(generarClaveSeguridad());
        cuenta.setEstadoCuenta(EstadoCuenta.ACTIVA); // Ajustado para usar el enumerado EstadoCuenta
        cuenta.setSaldo(0.0);
        cuenta.setTipoCuenta(tipoCuenta);

        return cuentaRepository.save(cuenta);
    }
    private String generarClaveSeguridad() {
        Random random = new Random();
        StringBuilder claveSeguridad = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            claveSeguridad.append(digit);
        }
        // Encriptar la clave de seguridad antes de retornarla
        return passwordEncoder.encode(claveSeguridad.toString());
    }

 */
    private String generarNumeroCuenta() {
        Random random = new Random();
        StringBuilder numeroCuenta = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            numeroCuenta.append(random.nextInt(10));
        }
        return numeroCuenta.toString();
    }
    public List <Cuenta> getAllCuentas (){
        return cuentaRepository.findAll();
    }
    public Cuenta updateCuenta(Long id, Cuenta cuenta) {
        Optional<Cuenta> optionalCuenta = cuentaRepository.findById(id);

        if (optionalCuenta.isPresent()) {
            Cuenta cuentaExistente = optionalCuenta.get();

            cuentaExistente.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaExistente.setClaveSeguridad(cuenta.getClaveSeguridad());
            cuentaExistente.setEstadoCuenta(cuenta.getEstadoCuenta());
            cuentaExistente.setSaldo(cuenta.getSaldo());

            return cuentaRepository.save(cuentaExistente);
        } else {
            throw new EntityNotFoundException("Cuenta not found with id: " + id);
        }
    }
    public void deleteCuenta(Long id) {
        Optional<Cuenta> optionalProduct = cuentaRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Cuenta cuenta = optionalProduct.get();
            cuentaRepository.delete(cuenta);
        } else {
            throw new EntityNotFoundException("Cuenta not found with id: " + id);
        }
    }
    public Cuenta findById(Long id) {
        Optional<Cuenta> optionalProduct = cuentaRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new EntityNotFoundException("Cuenta not found with id: " + id));
    }
    public Cuenta findAllChars(String search){
        List<Cuenta> searchAll = cuentaRepository.findAllBySearch(search);
        if (searchAll.isEmpty()) {
            throw new EntityNotFoundException("Cuenta not found with: " + search);
        }
        return searchAll.get(0);
    }

}