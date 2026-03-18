package com.notnilsson.individuelluppgiftjavabackend.exception;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
