package com.encodelabs.desafio.services;

import com.encodelabs.desafio.dtos.ProductDto;
import com.encodelabs.desafio.entities.Product;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Integer id);
    ProductDto updateProduct(Product product);
    void deleteProductById(Integer id);
}
