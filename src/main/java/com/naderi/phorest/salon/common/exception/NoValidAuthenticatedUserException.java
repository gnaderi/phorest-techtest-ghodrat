package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class NoValidAuthenticatedUserException extends BaseException {

    private static final long serialVersionUID = -7535043323520920138L;

    public NoValidAuthenticatedUserException() {
        this("You must logged in; or logout and login in again!");
    }

    public NoValidAuthenticatedUserException(String message) {
        super(HttpStatus.BAD_REQUEST, new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), SalonErrors.NO_VALID_AUTHENTICATED_USER.getErrorCode(),
                SalonErrors.NO_VALID_AUTHENTICATED_USER.getDescription(), message));
    }

}