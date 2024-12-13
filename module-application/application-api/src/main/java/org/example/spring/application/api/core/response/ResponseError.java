package org.example.spring.application.api.core.response;

import com.example.spring.domain.ErrorCode;
import org.example.spring.application.api.core.exception.ApiException;

public record ResponseError(ErrorCode code, String message, Object data) {
    public static ResponseError from(ApiException e) {
        return new ResponseError(e.getCode(), e.getMessage(), e.getData());
    }
}
