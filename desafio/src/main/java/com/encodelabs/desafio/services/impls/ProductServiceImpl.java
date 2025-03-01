package com.encodelabs.desafio.services.impls;

import com.encodelabs.desafio.dtos.CustomException;
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
import java.util.Objects;
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
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()){
            throw new CustomException("No se encontro Product con ID " + id,
                    HttpStatus.BAD_REQUEST);
        }

        return mapper.map(optionalProduct.get(), ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(updatedProduct.getId());

        if (optionalProduct.isEmpty()){
            throw new CustomException("No se pudo actualizar, no se encontro Product existente con ID " + updatedProduct.getId(),
                    HttpStatus.BAD_REQUEST);
        }

        productValidator(updatedProduct);

        return mapper.map(productRepository.save(updatedProduct), ProductDto.class);

    }

    @Override
    public void deleteProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()){
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

        // verifica si ya existe un Product con el mismo name (name es unique en la entity Product), y si lo encuentra,
        // solo tira la Exception si los IDs son diferentes.
        // Esto verifica 2 casos:
        //  Caso 1 - Metodo createProduct(ProductDto dto)
        //      Verifica q al crear un nuevo Product, el name NO exista en la DB, caso contrario,
        //      devuelve Exception explicando el error.

        //  Caso 2 - Metodo updateProduct(Product product)
        //      Verifica q al actualizar un Product existente, no use el name de OTRO Product existente,
        //      solo puede usar ese name si es la misma Entity Product (osea si tienen el mismo id y name)

        Optional<Product> optionalProduct = productRepository.findByName(product.getName());
        if (optionalProduct.isPresent() && !Objects.equals(product.getId(), optionalProduct.get().getId())){
            throw new CustomException("Ya existe un Producto con nombre: " + product.getName(), HttpStatus.BAD_REQUEST);
        }

        if (product.getDescription().length() > 1000){
            throw new CustomException("La Descripcion no puede tener mas de 1000 caracteres", HttpStatus.BAD_REQUEST);
        }
        if (product.getPrice() < 1){
            throw new CustomException("El Precio debe ser mayor a 0", HttpStatus.BAD_REQUEST);
        }
        if (product.getQuantity() < 0){
            throw new CustomException("La Cantidad debe ser mayor o igual a 0", HttpStatus.BAD_REQUEST);
        }
    }
}
