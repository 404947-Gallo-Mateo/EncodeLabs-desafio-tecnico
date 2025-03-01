package com.encodelabs.desafio.controllers;

import com.encodelabs.desafio.dtos.ProductDto;
import com.encodelabs.desafio.entities.Product;
import com.encodelabs.desafio.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = " Crear y Guardar un nuevo Product",
            description = """
                    Object: ProductDto
                    
                    | name: required, 255 char max.
                    
                    | description: optional, 1000 char max.
                    
                    | price: required, greater than 0.
                    
                    | quantity: required, greater or equal than 0.
                    """
    )
    @PostMapping("/create")
    public ResponseEntity<ProductDto> postProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @Operation(
            summary = "Traer todos los Products"
    )
    @GetMapping("/read/getAll")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Traer el Product especificado por ID"
    )
    @GetMapping("/read/getById/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
            summary = "Actualizar un Product existente",
            description = """
                    Object: Product
                    
                    | id: required, greater than 0.
                    
                    | name: required, 255 char max.
                    
                    | description: optional, 1000 char max.
                    
                    | price: required, greater than 0.
                    
                    | quantity: required, greater or equal than 0
                    """
    )
    @PutMapping("/update")
    public ResponseEntity<ProductDto> putProduct(@RequestBody Product updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(updatedProduct));
    }

    @Operation(
            summary = "Eliminar un Product existente"
    )
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Integer id) {
         productService.deleteProductById(id);
    }
}
