package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BaseException {

    private static final long serialVersionUID = 7124892421785401350L;

    public InvalidRequestException() {
        this("Invalid request exception!");
    }

    public InvalidRequestException(String host, String rraInstance) {
        this(String.format("Invalid http request exception! [Host#%s][RRA#%s]", host, rraInstance));
    }

    public InvalidRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), SalonErrors.INVALID_REQUEST.getErrorCode(),
                SalonErrors.INVALID_REQUEST.getDescription(), message));
    }

}