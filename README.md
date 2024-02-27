# INFORME: SISTEMA DE GESTIÓN DE CUENTAS DE AHORRO DIGITAL

## INTRODUCCIÓN

El sistema de gestión de cuentas de ahorro digital que se presenta ofrece una solución integral diseñada para facilitar a los clientes del Banco Unilibre la apertura y consulta de cuentas bancarias. Esto incluye cambios de estado y la administración de cuentas de manera digital. Este sistema no solo simplifica el proceso de registro y gestión para los clientes, sino que también brinda a la administración de la entidad financiera herramientas robustas y seguras para el manejo eficiente de las cuentas y la información de los clientes.

## AUTENTICACIÓN Y AUTORIZACIÓN

# Login - Autenticación

Los usuarios se autentican a través del endpoint `/api/v1/auth/login`, ingresando sus credenciales. Se hace uso de JWT (JSON Web Tokens) para asegurar la sesión del usuario, garantizando así la protección de las credenciales y la integridad de la sesión. En caso de que el cliente no tenga usuario y contraseña, debe registrarse proporcionando sus datos de la siguiente manera:

# Registro de Cliente

Para los nuevos clientes sin cuenta, ofrecemos un proceso de registro a través del endpoint `/api/v1/cliente/post`. Durante este proceso, se genera automáticamente un usuario y una contraseña (establecida por defecto como "customer") para el cliente.

Para realizar un registro eficaz, es necesario que el cliente proporcione la siguiente información:

- Cédula
- Nombres
- Fecha de nacimiento
- Número telefónico
- Email
- Dirección
- Ciudad de residencia
- Actividad económica: profesión
- Tipo de trabajo (Empleado, Independiente)
  
## Endpoint para realizar el registro

## http://localhost:8080/api/v1/cliente/post

Ejemplo de solicitud de registro:

```json
{
  "cedula": "123456",
  "nombres": "Ricardo Milos",
  "fechaNacimiento": "1999-03-02",
  "numeroTelefonico": "3001234567",
  "email": "ricardo.milos@gmail.com",
  "direccion": "Calle 123, Ciudad XYZ",
  "ciudadResidencia": "Ciudad XYZ",
  "actividadEconomica": "Ingeniero de Sistemas",
  "profesion": "Ingeniero",
  "tipoTrabajo": "Empleado"
}
```
## Endpoint para realizar el login 

## http://localhost:8080/api/v1/auth/login

Ejemplo de solicitud de login:

```json
{
  "username": "ricardo.milos@gmail.com",
  "password": "customer"
}
```
### Roles y Autorización con Spring Security

El sistema establece dos roles principales: ADMIN y CUSTOMER. Mientras el ADMIN goza de plenos derechos CRUD sobre clientes y cuentas, el CUSTOMER puede realizar operaciones de consulta y registro, evidenciando una clara administración de responsabilidades y acceso.

# GESTIÓN DE CUENTAS

Creación y Administración

La creación de una nueva cuenta se realiza a través del endpoint /api/v1/cuenta/post, lo cual crea una nueva cuenta digital y a su vez genera un número de cuenta de 11 dígitos y permite el registro de una clave de seguridad de 4 dígitos.

## Endpoint para crear una cuenta de ahorro 

## http://localhost:8080/api/v1/cuenta/post

Ejemplo para la creación de cuenta:

```json
{
  "clienteId": 1,
  "tipoCuenta": "AHORROS",
  "claveSeguridad": "1234"
}
```

### REPORTES AVANZADOS

# Generación de Reportes

El sistema brinda soporte completo para la generación de reportes en formatos PDF y Excel, permitiendo a los usuarios descargar documentos detallados que abarcan desde información general de cuentas hasta transacciones financieras específicas. Los siguientes endpoints están disponibles para la generación de estos reportes:

# Exportaciones en PDF

•	Certificado de Cuenta en PDF: Genera un certificado detallado para una cuenta específica, incluyendo información crítica como el tipo de cuenta, número de cuenta, fecha de apertura, estado actual y saldo disponible. Este documento es crucial para propósitos de verificación y auditoría.

## Endpoint para generar certificado PDF de cuenta de ahorros.

## http://localhost:8080/api/v1/reports/clientes-cuentas/export/pdf/{clienteCedula}

Ejemplo, reporte en PDF generado:

 ![image](https://github.com/Julian1699/Back-API-Banco-Unlibre/assets/114323630/45e5565c-8a79-4ccb-be36-4e188dd1c01d)

# Exportaciones en Excel

•	Generar Reporte de Clientes en Excel: Proporciona un resumen de todos los clientes registrados en el sistema.

•	Método y Ruta: GET /api/v1/cliente/export/excel

•	Generar Reporte de Clientes y Cuentas en Excel: Ofrece una vista combinada de clientes y sus cuentas asociadas, ideal para análisis financiero y administrativo.

•	Método y Ruta: GET /api/v1/cliente/clientes-cuentas/export/excel

### Descripción Adicional
Cada uno de estos endpoints ha sido diseñado con la flexibilidad y precisión necesarias para satisfacer diversas necesidades de informes financieros, desde la generación de reportes generales de todos los clientes hasta informes detallados de cuentas individuales, asegurando que la información relevante esté disponible de manera eficiente y segura.

### TECNOLOGÍA Y SEGURIDAD
El sistema está construido sobre Spring Boot, aprovechando la robustez de Spring Security y JWT para garantizar la seguridad. La generación de reportes se realiza mediante JasperReports, facilitando una presentación detallada y profesional de los datos financieros.

### CONCLUSIÓN
Este sistema de gestión de cuentas de ahorro digital representa una solución completa y avanzada, dirigida a modernizar y simplificar las operaciones bancarias tanto para clientes como para administradores. La implementación de tecnologías de vanguardia asegura un alto nivel de seguridad y una experiencia de usuario optimizada, marcando un hito en la gestión financiera digital.

