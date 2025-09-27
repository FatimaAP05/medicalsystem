
# MedicalSystem

Sistema de gestión de atenciones médicas desarrollado con Spring Boot 3.5.6 y Java 17.

## Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Ejecución del proyecto](#ejecución-del-proyecto)
- [Configuración de variables de entorno](#configuración-de-variables-de-entorno)
- [Inicialización de la base de datos](#inicialización-de-la-base-de-datos)
- [Arquitectura](#arquitectura)
- [Endpoints](#endpoints)
- [Consultas personalizadas](#consultas-personalizadas)
- [Seguridad](#seguridad)
- [Buenas prácticas](#buenas-prácticas)

## Descripción

MedicalSystem es un sistema que permite gestionar atenciones médicas, controlando pacientes, médicos, especialidades y el historial de atención. Implementa un CRUD completo, validaciones, seguridad con JWT y consultas personalizadas.

## Requisitos

- Java 17
- Maven 3.x
- PostgreSQL 14+
- IDE recomendado: IntelliJ IDEA, VSCode, Eclipse

## Ejecución del proyecto

```sh
mvn spring-boot:run
```

La aplicación correrá por defecto en [http://localhost:8080](http://localhost:8080).

## Configuración de variables de entorno

Se pueden definir en `src/main/resources/application.properties` o como variables del sistema:

| Variable    | Descripción                                 | Ejemplo                                    |
|-------------|---------------------------------------------|--------------------------------------------|
| DB_URL      | URL de conexión a la base de datos          | jdbc:postgresql://localhost:5432/medicaldb |
| DB_USERNAME | Usuario de la base de datos                 | postgres                                   |
| DB_PASSWORD | Contraseña de la base de datos              | 1234                                       |
| JWT_SECRET  | Clave secreta para generar y validar tokens | miClaveSecreta123                          |

## Inicialización de la base de datos

Coloca tu script en `src/main/resources/schema.sql`.

Ejemplo:

```sql
CREATE TABLE persona (...);
CREATE TABLE usuario (...);
CREATE TABLE paciente (...);
CREATE TABLE empleado (...);
CREATE TABLE especialidad (...);
CREATE TABLE medico_especialidad (...);
CREATE TABLE atencion (...);
```

## Arquitectura

- **Modelo MVC**: Separación de entidades, DTOs, servicios, controladores y repositorios.
- **DTOs**: Se usan para mantener la integridad de las entidades y desacoplar la lógica de negocio de la presentación.
- **Servicios**: Contienen la lógica de negocio y validaciones.
- **Controladores**: Exponen los endpoints REST.
- **Security**: JWT con roles (ADMIN, MEDICO, PACIENTE).
- **Manejo global de errores**: Implementado con `@ControllerAdvice`.

## Endpoints

### Autenticación

**POST** `/api/auth/login`

Body:

```json
{
	"usuario": "admin",
	"password": "admin123"
}
```

### Atenciones (ADMIN)

**GET** `/api/atenciones`

Header:

```
Authorization: Bearer <token>
```

### Crear atención (ADMIN / MEDICO)

**POST** `/api/atenciones`

Body:

```json
{
	"fecha": "2025-09-27T12:30:00",
	"motivo": "Consulta general",
	"pacienteId": 1,
	"empleadoId": 2,
	"estado": "PENDIENTE"
}
```

Header:

```
Authorization: Bearer <token>
```

### Actualizar atención (ADMIN / MEDICO)

**PUT** `/api/atenciones/{id}`

Body:

```json
{
	"fecha": "2025-09-28T10:00:00",
	"motivo": "Consulta modificada",
	"estado": "PENDIENTE"
}
```

Header:

```
Authorization: Bearer <token>
```

### Eliminar atención (ADMIN)

**DELETE** `/api/atenciones/{id}`

Header:

```
Authorization: Bearer <token>
```

### Ver mis atenciones (PACIENTE)

**GET** `/api/atenciones/mias`

Header:

```
Authorization: Bearer <token>
```

## Consultas personalizadas

### Buscar atenciones por fecha

**GET** `/api/atenciones/buscar-por-fecha?inicio=2025-09-01T00:00:00&fin=2025-09-30T23:59:59`

Header:

```
Authorization: Bearer <token>
```

Descripción: Devuelve todas las atenciones entre las fechas inicio y fin.

### Buscar atenciones por médico

**GET** `/api/atenciones/buscar-por-medico?medicoId=2`

Header:

```
Authorization: Bearer <token>
```

Descripción: Devuelve todas las atenciones asignadas al médico con ID 2.

#### Ejemplo de implementación en repositorio

```java
@Query("SELECT a FROM Atencion a WHERE a.fecha BETWEEN :inicio AND :fin")
List<Atencion> findByFechaBetween(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

@Query("SELECT a FROM Atencion a WHERE a.empleado.id = :medicoId")
List<Atencion> findByMedico(@Param("medicoId") Long medicoId);
```

## Seguridad

- JWT con roles: PACIENTE, MEDICO, ADMIN.
- Protección de rutas según rol.
- Endpoints sensibles (crear, actualizar, eliminar atenciones) requieren autenticación y rol adecuado.

## Buenas prácticas aplicadas

- Separación de responsabilidades: MVC claro y servicios desacoplados.
- Validaciones: Anotaciones `@NotNull`, `@NotBlank` en DTOs.
- DTOs: Evitan exponer entidades directamente.
- Manejo de errores global con `@ControllerAdvice`.
- Documentación: README completo con endpoints y consultas personalizadas.
- Git: Versionado y commits claros.
