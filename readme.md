# Prueba Técnica - GeTechnologiesMx

Este proyecto es una aplicación Spring Boot diseñada como parte de una prueba técnica. A continuación, se describen los pasos para inicializar el proyecto y ejecutarlo, tanto de manera local como utilizando Docker.

---

## pruebatecnica API

Proyecto Spring Boot que expone servicios REST para la gestión de **Personas** y **Facturas** sobre una base de datos H2 embebida. Todas las respuestas utilizan un formato uniforme `ApiResponse<T>`.

### Base URL

```
http://localhost:8080/api
```

### Formato de respuesta (`ApiResponse<T>`)

```json
{
  "status": 200,
  "message": "Descripción de la operación",
  "data": /* payload o null */,
  "error": /* detalle del error o null */
}
```

---

## Endpoints Persona

| Operación          | Método | URL                          | Request Body (JSON)                                                                 | Response Ejemplo                                                                 |
|--------------------|--------|------------------------------|-----------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| Listar todas       | GET    | `/personas`                  | —                                                                                 | ```json { "status":200, "message":"Listado de personas", "data":[ /* Personas */ ], "error":null } ``` |
| Obtener por ID     | GET    | `/personas/{identificacion}` | —                                                                                 | ```json { "status":200, "message":"Persona obtenida: ABC123", "data":{ /* Persona */ }, "error":null } ``` |
| Crear              | POST   | `/personas`                  | ```json { "nombre":"Ana","apellidoPaterno":"Ruiz","apellidoMaterno":"","identificacion":"AAA111" } ``` | ```json { "status":201, "message":"Persona creada: AAA111", "data":{ /* Nueva Persona */ }, "error":null } ``` |
| Eliminar por ID    | DELETE | `/personas/{identificacion}` | —                                                                                 | ```json { "status":200, "message":"Persona eliminada: AAA111", "data":null, "error":null } ``` |

---

## Endpoints Factura

| Operación                  | Método | URL                          | Request Body (JSON)                                               | Response Ejemplo                                                                 |
|----------------------------|--------|------------------------------|-------------------------------------------------------------------|---------------------------------------------------------------------------------|
| Listar facturas de persona | GET    | `/facturas/{identificacion}` | —                                                                 | ```json { "status":200, "message":"Facturas obtenidas para: ABC123", "data":[ /* Facturas */ ], "error":null } ``` |
| Crear factura para persona | POST   | `/facturas/{identificacion}` | ```json { "fecha":"2025-05-09","monto":1500.0 } ```               | ```json { "status":201, "message":"Factura creada para: ABC123", "data":{ /* Factura */ }, "error":null } ``` |

---

## Errores comunes

| Código | Descripción                  | Ejemplo de respuesta                                                                                              |
|--------|------------------------------|-------------------------------------------------------------------------------------------------------------------|
| 400    | Bad Request (campo inválido) | ```json { "status":400, "message":"Identificación obligatoria", "data":null, "error":"El campo 'identificacion' no puede estar vacío" } ``` |
| 404    | Recurso no encontrado        | ```json { "status":404, "message":"Recurso no encontrado", "data":null, "error":"Persona no encontrada: XYZ999" } ``` |
| 409    | Conflict (recurso duplicado) | ```json { "status":409, "message":"Recurso duplicado", "data":null, "error":"Ya existe una persona con identificación: ABC123" } ``` |
| 500    | Error interno del servidor   | ```json { "status":500, "message":"Error inesperado", "data":null, "error":"Detalle de excepción" } ``` |


---

## Logger

El proyecto utiliza **SLF4J** + **Logback**. Se registran operaciones clave en la capa de servicios (creación y eliminación).