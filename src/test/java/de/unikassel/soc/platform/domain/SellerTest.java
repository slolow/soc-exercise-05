package de.unikassel.soc.platform.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellerTest {

    Seller seller;

    @BeforeEach
    void setUp() { seller = new Seller(); }

    @Test
    void getName() {
        seller.setName("Youssoufa Moukoko");
        assertEquals("Youssoufa Moukoko", seller.getName());
    }
}