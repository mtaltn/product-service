package com.mta.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductCreateRequest {
    private String name;
    private  Integer quantity;
    private  Double price;
}
