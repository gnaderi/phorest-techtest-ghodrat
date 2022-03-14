package com.naderi.phorest.salon.common.exception;

public class ApiGlobalError {

    private final String code;
    private String message;

    public ApiGlobalError(String code) {
        this.code = code;
    }

    public ApiGlobalError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiGlobalError that = (ApiGlobalError) o;

        return code != null ? code.equals(that.code) : that.code == null;

    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

}