package com.example.exceptions;

public class ParsingException extends Exception{
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}