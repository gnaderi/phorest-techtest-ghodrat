package com.naderi.phorest.salon.common.exception;

import com.naderi.phorest.salon.common.SalonErrors;
import org.springframework.http.HttpStatus;

public class ApiErrorResponse {
    private final int status;
    private final String errorCode;
    private String message;
    private String detailsMessage;

    public ApiErrorResponse(int status, SalonErrors error) {
        this(status, error, null);
    }

    public ApiErrorResponse(int status, SalonErrors error, String detailsMessage) {
        this(status, error.getErrorCode(), error.getDescription(), detailsMessage);
    }

    public ApiErrorResponse(int status, String errorCode, String message) {
        this(status, errorCode, message, null);
    }

    public ApiErrorResponse(int status, String errorCode, String message, String detailsMessage) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.detailsMessage = detailsMessage;
    }

    public ApiErrorResponse(HttpStatus status, String errorCode, Exception exception) {
        this.status = status.value();
        this.errorCode = errorCode;
        if (exception != null) {
            this.message = exception.getMessage();
            StackTraceElement[] elements = exception.getStackTrace();
            StringBuilder devMessage = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                StackTraceElement element = elements[i];
                devMessage.append(element);
            }
            this.detailsMessage = devMessage.toString();
        }
    }

    public int getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailsMessage() {
        return detailsMessage;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" + "status=" + status + ", errorCode=" + errorCode + ", message=" + message + '}';
    }
}
