package com.example.inventory.exception;

public class ResourceNotFoundMessageOnlyException extends RuntimeException{

    public ResourceNotFoundMessageOnlyException(String message){
        super(String.format("%s not found", message));
    }
}
