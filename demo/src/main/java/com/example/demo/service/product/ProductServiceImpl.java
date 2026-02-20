package com.example.demo.service.product;


import com.example.demo.dto.product.ItemResponse;
import com.example.demo.dto.product.ProductRequest;
import com.example.demo.dto.product.ProductResponse;
import com.example.demo.entity.product.Item;
import com.example.demo.entity.product.Product;
import com.example.demo.repository.product.ItemRepository;
import com.example.demo.repository.product.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = getProduct(id);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request, String username) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .createdBy(username)
                .createdOn(Instant.now())
                .build();

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request, String username) {
        Product product = getProduct(id);
        product.setProductName(request.getProductName());
        product.setModifiedBy(username);
        product.setModifiedOn(Instant.now());
        return mapToResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Override
    public List<ItemResponse> getItemsByProductId(Long productId) {
        List<Item> items = itemRepository.findByProductId(productId);
        return items.stream()
                .map(i -> ItemResponse.builder()
                        .id(i.getId())
                        .quantity(i.getQuantity())
                        .build())
                .toList();
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    private ProductResponse mapToResponse(Product product) {
        List<ItemResponse> itemResponses = product.getItems() == null ? List.of() :
                product.getItems().stream()
                        .map(i -> ItemResponse.builder()
                                .id(i.getId())
                                .quantity(i.getQuantity())
                                .build())
                        .toList();

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .createdBy(product.getCreatedBy())
                .createdOn(product.getCreatedOn())
                .modifiedBy(product.getModifiedBy())
                .modifiedOn(product.getModifiedOn())
                .items(itemResponses)
                .build();
    }
}