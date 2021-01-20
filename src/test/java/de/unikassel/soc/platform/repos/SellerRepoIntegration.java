package de.unikassel.soc.platform.repos;

import de.unikassel.soc.platform.domain.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SellerRepoIntegration {

    @Autowired
    SellerRepo sellerRepo;

    @Test
    void deleteById() {
        Seller seller = new Seller();
        seller.setName("Mike");
        seller.setProducts(null);
        sellerRepo.save(seller);
        assertEquals(1, sellerRepo.findAll().size());
        sellerRepo.deleteById(seller.getId());
        assertEquals(0, sellerRepo.findAll().size());
    }
}