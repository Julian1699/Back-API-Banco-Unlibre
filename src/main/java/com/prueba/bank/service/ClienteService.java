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
import java.util.Optional;
/*
@Service
@AllArgsConstructor
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Cliente saveCliente(Cliente cliente){

        Optional<User> existingUser = userRepository.findByUsername(cliente.getEmail());
        User user;

        if (existingUser.isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el email: " + cliente.getEmail());
        } else {

            user = new User();
            user.setUsername(cliente.getEmail());
            user.setPassword(passwordEncoder.encode("customer"));
            user.setCliente(cliente.getId());
            userRepository.save(user);
        }

        cliente.setUser(user);
        Cliente savedCliente = clienteRepository.save(cliente);

        UserRole userRole = new UserRole();
        userRole.setUsername(user.getUsername());
        userRole.setRole("CUSTOMER");
        userRole.setUser(user);
        userRoleRepository.save(userRole);

        return savedCliente;
    }
    public List<Cliente> getAllClientes (){
        return clienteRepository.findAll();
    }

}

 */
@Service
@AllArgsConstructor
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Cliente saveCliente(Cliente cliente) {
        // Verificar si ya existe un usuario con el mismo correo
        userRepository.findByUsername(cliente.getEmail()).ifPresent(u -> {
            throw new RuntimeException("Ya existe un usuario con el email: " + cliente.getEmail());
        });

        // Primero, guardar el Cliente para obtener el id generado
        Cliente savedCliente = clienteRepository.save(cliente);

        // Luego, crear y guardar el User asociado a este Cliente
        User user = new User();
        user.setUsername(cliente.getEmail());
        user.setPassword(passwordEncoder.encode("customer"));
        user.setCliente(savedCliente); // Aquí asumimos que se ha corregido la asociación para referenciar la entidad, no el ID
        userRepository.save(user);

        /*
        // Actualizar el cliente con la referencia al usuario
        savedCliente.setUser(user);
        */
        clienteRepository.save(savedCliente);

        // Guardar el UserRole asociado al usuario
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
