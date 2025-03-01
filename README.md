# Desafío Técnico de EncodeLabs 
## Tecnologías usadas
* Framework: Spring 3.4.3
* Language: Java 17
* Project: Maven
* Packaging: Jar

## Funcionalidad
Ofrece varios endpoints para el CRUD de un Objeto Product.  
```json
{
    "id": 1,
    "name": "producto",
    "descripcion": "descripcion del producto",
    "price": 100.0,
    "quantity": 4,
}
```

Devuelve, en caso de ser necesario un ProductDto o una List de ProductDto.
```json
{
    "name": "producto",
    "descripcion": "descripcion del producto",
    "price": 100.0,
    "quantity": 4,
}
```

## Instrucciones
1. Clonar o descargar .zip del proyecto
2. Abrir en IDE de preferencia (ej: IntelliJ)
3. Ejecutar!
4. Ir a http://localhost:8080/swagger-ui/index.html para acceder desde swagger


## Endpoints
* Actualizar Product - http://localhost:8080/product/update
  
  
* Crear Product - http://localhost:8080/product/create
  
* Traer Product por ID - http://localhost:8080/product/read/getById/{id}

* Traer todos los Products http://localhost:8080/product/read/getAll

* Eliminar Product por ID - http://localhost:8080/product/delete/{id}
