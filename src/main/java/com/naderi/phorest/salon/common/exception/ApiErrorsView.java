package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class ApiErrorsView extends ApiErrorResponse {

    private final List<ApiFieldError> fieldErrors;
    private final List<ApiGlobalError> globalErrors;

    public ApiErrorsView(SalonErrors errorCode, List<ApiFieldError> fieldErrors, List<ApiGlobalError> globalErrors, MethodArgumentNotValidException ex) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, errorCode.getErrorCode(), ex);
        super.setMessage(errorCode.getDescription());
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }

    public List<ApiFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public List<ApiGlobalError> getGlobalErrors() {
        return globalErrors;
    }


}