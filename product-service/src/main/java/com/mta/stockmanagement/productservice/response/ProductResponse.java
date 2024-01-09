package com.mta.stockmanagement.productservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Long createdDate;
    private Long updatedDate;
}
