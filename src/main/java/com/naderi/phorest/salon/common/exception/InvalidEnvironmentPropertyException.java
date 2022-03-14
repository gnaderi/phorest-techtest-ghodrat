package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class InvalidEnvironmentPropertyException extends BaseException {

    private static final long serialVersionUID = 7124892421785401350L;

    public InvalidEnvironmentPropertyException() {
        this("No application property found for that environment");
    }

    public InvalidEnvironmentPropertyException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), SalonErrors.FILE_OR_RESOURCE_MISSING.getErrorCode(),
                SalonErrors.FILE_OR_RESOURCE_MISSING.getDescription(), message));
    }

}