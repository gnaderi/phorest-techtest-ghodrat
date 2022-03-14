package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class InvalidYamlInputException extends BaseException {
    public InvalidYamlInputException() {
        this("Invalid yaml file or content!");
    }

    public InvalidYamlInputException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, new ApiErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), SalonErrors.INVALID_YAML_EXCEPTION.getErrorCode(), SalonErrors.INVALID_YAML_EXCEPTION.getDescription(), message));
    }
}