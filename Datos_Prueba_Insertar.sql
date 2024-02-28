INSERT INTO cliente (cedula, nombres, fecha_nacimiento, numero_telefonico, email, direccion, ciudad_residencia, actividad_economica, profesion, tipo_trabajo) VALUES 
('12345678', 'Juan Perez', '1985-10-15', '0998765432', 'juan.perez@example.com', 'Calle Falsa 123', 'Bogotá', 'Comerciante', 'Ingeniero Comercial', 'Independiente'),
('09876541', 'Carlos Bolaños', '1990-05-20', '0987654321', 'carlos.bolaños@example.com', 'Avenida Siempre Viva 456', 'Medellín', 'Servicios', 'Ingeniera Arquitecta', 'Por Contrato'),
('56789014', 'Carlos Estrella', '1978-02-28', '0934567890', 'carlos.estrella@example.com', 'Urbanización El Bosque', 'Cali', 'Educativo', 'Ingeniero Docente', 'Fijo'),
('23456781', 'Ana Torres', '1982-07-14', '0945678901', 'ana.torres@example.com', 'Barrio Central', 'Cartagena', 'Salud', 'Ingeniera Médico', 'Independiente'),
('34567892', 'Roberto Gomez', '1975-12-01', '0956789012', 'roberto.gomez@example.com', 'Conjunto Residencial Sol', 'Barranquilla', 'Tecnología', 'Ingeniero de Sistemas', 'Por Proyecto');
INSERT INTO user (username, password, cliente_id) VALUES 
('juan.perez@example.com', '$2a$10$G5c5OFhkvtJ52yyzCbNqNeENn47j0xcmjY6xe8udNTu97AeuND6dq', 1),
('carlos.bolaños@example.com', '$2a$10$G5c5OFhkvtJ52yyzCbNqNeENn47j0xcmjY6xe8udNTu97AeuND6dq', 2),
('carlos.estrella@example.com', '$2a$10$G5c5OFhkvtJ52yyzCbNqNeENn47j0xcmjY6xe8udNTu97AeuND6dq', 3),
('ana.torres@example.com', '$2a$10$G5c5OFhkvtJ52yyzCbNqNeENn47j0xcmjY6xe8udNTu97AeuND6dq', 4),
('roberto.gomez@example.com', '$2a$10$G5c5OFhkvtJ52yyzCbNqNeENn47j0xcmjY6xe8udNTu97AeuND6dq', 5);
INSERT INTO user_role (username, role) VALUES 
('juan.perez@example.com', 'CUSTOMER'),
('carlos.bolaños@example.com', 'CUSTOMER'),
('carlos.estrella@example.com', 'CUSTOMER'),
('ana.torres@example.com', 'CUSTOMER'),
('roberto.gomez@example.com', 'CUSTOMER');
INSERT INTO cuenta (numero_cuenta, clave_seguridad, estado_cuenta, saldo, tipo_cuenta, cliente_id, fecha_creacion) VALUES 
('123456789012', '$2a$10$FtrYv5kFH0vhNZdW/TC/z.LXAULbfdgGzeGUhT5I9wmqjmJxFizvC', 'ACTIVA', 0.00, 'AHORROS', 1, CURRENT_TIMESTAMP),
('987654321098', '$2a$10$FtrYv5kFH0vhNZdW/TC/z.LXAULbfdgGzeGUhT5I9wmqjmJxFizvC', 'ACTIVA', 0.00, 'CORRIENTE', 2, CURRENT_TIMESTAMP),
('456789123456', '$2a$10$FtrYv5kFH0vhNZdW/TC/z.LXAULbfdgGzeGUhT5I9wmqjmJxFizvC', 'ACTIVA', 0.00, 'AHORROS', 3, CURRENT_TIMESTAMP),
('654321987654', '$2a$10$FtrYv5kFH0vhNZdW/TC/z.LXAULbfdgGzeGUhT5I9wmqjmJxFizvC', 'ACTIVA', 0.00, 'AHORROS', 4, CURRENT_TIMESTAMP),
('789123456789', '$2a$10$FtrYv5kFH0vhNZdW/TC/z.LXAULbfdgGzeGUhT5I9wmqjmJxFizvC', 'ACTIVA', 0.00, 'CORRIENTE', 5, CURRENT_TIMESTAMP);
