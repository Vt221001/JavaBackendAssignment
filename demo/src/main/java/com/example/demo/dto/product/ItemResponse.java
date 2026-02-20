package com.example.demo.dto.product;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponse {
    private Long id;
    private Integer quantity;
}