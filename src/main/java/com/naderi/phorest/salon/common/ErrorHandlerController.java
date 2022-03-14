package com.naderi.phorest.salon.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class ErrorHandlerController implements ErrorController {
    private static final String ERROR_PAGES_NAME = "pages/error";
    private final static Logger LOGGER = LogManager.getLogger();
    @Value("${spring.profiles.active}")
    protected String activeProfile;

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        // get error status
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        LOGGER.info("Unchecked Exception: ErrorStatusCode:[{}]", status);
        LOGGER.info("Unchecked Exception: Error Message: Page/resource [{}] {}", uri, errorMessage);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGES_NAME);
        modelAndView.addObject("datetime", LocalDateTime.now());
        modelAndView.addObject("statusCode", status != null ? status : HttpStatus.NOT_FOUND.value());
        modelAndView.addObject("uri", uri);
        modelAndView.addObject("messageCode", SalonErrors.RESOURCE_NOT_FOUND.getErrorCode());
        modelAndView.addObject("message", SalonErrors.RESOURCE_NOT_FOUND.getDescription()+"\n"+errorMessage);
        modelAndView.addObject("devMessage", throwable);
        modelAndView.addObject("env", activeProfile);
        return modelAndView;
    }
}