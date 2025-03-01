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

Recibe y devuelve, en caso de ser necesario, un ProductDto o una List de ProductDto.
```json
{
    "name": "producto",
    "descripcion": "descripcion del producto",
    "price": 100.0,
    "quantity": 4,
}
```

## Instrucciones
1. Clonar el repositorio o descargar el .zip del proyecto
2. Tener instalado Java 17 y Maven
3. Abrir en IDE de preferencia (ej: IntelliJ)
4. Ejecutar desde el IDE
5. Ir a http://localhost:8080/swagger-ui/index.html para acceder desde swagger, o usar otra herramienta de preferencia

## Endpoints y cURLs
* Crear Product - http://localhost:8080/product/create
```curl
curl --location 'http://localhost:8080/product/create' \
--header 'Content-Type: application/json' \
--data '{
  "name": "producto 2",
  "description": "2do producto",
  "price": 200.0,
  "quantity": 2
}'
```

* Actualizar Product - http://localhost:8080/product/update
```curl
curl --location --request PUT 'http://localhost:8080/product/update' \
--header 'Content-Type: application/json' \
--data '{
  "id": 1,
  "name": "agua",
  "description": "desc",
  "price": 100.0,
  "quantity": 2
}'
```

* Traer Product por ID - http://localhost:8080/product/read/getById/{id}
```curl
curl --location 'http://localhost:8080/product/read/getById/1'
```

* Traer todos los Products http://localhost:8080/product/read/getAll
```curl
curl --location 'http://localhost:8080/product/read/getAll'
```

* Eliminar Product por ID - http://localhost:8080/product/delete/{id}
```curl
curl --location --request DELETE 'http://localhost:8080/product/delete/1'
```
