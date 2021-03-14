package com.techelevator.tenmo.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Insufficient funds in your account")
public class InsufficientFundsException extends Exception {
    private static final long serialVersionUID = 1L;

  
    public InsufficientFundsException(String message) {
        super("Insufficient funds in your account");
    }

}
