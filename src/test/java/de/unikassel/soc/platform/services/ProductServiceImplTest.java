package de.unikassel.soc.platform.services;

import de.unikassel.soc.platform.domain.Product;
import de.unikassel.soc.platform.repos.ProductRepo;
import de.unikassel.soc.platform.web.mappers.ProductMapper;
import de.unikassel.soc.platform.web.mappers.ProductMapperImpl;
import de.unikassel.soc.platform.web.model.ProductDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    static ProductMapper mapper;
    ProductServiceImpl productService;

    @Mock
    ProductRepo repo;

    @BeforeAll
    static void beforeAll() { mapper = new ProductMapperImpl(); }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(repo, mapper);
    }

    @Test
    void getProductsByPriceBetween() {
        Double from = 10.0;
        Double to = 20.0;
        List<Product> products = new ArrayList<>();
        List<ProductDto> productsDto = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            UUID uuid = UUID.randomUUID();
            Product product = new Product(uuid, "Iphone", "description", 15.0, "Euro");
            products.add(product);
            productsDto.add(mapper.productToProductDto(product));
        }

        when(repo.findByPriceBetween(from, to)).thenReturn(products);
        assertEquals(productsDto, productService.getProductsByPriceBetween(from, to));
        verify(repo, times(1)).findByPriceBetween(from, to);
    }
}