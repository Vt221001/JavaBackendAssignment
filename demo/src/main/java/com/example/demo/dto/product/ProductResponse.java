package com.example.demo.dto.product;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String createdBy;
    private Instant createdOn;
    private String modifiedBy;
    private Instant modifiedOn;
    private List<ItemResponse> items;
}