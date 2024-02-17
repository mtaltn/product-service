package com.mta.stockmanagement.productservice.service;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;
import com.mta.stockmanagement.productservice.response.InternalApiResponse;
import com.mta.stockmanagement.productservice.response.ProductResponse;

import java.util.*;

public interface IProductRepositoryService {
    InternalApiResponse<ProductResponse> create(Language language, ProductCreateRequest productCreateRequest);
    InternalApiResponse<ProductResponse> getById(Language language, Long id);
    InternalApiResponse<List<ProductResponse>> getAll(Language language);
    InternalApiResponse<ProductResponse> update(Language language, Long id, ProductUpdateRequest productUpdateRequest);
    InternalApiResponse<ProductResponse> delete(Language language, Long id);
    ProductResponse convertProductResponse(Product product);
    List<ProductResponse> convertProductResponseList(List<Product> productList);
}
