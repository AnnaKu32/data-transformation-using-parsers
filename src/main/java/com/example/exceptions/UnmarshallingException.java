package com.example.exceptions;

public class UnmarshallingException extends Exception {

    public UnmarshallingException(String message) {
        super(message);
    }

    public UnmarshallingException(String message, Throwable cause) {
        super(message, cause);
    }
}
