package spring.otus.hw09.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.otus.hw09.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException ex, Model model) {
        logger.error("Entity not found", ex);
        model.addAttribute("error", new ErrorDto(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        logger.error("Internal server error", ex);
        model.addAttribute("error", new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
        return "error/500";
    }
}
