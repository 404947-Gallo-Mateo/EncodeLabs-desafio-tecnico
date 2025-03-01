package com.encodelabs.desafio.services;

import com.encodelabs.desafio.dtos.ProductDto;
import com.encodelabs.desafio.entities.Product;
import com.encodelabs.desafio.repositories.ProductRepository;
import com.encodelabs.desafio.services.impls.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ProductServiceUnitTesting {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;
    @Spy
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_Test_Success(){
        //objetos (q se van a pasar por parametro, o devolver de otros metodos simulados "fakes")
        ProductDto dto = new ProductDto("gaseosa", "desc", 300.0, 3);
        Optional<Product> optionalProduct = Optional.empty();

        //fakes (se simula la respuesta de las capas externas a la capa service)
        when(productRepository.findByName(anyString())).thenReturn(optionalProduct);

        when(productRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //test (se prueba el metodo)
        ProductDto respuesta = productService.createProduct(dto);

        //asserts (se verifica q la respuesta sea la esperada/correcta)
        assertEquals(dto.getName(), respuesta.getName());
        assertEquals(dto.getDescription(), respuesta.getDescription());
        assertEquals(dto.getPrice(), respuesta.getPrice());
        assertEquals(dto.getQuantity(), respuesta.getQuantity());
    }

    @Test
    void getAllProducts_Test_Success(){
//objetos (q se van a pasar por parametro, o devolver de otros metodos simulados "fakes")
        Product product1 = new Product(1, "gaseosa", "gaseosa sabor sandia", 250.0, 3);
        Product product2 = new Product(2, "avena", "desc", 100.0, 1);

        List<Product> productList = new ArrayList<>();
        productList.add(product1); productList.add(product2);

        //fakes (se simula la respuesta de las capas externas a la capa service)
        when(productRepository.findAll()).thenReturn(productList);

        //test (se prueba el metodo)
        List<ProductDto> respuesta = productService.getAllProducts();

        //asserts (se verifica q la respuesta sea la esperada/correcta)
        assertEquals(product1.getName(), respuesta.get(0).getName());
        assertEquals(product1.getDescription(), respuesta.get(0).getDescription());
        assertEquals(product1.getPrice(), respuesta.get(0).getPrice());
        assertEquals(product1.getQuantity(), respuesta.get(0).getQuantity());
        assertEquals(product2.getName(), respuesta.get(1).getName());
        assertEquals(product2.getDescription(), respuesta.get(1).getDescription());
        assertEquals(product2.getPrice(), respuesta.get(1).getPrice());
        assertEquals(product2.getQuantity(), respuesta.get(1).getQuantity());
    }

    @Test
    void getProductById_Test_Success(){
        //objetos (q se van a pasar por parametro, o devolver de otros metodos simulados "fakes")
        Product product1 = new Product(1, "gaseosa", "gaseosa sabor sandia", 250.0, 3);

        //fakes (se simula la respuesta de las capas externas a la capa service)
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product1));

        //test (se prueba el metodo)
        ProductDto respuesta = productService.getProductById(1);

        //asserts (se verifica q la respuesta sea la esperada/correcta)
        assertEquals(product1.getName(), respuesta.getName());
        assertEquals(product1.getDescription(), respuesta.getDescription());
        assertEquals(product1.getPrice(), respuesta.getPrice());
        assertEquals(product1.getQuantity(), respuesta.getQuantity());
    }

    @Test
    void updateProduct_Test_Success(){
        //objetos (q se van a pasar por parametro, o devolver de otros metodos simulados "fakes")
        Product updatedProduct = new Product(1, "gaseosa", "gaseosa sabor sandia", 250.0, 3);
        Optional<Product> optionalProduct = Optional.empty();
        Product oldProduct = new Product(1, "gaseosa", "desc", 300.0, 3);

        //fakes (se simula la respuesta de las capas externas a la capa service)
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(oldProduct));

        when(productRepository.findByName(anyString())).thenReturn(Optional.of(oldProduct));

        when(productRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        //test (se prueba el metodo)
        ProductDto respuesta = productService.updateProduct(updatedProduct);

        //asserts (se verifica q la respuesta sea la esperada/correcta)
        assertEquals(updatedProduct.getName(), respuesta.getName());
        assertEquals(updatedProduct.getDescription(), respuesta.getDescription());
        assertEquals(updatedProduct.getPrice(), respuesta.getPrice());
        assertEquals(updatedProduct.getQuantity(), respuesta.getQuantity());
    }

    @Test
    void deleteProductById_Test_Success(){
        //objetos (q se van a pasar por parametro, o devolver de otros metodos simulados "fakes")
        Integer idProduct = 1;

        Product product = new Product(1, "gaseosa", "desc", 300.0, 3);

        //fakes (se simula la respuesta de las capas externas a la capa service)
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));

        doNothing().when(productRepository).deleteById(idProduct);

        //test (se prueba el metodo)
        assertDoesNotThrow(() -> productService.deleteProductById(idProduct));

        //asserts (se verifica q se llamo al metodo correctamente, y una cant especifica de veces)
        verify(productRepository, times(1)).deleteById(idProduct);
    }
}
