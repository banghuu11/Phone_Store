package com.phonestore.app.controller;

import com.phonestore.app.model.Phone;
import com.phonestore.app.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhoneController.class)
class PhoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneService phoneService;

    private Phone testPhone;
    private static final String TEST_PHONE_JSON = "{\"brand\":\"Apple\",\"model\":\"iPhone 15 Pro\",\"price\":999.99,\"description\":\"Latest iPhone\",\"stockQuantity\":50,\"imageUrl\":\"https://example.com/iphone.jpg\"}";

    @BeforeEach
    void setUp() {
        testPhone = new Phone();
        testPhone.setId(1L);
        testPhone.setBrand("Apple");
        testPhone.setModel("iPhone 15 Pro");
        testPhone.setPrice(999.99);
        testPhone.setDescription("Latest iPhone");
        testPhone.setStockQuantity(50);
        testPhone.setImageUrl("https://example.com/iphone.jpg");
    }

    @Test
    void getAllPhones_ReturnsListOfPhones() throws Exception {
        List<Phone> phones = Arrays.asList(testPhone);
        when(phoneService.getAllPhones()).thenReturn(phones);

        mockMvc.perform(get("/api/phones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is("Apple")))
                .andExpect(jsonPath("$[0].model", is("iPhone 15 Pro")));
    }

    @Test
    void getPhoneById_ReturnsPhone() throws Exception {
        when(phoneService.getPhoneById(1L)).thenReturn(testPhone);

        mockMvc.perform(get("/api/phones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is("Apple")))
                .andExpect(jsonPath("$.model", is("iPhone 15 Pro")));
    }

    @Test
    void createPhone_ReturnsCreatedPhone() throws Exception {
        when(phoneService.createPhone(any(Phone.class))).thenReturn(testPhone);

        mockMvc.perform(post("/api/phones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_PHONE_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand", is("Apple")))
                .andExpect(jsonPath("$.model", is("iPhone 15 Pro")));
    }

    @Test
    void updatePhone_ReturnsUpdatedPhone() throws Exception {
        when(phoneService.updatePhone(anyLong(), any(Phone.class))).thenReturn(testPhone);

        mockMvc.perform(put("/api/phones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_PHONE_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is("Apple")));
    }

    @Test
    void deletePhone_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/phones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchByBrand_ReturnsPhones() throws Exception {
        List<Phone> phones = Arrays.asList(testPhone);
        when(phoneService.searchByBrand("Apple")).thenReturn(phones);

        mockMvc.perform(get("/api/phones/search/brand")
                        .param("brand", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand", is("Apple")));
    }

    @Test
    void searchByModel_ReturnsPhones() throws Exception {
        List<Phone> phones = Arrays.asList(testPhone);
        when(phoneService.searchByModel("iPhone 15 Pro")).thenReturn(phones);

        mockMvc.perform(get("/api/phones/search/model")
                        .param("model", "iPhone 15 Pro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].model", is("iPhone 15 Pro")));
    }

    @Test
    void searchByPriceRange_ReturnsPhones() throws Exception {
        List<Phone> phones = Arrays.asList(testPhone);
        when(phoneService.searchByPriceRange(900.0, 1100.0)).thenReturn(phones);

        mockMvc.perform(get("/api/phones/search/price")
                        .param("minPrice", "900.0")
                        .param("maxPrice", "1100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].price", is(999.99)));
    }
}
