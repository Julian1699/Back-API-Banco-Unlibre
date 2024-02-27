package com.prueba.bank.controller;

import com.prueba.bank.config.JwtUtil;
import com.prueba.bank.dto.LoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@Tag(name = "Login", description = "Autenticación de inicio de sesión con JWT")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(
            summary = "Iniciar sesión con tu rol predeterminado",
            description = "Con las credenciales correctas, devuelve un token Bearer para usar en cada solicitud HTTP.",
            tags = {"Login"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario Autenticado"),
            @ApiResponse(responseCode = "400", description = "Solicitud Incorrecta")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

        try {

            UsernamePasswordAuthenticationToken login =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(login);
            System.out.println(authentication.isAuthenticated());
            System.out.println(authentication.getPrincipal());

            String jwt = this.jwtUtil.create(loginDto.getUsername());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            return ResponseEntity.ok().headers(headers).body("Bienvenido usuario autenticado, aquí esta su Bearer Token: " + jwt);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación: " + e.getMessage());
        }
    }
}


