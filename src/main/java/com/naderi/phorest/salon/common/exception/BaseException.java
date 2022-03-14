package com.naderi.phorest.salon.common.exception;


import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private static final long serialVersionUID = -5909752462019991481L;

    private HttpStatus httpStatus;
    private ApiErrorResponse error;

    public BaseException(HttpStatus httpStatus, ApiErrorResponse error) {
        super(error.getMessage());
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BaseException(String message, HttpStatus httpStatus, ApiErrorResponse error) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BaseException(String message, Throwable cause, HttpStatus httpStatus, ApiErrorResponse error) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BaseException(Throwable cause, HttpStatus httpStatus, ApiErrorResponse error) {
        super(cause);
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus, ApiErrorResponse error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
        this.error = error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiErrorResponse getError() {
        return error;
    }

    public void setError(ApiErrorResponse error) {
        this.error = error;
    }
}
