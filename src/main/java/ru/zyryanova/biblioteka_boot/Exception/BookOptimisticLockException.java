package ru.zyryanova.biblioteka_boot.Exception;

public class BookOptimisticLockException extends RuntimeException{
    public BookOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
