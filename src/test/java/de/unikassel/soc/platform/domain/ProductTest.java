package de.unikassel.soc.platform.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void getPrice() {
        product.setPrice(10.0);
        assertEquals(10.0, product.getPrice());
    }

    @Test
    void getDescription() {
        product.setDescription("nice product");
        assertEquals("nice product", product.getDescription());
    }
}