package de.unikassel.soc.platform.repos;

import de.unikassel.soc.platform.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepoIntegration {

    @Autowired
    ProductRepo productRepo;

    @Test
    void findByPriceBetween() {
        double from = 10.0;
        double to = 20.0;
        productRepo.saveAll(List.of(
                new Product(UUID.randomUUID(), "Knife", "pointed", from, "Euro"),
                new Product(UUID.randomUUID(), "Knife", "pointed", to, "Euro"),
                new Product(UUID.randomUUID(), "Knife", "pointed", 5.0, "Euro")
        ));

        assertEquals(2, productRepo.findByPriceBetween(from, to).size());
    }
}