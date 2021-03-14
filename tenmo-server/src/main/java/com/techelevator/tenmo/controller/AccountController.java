package com.techelevator.tenmo.controller;

import java.math.BigDecimal;


import javax.validation.Valid;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;


@RestController

public class AccountController {
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransferDAO transferDAO;
    private Account account;
    private Transfer transfer;
    
    
    public AccountController(AccountDAO accountDAO  ) {
		  this.accountDAO = accountDAO;
	       
    }
    
    @RequestMapping(path = "/accounts/balance/{user_id}", method = RequestMethod.GET)
    public BigDecimal viewBalance(@Valid @PathVariable int user_id) {
	return accountDAO.viewBalance(user_id);
	 }
    @RequestMapping(path ="/accounts/{user_id}", method = RequestMethod.GET)
    public Account getAccountById(@Valid @PathVariable int user_id) {
	return accountDAO.getAccountById(user_id);
    }
  
   
  
}
