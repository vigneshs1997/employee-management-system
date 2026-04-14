package com.spvm.ems_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)//Resource Not found Exception
public class ResourceNotFoundException extends RuntimeException{
    /*Spring Boot will catch the exception and return error message with the Http status code*/


    /*Creating a constructor*/
    public ResourceNotFoundException(String message){
      super(message);//sending message to super class constructor
    }
}
