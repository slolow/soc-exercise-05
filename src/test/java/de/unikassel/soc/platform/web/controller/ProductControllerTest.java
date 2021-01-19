package de.unikassel.soc.platform.web.controller;

import de.unikassel.soc.platform.services.ProductService;
import de.unikassel.soc.platform.web.model.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest {

    ProductController productController;
    UUID uuid;

    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController((productService));
        uuid = UUID.randomUUID();
    }

    @Test
    void getProductsByPriceRange() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        Double from = 10.0;
        Double to = 20.0;
        List<ProductDto> productsDto = new ArrayList<>();
        ProductDto productDto = new ProductDto(uuid, "iphone", "nice", 15.0, "Euro");
        productsDto.add(productDto);
        when(productService.getProductsByPriceBetween(from, to)).thenReturn(productsDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/?from=" + from + "&to=" + to))
                .andExpect(status().isOk());
    }
}