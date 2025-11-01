package ru.zyryanova.biblioteka_boot.Exception;

public class PersonOptimisticLockException extends RuntimeException{
    public PersonOptimisticLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
