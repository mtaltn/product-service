package com.mta.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private Long ID;
    private String name;
    private Integer quantity;
    private Double price;
}
