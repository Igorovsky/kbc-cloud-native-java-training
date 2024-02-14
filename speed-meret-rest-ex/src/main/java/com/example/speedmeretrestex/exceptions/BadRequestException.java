package com.example.speedmeretrestex.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException (String message){
        super(message);
    }
}
