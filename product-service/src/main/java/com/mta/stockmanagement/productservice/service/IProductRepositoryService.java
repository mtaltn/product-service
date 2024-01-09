package com.mta.stockmanagement.productservice.service;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;

import java.util.*;

public interface IProductRepositoryService {
    Product create(Language language, ProductCreateRequest productCreateRequest);
    Product getById(Language language, Long id);
    List<Product> getAll(Language language);
    Product update(Language language, Long id, ProductUpdateRequest productUpdateRequest);
    Product delete(Language language, Long id);
}
