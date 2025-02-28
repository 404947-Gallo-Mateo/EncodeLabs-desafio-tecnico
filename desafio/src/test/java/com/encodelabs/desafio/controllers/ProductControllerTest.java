package com.encodelabs.desafio.controllers;

import com.encodelabs.desafio.dtos.ProductDto;
import com.encodelabs.desafio.entities.Product;
import com.encodelabs.desafio.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void postProduct_Test_Success() throws Exception {
        ProductDto productDto1 = new ProductDto("agua", "desc", 100.0, 1);

        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto1);

        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("agua"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(1));

    }

    @Test
    void getAllProducts_Test_Success() throws Exception {
        ProductDto productDto1 = new ProductDto("agua", "desc", 100.0, 1);
        ProductDto productDto2 = new ProductDto("galletas", "desc", 200.0, 2);

        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto1); productDtoList.add(productDto2);

        when(productService.getAllProducts()).thenReturn(productDtoList);

        mockMvc.perform(get("/product/read/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("agua"))
                .andExpect(jsonPath("$[0].description").value("desc"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[1].name").value("galletas"))
                .andExpect(jsonPath("$[1].description").value("desc"))
                .andExpect(jsonPath("$[1].price").value(200.0))
                .andExpect(jsonPath("$[1].quantity").value(2));
    }

    @Test
    void getProductById_Test_Success() throws Exception {
        ProductDto productDto1 = new ProductDto("agua", "desc", 100.0, 1);

        when(productService.getProductById(anyInt())).thenReturn(productDto1);

        mockMvc.perform(get("/product/read/getById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("agua"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(1));
    }

    @Test
    void putProduct_Test_Success() throws Exception {
        Product product1 = new Product(1, "sanguchito", "desc", 150.0, 1);
        ProductDto productDto1 = new ProductDto("sanguchito", "desc", 150.0, 1);

        when(productService.updateProduct(any(Product.class))).thenReturn(productDto1);

        mockMvc.perform(put("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sanguchito"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.price").value(150.0))
                .andExpect(jsonPath("$.quantity").value(1));
    }

    @Test
    void deleteProduct_Test_Success() throws Exception {
        Integer idProduct = 1;

        doNothing().when(productService).deleteProductById(idProduct);

        assertDoesNotThrow(() -> productController.deleteProduct(idProduct));

        verify(productService, times(1)).deleteProductById(idProduct);
    }

}
