package com.example.Bank_System.exception;

import com.example.Bank_System.service.CustomerService;


public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
