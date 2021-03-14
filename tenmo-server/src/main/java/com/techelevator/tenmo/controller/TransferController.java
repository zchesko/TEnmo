package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;


@RestController
public class TransferController {
	
	private TransferDAO transferDAO;
	private UserDAO userDAO;
	
	public TransferController(UserDAO userDAO, TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
		this.userDAO = userDAO;
	}
	
	@RequestMapping (path = "users/{userId}/transfer", method = RequestMethod.GET)
	@ResponseStatus (HttpStatus.FOUND)
	public List<Transfer> getAllTransfers(@PathVariable int userId) {
		return transferDAO.getAllTransfers(userId);
	}
	
	@RequestMapping (path = "users/{userId}/transfer/{transferId}", method = RequestMethod.GET)
	@ResponseStatus (HttpStatus.FOUND)
	public Transfer getTransfersById(@PathVariable int userId, @PathVariable int transferId) {
		return transferDAO.getTransfersById(transferId);
	}
	@RequestMapping( path ="/allUsers", method = RequestMethod.GET)
	public List <User> getAllUsers(){
	    return userDAO.findAll();
	}
	@RequestMapping( path = "/transfer/{account_from}/{amount}/{account_to}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public boolean sendBucks(@Valid @PathVariable int account_from, @PathVariable BigDecimal amount, @Valid @PathVariable int account_to ) {
	    return transferDAO.makeTransfer(account_from, amount, account_to);
	    
	}
	@RequestMapping(path = "/update/transfer/{account_from}/{account_to}/{amount}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public boolean affectAccounts(@Valid @PathVariable int account_from,@PathVariable int account_to,  @PathVariable BigDecimal amount) {
	    return transferDAO.affectAccounts( account_from, account_to,  amount );
	}
	
	
	}
	
	
	
	
	
	
	
	
		
	

