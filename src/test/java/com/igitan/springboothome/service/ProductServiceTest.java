package com.igitan.springboothome.service;

import com.igitan.springboothome.dto.ProductDTO;
import com.igitan.springboothome.model.Product;
import com.igitan.springboothome.repository.CategoryRepository;
import com.igitan.springboothome.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private ProductService productService;

  private Product product;
  private ProductDTO productDTO;

  @BeforeEach
  void setUp() {
    product = new Product();
    product.setId(1L);
    product.setName("Test Product");
    product.setPrice(100.0);
    product.setStockQuantity(10);

    productDTO = new ProductDTO();
    productDTO.setId(1L);
    productDTO.setName("Test Product");
    productDTO.setPrice(100.0);
    productDTO.setStockQuantity(10);
  }

  @Test
    void getProductById_WhenProductExists_ShouldReturnProductDTO() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = productService.getProductById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

  @Test
    void getProductById_WhenProductDoesNotExist_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }
}
