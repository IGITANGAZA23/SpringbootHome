package com.igitan.springboothome.controller;

import com.igitan.springboothome.dto.ProductDTO;
import com.igitan.springboothome.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security for this test to focus on controller logic
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService productService;

  private ProductDTO productDTO;

  @BeforeEach
  void setUp() {
    productDTO = new ProductDTO();
    productDTO.setId(1L);
    productDTO.setName("Test Product");
    productDTO.setPrice(100.0);
    productDTO.setStockQuantity(10);
  }

  @Test
  void getAllProducts_ShouldReturnListOfProducts() throws Exception {
    // Arrange
    List<ProductDTO> products = Arrays.asList(productDTO);
    when(productService.getAllProducts()).thenReturn(products);

    // Act & Assert
    mockMvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name").value("Test Product"))
        .andExpect(jsonPath("$[0].price").value(100.0));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productService.getProductById(1L)).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).getProductById(1L);
    }
}
