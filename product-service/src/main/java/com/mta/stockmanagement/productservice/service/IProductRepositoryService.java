package com.mta.stockmanagement.productservice.service;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;

import java.util.List;

public interface IProductRepositoryService {
    Product create(Language language, ProductCreateRequest productCreateRequest);
    Product getByID(Language language,Long ID);
    List<Product> getAll(Language language);
    Product update(Language language, Long ID, ProductUpdateRequest productUpdateRequest);
    Product delete(Language language, Long ID);
}
