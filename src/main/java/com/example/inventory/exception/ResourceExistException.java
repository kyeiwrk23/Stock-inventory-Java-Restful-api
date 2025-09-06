package com.example.inventory.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceExistException extends RuntimeException {

    private String resourceName;

    public ResourceExistException(String resourceName) {
        super(String.format("%s Already exist!!!",resourceName));
        this.resourceName = resourceName;
    }
}
