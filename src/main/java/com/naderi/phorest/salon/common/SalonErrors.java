package com.naderi.phorest.salon.common;

public enum SalonErrors {

    VALIDATION_ERROR_422(1, "The request could not be validated by the server."),
    RESOURCE_NOT_FOUND(2, "Requested page/resource not found or has been removed!"),
    APP_CONFIGURATION_EXCEPTION(3, "There is an error in one of the application configuration files!"),
    INVALID_REQUEST(5 ,"Invalid/bad http request to target page/resource!"),
    INVALID_YAML_EXCEPTION(6, "Invalid Yaml file or content value!"),
    NO_VALID_AUTHENTICATED_USER(7, "The user is not logged in or invalid. Please logout and login again!"),

    ;

    private final int code;
    private final String description;

    SalonErrors(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public String getErrorCode() {
        return this.name();
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
