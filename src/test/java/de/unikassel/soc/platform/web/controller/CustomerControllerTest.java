package de.unikassel.soc.platform.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.unikassel.soc.platform.services.CustomerService;
import de.unikassel.soc.platform.web.model.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    CustomerController customerController;
    UUID uuid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(customerService);
        uuid = UUID.randomUUID();
    }

    @Test
    void getCustomer() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        //UUID uuid = UUID.randomUUID();
        CustomerDto customerDto = new CustomerDto(uuid, "Test", null);
        when(customerService.getCustomerById(uuid)).thenReturn(customerDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/" + uuid))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerNotFound() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new MvcExceptionHandler())
                .build();
        //UUID uuid = UUID.randomUUID();
        when(customerService.getCustomerById(uuid)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/" + uuid))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerByName() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        List<CustomerDto> customersDto = new ArrayList<>();
        CustomerDto customerDto = new CustomerDto(uuid, "Harald", null);
        customersDto.add(customerDto);
        when(customerService.getCustomersByName(customerDto.getName())).thenReturn(customersDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/?name=" + customerDto.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void handlePost() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        CustomerDto customerDto = new CustomerDto(uuid, "Harald", null);
        when(customerService.saveNewCustomer(customerDto)).thenReturn(customerDto);

        // send HttpBody as JSON
        final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(customerDto);

        mockMvc.perform(post("/api/v1/customer").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString(uuid.toString())));
    }
}