package com.prueba.bank.service;

import com.prueba.bank.domain.Cliente;
import com.prueba.bank.domain.User;
import com.prueba.bank.domain.UserRole;
import com.prueba.bank.repository.ClienteRepository;
import com.prueba.bank.repository.UserRepository;
import com.prueba.bank.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Cliente saveCliente(Cliente cliente) {

        userRepository.findByUsername(cliente.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Ya existe un usuario con el email: " + cliente.getEmail());
        });

        Cliente savedCliente = clienteRepository.save(cliente);

        User user = new User();
        user.setUsername(cliente.getEmail());
        user.setPassword(passwordEncoder.encode("customer"));
        user.setCliente(savedCliente);
        userRepository.save(user);

        clienteRepository.save(savedCliente);

        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setRole("CUSTOMER");
        userRole.setUser(user);
        userRoleRepository.save(userRole);

        return savedCliente;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
}
