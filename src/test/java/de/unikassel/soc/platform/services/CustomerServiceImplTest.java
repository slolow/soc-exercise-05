package de.unikassel.soc.platform.services;

import de.unikassel.soc.platform.domain.Customer;
import de.unikassel.soc.platform.repos.CustomerRepo;
import de.unikassel.soc.platform.web.mappers.CustomerMapper;
import de.unikassel.soc.platform.web.mappers.CustomerMapperImpl;
import de.unikassel.soc.platform.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    static CustomerMapper mapper;
    CustomerServiceImpl customerService;
    UUID uuid;

    @Mock
    CustomerRepo repo;

    @BeforeAll
    static void beforeAll() {
        mapper = new CustomerMapperImpl();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(repo, mapper);
        uuid = UUID.randomUUID();
    }

    @Test
    void getCustomerByIdError() {
        //UUID uuid = UUID.randomUUID();
        assertThrows(NoSuchElementException.class, () -> customerService.getCustomerById(uuid));
    }

    @Test
    void getCustomerById() {
        //UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "Test", new ArrayList<>());
        when(repo.findById(uuid)).thenReturn(Optional.of(customer));
        assertEquals(customer.getName(), customerService.getCustomerById(uuid).getName());
        verify(repo, times(1)).findById(uuid);
    }

    @Test
    void getCustomerByName() {
        List<Customer> customers = new ArrayList<>();
        List<CustomerDto> customersDto = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            UUID uuid = UUID.randomUUID();
            Customer customer = new Customer(uuid, "Hans", new ArrayList<>());
            customers.add(customer);
            customersDto.add(mapper.customerToCustomerDto(customer));
        }
        String customerName = customers.get(0).getName();
        when(repo.findByName(customerName)).thenReturn(customers);
        assertEquals(customersDto, customerService.getCustomersByName("Hans"));
        verify(repo, times(1)).findByName(customerName);
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto = new CustomerDto(uuid, "Hans", new ArrayList<>());
        customerService.updateCustomer(uuid, customerDto);
        verify(repo, times(1)).save(mapper.customerDtoToCustomer(customerDto));
    }
}