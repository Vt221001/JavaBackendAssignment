package com.example.demo.repository.product;


import com.example.demo.entity.product.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByProductId(Long productId);
}
