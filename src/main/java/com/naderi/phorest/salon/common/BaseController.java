package com.naderi.phorest.salon.common;

import com.naderi.phorest.salon.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public abstract class BaseController {
    private static final String ERROR_PAGES_NAME = "pages/error";
    private final static Logger log = LoggerFactory.getLogger(BaseController.class);
    @Value("${spring.profiles.active}")
    protected String activeProfile;

    @ExceptionHandler(AppConfigurationException.class)
    public ModelAndView invalidAppConfigurationExceptionHandler(HttpServletRequest req, AppConfigurationException exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ModelAndView unsupportedOperationExceptionHandler(HttpServletRequest req, UnsupportedOperationException exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView dataIntegrityViolationExceptionHandler(HttpServletRequest req, Exception exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(DataAccessException.class)
    public ModelAndView dataAccessExceptionHandler(HttpServletRequest req, DataAccessException exception) {
        return createResponseBody(req, exception);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView methodArgumentTypeMismatchExceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView httpMessageNotReadableExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            log.debug("Field Validation Failed : Message : {}  rootbean: {}  property: {} invalid value: {}", violation.getMessage(), violation.getRootBean(), violation.getPropertyPath(), violation.getInvalidValue());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ModelAndView handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream().map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getCode(), fieldError.getRejectedValue())).collect(toList());

        List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream().map(globalError -> new ApiGlobalError(globalError.getCode())).collect(toList());

        ApiErrorsView apiErrorsView = new ApiErrorsView(SalonErrors.VALIDATION_ERROR_422, apiFieldErrors, apiGlobalErrors, ex);
        return getModelAndView(null, HttpStatus.UNPROCESSABLE_ENTITY, apiErrorsView);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        return createResponseBody(req, exception);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handle404Exception(HttpServletRequest req, NoHandlerFoundException exception) {
        return createResponseBody(req, exception);
    }

    HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = AnnotatedElementUtils.findMergedAnnotation(exception.getClass(), ResponseStatus.class);

        if (annotation != null) {
            return annotation.value();
        }
        if (exception instanceof DataIntegrityViolationException) return HttpStatus.CONFLICT;
        if (exception instanceof DataAccessException) return HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception instanceof UnsupportedOperationException) return HttpStatus.METHOD_NOT_ALLOWED;
        if (exception instanceof MethodArgumentTypeMismatchException) return HttpStatus.BAD_REQUEST;
        if (exception instanceof HttpMessageNotReadableException) return HttpStatus.BAD_REQUEST;
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ModelAndView createResponseBody(HttpServletRequest req, Exception exception) {

        String errorCode = "General_Error";
        if (exception instanceof BaseException) {
            final BaseException baseException = (BaseException) exception;
            return getModelAndView(req.getRequestURL().toString(), baseException.getHttpStatus(), baseException.getError());
        }

        HttpStatus responseStatus = resolveAnnotatedResponseStatus(exception);
        if (responseStatus.equals(HttpStatus.UNPROCESSABLE_ENTITY) || responseStatus.equals(HttpStatus.NOT_FOUND))
            log.info("Request: {} raised {}", req.getRequestURL(), responseStatus.value());
        else {
            log.error("Request: {} raised: ", req.getRequestURL(), exception);
        }
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(responseStatus, errorCode, exception);

        return getModelAndView(req.getRequestURL().toString(), responseStatus, apiErrorResponse);
    }

    private ModelAndView getModelAndView(String url, HttpStatus responseStatus, ApiErrorResponse response) {

        log.info("Unchecked Exception: ErrorStatusCode:[{}]", responseStatus);
        log.info("Unchecked Exception: Error Message: Page/resource [{}] {}", url, response.getMessage());
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGES_NAME, HttpStatus.BAD_REQUEST);
        modelAndView.addObject("datetime", LocalDateTime.now());
        modelAndView.addObject("statusCode", responseStatus != null ? responseStatus : HttpStatus.NOT_FOUND.value());
        modelAndView.addObject("uri", url);
        modelAndView.addObject("messageCode", response.getErrorCode());
        modelAndView.addObject("message", response.getMessage());
        modelAndView.addObject("devMessage", response.getDetailsMessage());
        modelAndView.addObject("env", activeProfile);
        return modelAndView;

    }
}
