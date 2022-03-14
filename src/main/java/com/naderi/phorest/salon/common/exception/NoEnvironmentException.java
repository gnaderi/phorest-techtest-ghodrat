package com.naderi.phorest.salon.common.exception;

import org.springframework.http.HttpStatus;

public class NoEnvironmentException extends BaseException {
    public NoEnvironmentException(String errorCode, String errorMsg, String devErrorMsg) {
        super(HttpStatus.BAD_REQUEST, new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), errorCode, errorMsg, devErrorMsg));
    }

    public NoEnvironmentException(String errorCode, String errorMsg) {
        this(errorCode, errorMsg, null);
    }
}
