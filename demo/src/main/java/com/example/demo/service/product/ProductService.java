package com.example.demo.service.product;


import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request, String username);
    ProductResponse updateProduct(Long id, ProductRequest request, String username);
    void deleteProduct(Long id);
    List<?> getItemsByProductId(Long productId);
}