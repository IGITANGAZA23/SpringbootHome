package com.igitan.springboothome.service;

import com.igitan.springboothome.dto.ProductDTO;
import com.igitan.springboothome.model.Category;
import com.igitan.springboothome.model.Product;
import com.igitan.springboothome.repository.CategoryRepository;
import com.igitan.springboothome.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    return convertToDTO(product);
  }

  @Transactional
  public ProductDTO createProduct(ProductDTO productDTO) {
    Product product = new Product();
    updateProductFromDTO(product, productDTO);
    Product savedProduct = productRepository.save(product);
    return convertToDTO(savedProduct);
  }

  @Transactional
  public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    updateProductFromDTO(product, productDTO);
    Product updatedProduct = productRepository.save(product);
    return convertToDTO(updatedProduct);
  }

  @Transactional
  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new RuntimeException("Product not found with id: " + id);
    }
    productRepository.deleteById(id);
  }

  private ProductDTO convertToDTO(Product product) {
    ProductDTO dto = new ProductDTO();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setStockQuantity(product.getStockQuantity());
    if (product.getCategory() != null) {
      dto.setCategoryId(product.getCategory().getId());
      dto.setCategoryName(product.getCategory().getName());
    }
    return dto;
  }

  private void updateProductFromDTO(Product product, ProductDTO dto) {
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setStockQuantity(dto.getStockQuantity());
    if (dto.getCategoryId() != null) {
      Category category = categoryRepository.findById(dto.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
      product.setCategory(category);
    } else if (dto.getCategoryName() != null) {
      Category category = categoryRepository.findByName(dto.getCategoryName())
          .orElseGet(() -> {
            Category newCat = new Category();
            newCat.setName(dto.getCategoryName());
            return categoryRepository.save(newCat);
          });
      product.setCategory(category);
    }
  }
}
