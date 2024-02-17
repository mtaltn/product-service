package com.mta.stockmanagement.productservice.controller;

import com.mta.stockmanagement.productservice.enums.Language;
import com.mta.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.mta.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.mta.stockmanagement.productservice.repository.entity.Product;
import com.mta.stockmanagement.productservice.request.ProductCreateRequest;
import com.mta.stockmanagement.productservice.request.ProductUpdateRequest;
import com.mta.stockmanagement.productservice.response.FriendlyMessage;
import com.mta.stockmanagement.productservice.response.InternalApiResponse;
import com.mta.stockmanagement.productservice.response.ProductResponse;
import com.mta.stockmanagement.productservice.service.IProductRepositoryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/products")
    public InternalApiResponse<ProductResponse> create(@PathVariable("language")Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest){
        return productRepositoryService.create(language,productCreateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/products/{id}")
    public InternalApiResponse<ProductResponse> getbyid(@PathVariable("language") Language language,
                                                        @PathVariable("id") Long id){
        return productRepositoryService.getById(language,id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/products")
    public InternalApiResponse<ProductResponse> update(@PathVariable("language") Language language,
                                                       @RequestBody ProductUpdateRequest productUpdateRequest){
        return productRepositoryService.update(language,productUpdateRequest.getId(),productUpdateRequest);
    }

    @ApiOperation(value = "This endpoint get all product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/products")
    public InternalApiResponse<List<ProductResponse>> getAll(@PathVariable("language") Language language) {
        return productRepositoryService.getAll(language);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/products/{id}")
    public InternalApiResponse<ProductResponse> delete(@PathVariable("language") Language language,
                                                       @PathVariable("id") Long id){
        return productRepositoryService.delete(language,id);
    }

}
