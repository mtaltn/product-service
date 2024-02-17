package com.mta.stockmanagement.productservice.service.impl;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.mta.stockmanagement.productservice.exception.exceptions.ProductAlreadyDeletedException;
import com.mta.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import com.mta.stockmanagement.productservice.exception.exceptions.ProductNotFoundException;
import com.mta.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.mta.stockmanagement.productservice.repository.ProductRepository;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;
import com.mta.stockmanagement.productservice.response.FriendlyMessage;
import com.mta.stockmanagement.productservice.response.InternalApiResponse;
import com.mta.stockmanagement.productservice.response.ProductResponse;
import com.mta.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {
    private final ProductRepository productRepository;

    @Override
    public InternalApiResponse<ProductResponse> create(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][create] -> request: {}",this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                    .name(productCreateRequest.getName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            ProductResponse productResponse = convertProductResponse(productRepository.saveAndFlush(product));
            log.debug("[{}][create] -> response: {}",this.getClass().getSimpleName(), productResponse);

            return InternalApiResponse.<ProductResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                            .build())
                    .httpStatus(HttpStatus.CREATED)
                    .hasError(false)
                    .payload(productResponse)
                    .build();
        }catch (Exception exception){
            throw new ProductNotCreatedException(language,
                    FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION,
                    "product request:" + productCreateRequest.toString());
        }
    }

    @Override
    public InternalApiResponse<ProductResponse> getById(Language language, Long id) {
        log.debug("[{}][getById] -> request id: {}",this.getClass().getSimpleName(), id);
        Product product = productRepository.getByIdAndDeletedFalse(id);
        if (Objects.isNull(product)){
            throw new ProductNotFoundException(language,FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "Product not found for id:"+id);
        }
        log.debug("[{}][getById] -> response id: {}",this.getClass().getSimpleName(), product);
        ProductResponse productResponse = convertProductResponse(product);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Override
    public InternalApiResponse<List<ProductResponse>> getAll(Language language) {
        List<Product> products = productRepository.getAllByDeletedFalse();
        log.debug("[{}][getAll] -> ",this.getClass().getSimpleName());
        if (products.isEmpty()) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "Product not found");
        }
        List<ProductResponse> productResponses = convertProductResponseList(products);
        log.debug("[{}][update] -> response: {}",this.getClass().getSimpleName(), productResponses);

        return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponses)
                .build();
    }

    @Override
    public InternalApiResponse<ProductResponse> update(Language language, Long id, ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][update] -> request: {}",this.getClass().getSimpleName(), productUpdateRequest);
        Product product = getByIdReturnProduct(language,id);
        product.setName(productUpdateRequest.getName());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setPrice(productUpdateRequest.getPrice());
        product.setCreatedDate(product.getCreatedDate());
        product.setUpdatedDate(new Date());
        ProductResponse productResponse = convertProductResponse(productRepository.saveAndFlush(product));
        log.debug("[{}][update] -> response: {}",this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Override
    public  InternalApiResponse<ProductResponse> delete(Language language, Long id) {
        log.debug("[{}][delete] -> request id: {}",this.getClass().getSimpleName(), id);
        Product product;
        try{
            product = getByIdReturnProduct(language,id);
            product.setDeleted(true);
            product.setUpdatedDate(new Date());
            Product productInfo = productRepository.saveAndFlush(product);
            ProductResponse productResponses = convertProductResponse(productInfo);
            log.debug("[{}][delete] -> response: {}",this.getClass().getSimpleName(), productResponses);
            return InternalApiResponse.<ProductResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_DELETED))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .payload(productResponses)
                    .build();
        }catch (ProductNotFoundException productNotFoundException){
            throw new ProductAlreadyDeletedException(language,FriendlyMessageCodes.PRODUCT_ALREADY_DELETED,"Product already deleted id : "+ id);
        }
    }

    public Product getByIdReturnProduct(Language language, Long id) {
        log.debug("[{}][getById] -> request id: {}",this.getClass().getSimpleName(), id);
        Product product = productRepository.getByIdAndDeletedFalse(id);
        if (Objects.isNull(product)){
            throw new ProductNotFoundException(language,FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "Product not found for id:"+id);
        }
        log.debug("[{}][getById] -> response id: {}",this.getClass().getSimpleName(), product);
        return product;
    }

    @Override
    public List<ProductResponse> convertProductResponseList(List<Product> productList){
        return productList.stream()
                .map(arg -> ProductResponse.builder()
                        .id(arg.getId())
                        .name(arg.getName())
                        .quantity(arg.getQuantity())
                        .price(arg.getPrice())
                        .createdDate(arg.getCreatedDate().getTime())
                        .updatedDate(arg.getUpdatedDate().getTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .createdDate(product.getCreatedDate().getTime())
                .updatedDate(product.getUpdatedDate().getTime())
                .build();
    }
}
