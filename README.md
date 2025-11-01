#  Tarea 2:  CRUD básico de productos

## Iniciar y detener la base de datos

 - Para iniciar la base de datos: `docker compose up -d`
 - Para verificar que la base de datos se ha iniciado correctamente: `docker compose ps`
 - Para detener la base de datos: `docker compose down`

![img.png](screenshots/up_down_database.png)

---

# CRUD - Productos

## Crear productos
![img.png](screenshots/create_product.png)

## Verificar que los datos se han ingresado correctamente
![img.png](screenshots/verification-query-products.png)

## Listar productos
![img.png](screenshots/list_products.png)

## Listar productos y filtrar por nombre
![img.png](screenshots/list_product_by_name.png)

## Obtener productos por id
![img.png](screenshots/get_product_by_id.png)

## Actualizar productos
![img.png](screenshots/update_product.png)

## Eliminar productos
![img.png](screenshots/delete_product.png)

---

# Docker corriendo
![img.png](screenshots/docker-compose-up.png)

---

---
# CRUD - Categorias

## Crear categorias
![img.png](screenshots/create_category.png)
## Verificar que los datos se han ingresado correctamente
![img.png](screenshots/verification-query-categories.png)
## Listar categorias
![img.png](screenshots/list-categories.png)
## Listar categorias y filtrar por nombre
![img.png](screenshots/list-categories-by-name.png)
## Obtener categorias por id
![img.png](screenshots/get-category-by-id.png)
## Actualizar categorias
![img.png](screenshots/update-category.png)
## Error al actualizar categoria nombre duplicado
![img.png](screenshots/update-category-error-duplicated-name.png)
## Eliminar categorias
![img.png](screenshots/delete-category.png)
## Eliminar categorias error tiene productos
![img.png](screenshots/delete-category-error-has-products.png)
---
## Obtener productos de una categoria
![img.png](screenshots/get-products-by-category.png)
## Obtener productos de una categoria, error categoria no encontrada
![img.png](screenshots/get-product-by-category-error-category-notfound.png)
## Obtener productos de una categoria, categoria sin productos
![img.png](screenshots/get-product-by-category-has-not-products.png)

---

# Manejo de Errores

## 400 Bad Request
Se produce cuando hay errores de validación o parámetros inválidos.  
**Ejemplo:** falta un campo obligatorio o el formato de un valor es incorrecto.

## 404 Not Found
Nos dice que el registro solicitado no existe.  
**Ejemplo:** se intenta acceder a un producto o categoría que no está registrada.

## 409 Conflict
Ocurre cuando existe un conflicto de integridad en los datos.  
**Ejemplo:** se intenta crear una categoría o producto que ya existe.

## Formato de respuesta de error

```json
{
  "timestamp": "2025-10-31T22:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El nombre del producto es obligatorio",
  "path": "/api/products"
}