package com.mta.stockmanagement.productservice.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.*;

@Data
@Builder
public class InternalApiResponse<T> {
    private FriendlyMessage friendlyMessage;
    private T payload;
    private boolean hasError;
    private List<String> errorMessages;
    private HttpStatus httpStatus;
}
