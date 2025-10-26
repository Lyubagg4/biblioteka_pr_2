package ru.zyryanova.biblioteka_boot.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super("Ресурс не найден");
    }
}
