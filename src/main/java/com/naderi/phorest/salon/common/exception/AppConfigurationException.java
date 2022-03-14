package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class AppConfigurationException extends BaseException {

    private static final long serialVersionUID = -4550341251277824484L;

    public AppConfigurationException() {
        this("One of the configuration files contain invalid setting!");
    }

    public AppConfigurationException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), SalonErrors.APP_CONFIGURATION_EXCEPTION.getErrorCode(),
                SalonErrors.APP_CONFIGURATION_EXCEPTION.getDescription(), message));
    }
}
