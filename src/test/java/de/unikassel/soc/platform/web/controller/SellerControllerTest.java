package de.unikassel.soc.platform.web.controller;

import de.unikassel.soc.platform.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SellerControllerTest {

    SellerController sellerController;
    UUID uuid;

    @Mock
    SellerService sellerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sellerController = new SellerController(sellerService);
        uuid = UUID.randomUUID();
    }

    @Test
    void deleteById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(sellerController).build();
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/seller/{sellerId}", uuid))
                .andExpect(status().isOk());
    }
}