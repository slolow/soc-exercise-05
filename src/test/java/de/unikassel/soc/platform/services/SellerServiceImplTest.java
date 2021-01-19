package de.unikassel.soc.platform.services;

import de.unikassel.soc.platform.repos.SellerRepo;
import de.unikassel.soc.platform.web.mappers.SellerMapper;
import de.unikassel.soc.platform.web.mappers.SellerMapperImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SellerServiceImplTest {

    static SellerMapper mapper;
    SellerServiceImpl sellerService;
    UUID uuid;

    @Mock
    SellerRepo repo;

    @BeforeAll
    static void beforeAll() { mapper = new SellerMapperImpl(); }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sellerService = new SellerServiceImpl(repo, mapper);
        uuid = UUID.randomUUID();
    }

    @Test
    void deleteById() {
        sellerService.deleteById(uuid);
        verify(repo, times(1)).deleteById(uuid);
    }
}