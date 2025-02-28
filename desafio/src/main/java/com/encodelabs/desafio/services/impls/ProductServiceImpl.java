package com.encodelabs.desafio.services.impls;

import com.encodelabs.desafio.controllers.manageExceptions.CustomException;
import com.encodelabs.desafio.dtos.ProductDto;
import com.encodelabs.desafio.entities.Product;
import com.encodelabs.desafio.repositories.ProductRepository;
import com.encodelabs.desafio.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto createProduct(ProductDto dto) {
        Product product = new Product(null, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getQuantity());

        productValidator(product);

        return mapper.map(productRepository.save(product), ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        List<ProductDto> allProductsDto = new ArrayList<>();

        for(Product p : allProducts){
            allProductsDto.add(mapper.map(p, ProductDto.class));
        }

        return allProductsDto;
    }

    @Override
    public ProductDto getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()){
            throw new CustomException("No se encontro Product con ID " + id,
                    HttpStatus.BAD_REQUEST);
        }

        return mapper.map(product.get(), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Product updatedProduct) {
        Optional<Product> optProduct = productRepository.findById(updatedProduct.getId());

        if (optProduct.isEmpty()){
            throw new CustomException("No se pudo actualizar, no se encontro Product existente con ID " + updatedProduct.getId(),
                    HttpStatus.BAD_REQUEST);
        }

        productValidator(updatedProduct);

        return mapper.map(productRepository.save(updatedProduct), ProductDto.class);

    }

    @Override
    public void deleteProductById(Integer id) {
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isEmpty()){
            throw new CustomException("No se pudo eliminar, no se encontro Product existente con ID " + id,
                    HttpStatus.BAD_REQUEST);
        }

        productRepository.deleteById(id);
    }

    private void productValidator(Product product) {
        product.setName(product.getName().trim());
        product.setDescription(product.getDescription().trim());

        if (product.getName().isEmpty()){
            throw new CustomException("El Nombre no puede estar vacio", HttpStatus.BAD_REQUEST);
        }
        if (product.getName().length() > 255){
            throw new CustomException("El Nombre no puede tener mas de 255 caracteres", HttpStatus.BAD_REQUEST);
        }
        if (productRepository.findByName(product.getName()).isPresent()){
            throw new CustomException("Ya existe Producto con nombre: " + product.getName(), HttpStatus.BAD_REQUEST);
        }
        if (product.getDescription().length() > 1000){
            throw new CustomException("La Descripcion no puede tener mas de 1000 caracteres", HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() < 1){
            throw new CustomException("El Precio debe ser mayor a 1", HttpStatus.BAD_REQUEST);
        }
        if (product.getQuantity() < 0){
            throw new CustomException("La Cantidad debe ser mayor o igual a 0", HttpStatus.BAD_REQUEST);
        }
    }
}
