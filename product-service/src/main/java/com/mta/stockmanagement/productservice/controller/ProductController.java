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
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> create(@PathVariable("language")Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest){
        log.debug("[{}][create] -> request: {}",this.getClass().getSimpleName(),productCreateRequest);
        Product product = productRepositoryService.create(language,productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][create] -> response: {}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{id}")
    public InternalApiResponse<ProductResponse> getbyid(@PathVariable("language") Language language,
                                                        @PathVariable("id") Long id){
        log.debug("[{}][getById] -> request id: {}",this.getClass().getSimpleName(),id);
        Product product = productRepositoryService.getById(language,id);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][getById] -> response: {}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{id}")
    public InternalApiResponse<ProductResponse> update(@PathVariable("language") Language language,
                                                       @PathVariable("id") Long id,
                                                       @RequestBody ProductUpdateRequest productUpdateRequest){
        log.debug("[{}][update] -> request: {} {}",this.getClass().getSimpleName(), id,productUpdateRequest);
        Product product = productRepositoryService.update(language,id,productUpdateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][update] -> response: {} {}",this.getClass().getSimpleName(), id,productUpdateRequest);
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

    @ApiOperation(value = "This endpoint get all product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/products")
    public InternalApiResponse<List<ProductResponse>> getAll(@PathVariable("language") Language language){
        log.debug("[{}][getAll]",this.getClass().getSimpleName());
        List<Product> products = productRepositoryService.getAll(language);
        List<ProductResponse> productResponses = convertProductResponseList(products);
        log.debug("[{}][getAll] -> response: {}",this.getClass().getSimpleName(),productResponses);
        return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponses)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{id}")
    public InternalApiResponse<ProductResponse> delete(@PathVariable("language") Language language,
                                                       @PathVariable("id") Long id){
        log.debug("[{}][delete] -> request id: {}",this.getClass().getSimpleName(), id);
        Product product = productRepositoryService.delete(language,id);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][delete] -> response: {}",this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private List<ProductResponse> convertProductResponseList(List<Product> productList){
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
    private static ProductResponse convertProductResponse(Product product) {
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
