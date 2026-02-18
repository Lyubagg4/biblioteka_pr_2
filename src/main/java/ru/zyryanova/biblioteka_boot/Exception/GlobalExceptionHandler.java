package ru.zyryanova.biblioteka_boot.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model){
        model.addAttribute("message", ex.getMessage());
        logger.warn("Resource not found", ex.getMessage());
        return "errors/404";
    }
    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException ex, Model model){
        model.addAttribute("message", ex.getMessage());
        logger.warn("Illegal state", ex.getMessage());
        return "errors/400";
    }
    @ExceptionHandler(PersonOptimisticLockException.class)
    public String handlePersonOptimisticLockException(PersonOptimisticLockException ex, Model model){
        model.addAttribute("message", ex.getMessage());
        logger.warn("Person optimistic lock conflict", ex.getMessage());
        return "errors/409";
    }
    @ExceptionHandler(BookOptimisticLockException.class)
    public String handleOptimisticLockException(BookOptimisticLockException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        logger.warn("Book optimistic lock conflict", ex.getMessage());
        return "errors/409";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model){
        model.addAttribute("message", "Произошла внутренняя ошибка сервера");
        logger.error("Internal server error", ex.getMessage());
        return "errors/500";
    }


}
