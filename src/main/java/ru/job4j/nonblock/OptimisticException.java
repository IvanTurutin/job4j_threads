package ru.job4j.nonblock;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}