package com.example.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyResourceNotFoundException extends RuntimeException{
   private String ResourceName;
   private String field;
   private String fieldName;
   private Long fieldId;

    public MyResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,fieldId));
        ResourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public MyResourceNotFoundException(String resourceName, String fieldName, String field) {
        super(String.format("%s not found with %s: %s",resourceName,fieldName,field));
        ResourceName = resourceName;
        this.fieldName = fieldName;
        this.field = field;
    }
}
