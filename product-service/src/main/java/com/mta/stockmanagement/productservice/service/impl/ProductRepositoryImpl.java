package com.mta.stockmanagement.productservice.service.impl;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.mta.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import com.mta.stockmanagement.productservice.repository.ProductRepository;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;
import com.mta.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;
    @Override
    public Product create(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][create] -> request: {}",this.getClass().getSimpleName(),productCreateRequest);
        try {
            Product product = Product.builder()
                    .name(productCreateRequest.getName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product productResponse = productRepository.saveAndFlush(product);
            log.debug("[{}][create] -> response: {}",this.getClass().getSimpleName(),productResponse);
            return productResponse;
        }catch (Exception exception){
            throw new ProductNotCreatedException(language,
                    FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION,
                    "product request:" + productCreateRequest.toString());
        }
    }

    @Override
    public Product getByID(Language language, Long ID) {
        return null;
    }

    @Override
    public List<Product> getAll(Language language) {
        return null;
    }

    @Override
    public Product update(Language language, Long ID, ProductUpdateRequest productUpdateRequest) {
        return null;
    }

    @Override
    public Product delete(Language language, Long ID) {
        return null;
    }
}
