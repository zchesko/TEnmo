package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

import io.cucumber.core.gherkin.vintage.internal.gherkin.deps.com.google.gson.JsonObject;

public class TransferService {

    private String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private AccountService accountService;
    private ConsoleService console;
    AuthenticatedUser currentUser;
    private Transfer transfer;
    public TransferService(String url) {
	this.API_BASE_URL = url;
    }

    // Create
    public void createTransfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo,
	    BigDecimal amount) {
	Transfer transfer = new Transfer();
	transfer.setTransferTypeId(transferTypeId);
	transfer.setTransferStatusId(transferStatusId);
	transfer.setAccountFrom(accountFrom);
	transfer.setAccountTo(accountTo);
	transfer.setAmount(amount);

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

	restTemplate.exchange(API_BASE_URL + "users/transfer", HttpMethod.POST, entity, Transfer.class).getBody();
    }

    
	

    // Get Users
    public User[] listAllUsers(String token) {
	HttpHeaders headers = new HttpHeaders();
	headers.setBearerAuth(token);
	HttpEntity entity = new HttpEntity(headers);

	User[] getAllUsers = null;
	try {
	    getAllUsers = restTemplate.exchange(API_BASE_URL + "users/", HttpMethod.GET, entity, User[].class)
		    .getBody();
	    for (User allUser : getAllUsers) {
		System.out.println(allUser);
	    }
	} catch (RestClientResponseException e) {
	    System.out.println("Error getting user list");
	}
	return getAllUsers;
    }

    // Get Transfer History list o transfer
    public Transfer[] listAllTransfers(String token, int userId) {
	HttpHeaders headers = new HttpHeaders();
	headers.setBearerAuth(token);
	HttpEntity entity = new HttpEntity( headers);

	Transfer[] transfer = null;
	try {
	    transfer = restTemplate
		    .exchange(API_BASE_URL + "users/" + userId + "/transfer", HttpMethod.GET, entity, Transfer[].class)
		    .getBody();
	    System.out.println("-----------------------------\r\n" + "TRANSFERS\r\n" + "ID" + '\t' + '\t' + "FROM/TO"
		    + '\t' + '\t' + "AMOUNT \r\n");
	    String fromTo = "";
	    String username = "";
	    for (Transfer findName : transfer) {
		if (userId == findName.getAccountFrom()) {
		    fromTo = "TO: ";
		    username = findName.getUserTo();
		} else {
		    fromTo = "FROM: ";
		    username = findName.getUserFrom();
		}
		System.out.println(
			findName.getTransferId() + "\t\t" + fromTo + username + "\t\t$" + findName.getAmount());
	    }
	    System.out.println("-----------------------------\r\n" + "Please Enter Transfer ID To View Details: ");
	    Scanner scanner = new Scanner(System.in);
	    String input = scanner.nextLine();

	    if (Integer.parseInt(input) != 0) {
		boolean findTransferId = false;
		for (Transfer findName : transfer) {
		    if (Integer.parseInt(input) == findName.getTransferId()) {
			Transfer trans = restTemplate
				.exchange(API_BASE_URL + "users/" + userId + "/transfer/" + findName.getTransferId(),
					HttpMethod.GET, entity, Transfer.class)
				.getBody();
			findTransferId = true;
			System.out.println("-----------------------------\r\n" + "TRANSFER DETAILS\r\n"
				+ "-----------------------------\r\n" + "ID: " + trans.getTransferId() + "\r\n"
				+ "FROM: " + trans.getUserFrom() + "\r\n" + "TO: " + trans.getUserTo() + "\r\n"
				+ "TYPE: " + trans.getTransferType() + "\r\n" + "STATUS: " + trans.getTransferStatus()
				+ "\r\n" + "AMOUNT: $" + trans.getAmount());
		    }
		}
		if (!findTransferId) {
		    System.out.print("Invalid Transfer ID");
		}
	    }
	} catch (Exception e) {
	    System.out.println("Uh oh...");
	}
	return transfer;
    }

    // Details By ID

    public Transfer listDetailsForTransfer(AuthenticatedUser userId, int transferId) {
	Transfer transfer = null;
	try {
	    transfer = restTemplate.getForObject(API_BASE_URL + +transferId, Transfer.class);
	} catch (RestClientResponseException ex) {
	    console.printError("Could not retrieve the transfer details.");
	} catch (ResourceAccessException ex) {
	    console.printError("A network error occurred.");
	}
	return transfer;
    }

    // Update

    public void updateTransfer(int transferId, int transferStatusId) {
	Transfer transfer = new Transfer();
	transfer.setTransferStatusId(transferStatusId);

	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

	restTemplate.exchange(API_BASE_URL + "users/transfer/" + transferId + "/update", HttpMethod.POST, entity,
		Transfer.class).getBody();
    }
    
    public void sendBucks(String token, User user1 )  {
    HttpHeaders headers = new HttpHeaders();
	headers.setBearerAuth(token);
	headers.setContentType(MediaType.APPLICATION_JSON);
	HttpEntity entity = new HttpEntity<>( headers);
	
	User[] getAllUsers = null;
	try {
	    Scanner scanner = new Scanner(System.in);
	    getAllUsers = restTemplate.exchange(API_BASE_URL + "/allUsers", HttpMethod.GET, entity, User[].class)
		    .getBody();
	     
		System.out.println("------------------------------------\r\n" +
				"Users\r\n"+
				"ID\t\tName\r\n" +
				"---------------------------------------");
		for(User user: getAllUsers) {
		    if(user.getId() != user1.getId()) {
			System.out.println(user.getId() + "\t\t" +user.getUsername());
		    }
	    } System.out.println("---------------------------------------\r\n" +
		    		"Enter the ID of who you are sending money to: ");
	    boolean validInput = false;
	    while(validInput == false) {
	    String input = scanner.nextLine();
	    int receiverAccountId = Integer.parseInt(input);
	    	if(receiverAccountId != 0) {
	    	    System.out.println("Enter Amount: ");
	    	   
	    		
	    	    }
	    	
	    		String amount = scanner.nextLine();
	    		
	    	
	    	    BigDecimal answer = new BigDecimal(amount);
	    	   if( answer.compareTo(accountService.viewBalance(user1.getId(), token)) <=0);  
		 restTemplate.exchange(API_BASE_URL + "transfer/" + user1.getId() + "/"+ answer+ "/" + receiverAccountId, HttpMethod.POST, entity, boolean.class);
		 restTemplate.put(API_BASE_URL + "update/transfer/" + user1.getId() +"/" +receiverAccountId +"/" + answer , HttpMethod.PUT, entity, boolean.class);
		 System.out.println("You're transfer went through!!!");
		 validInput = true;
	    		System.out.println("DOesnt work");
	    		
	    	    }
	    		
	   
	}
    

	 catch (Exception e) {
		 System.out.println("Invalid Input");
	    }

    }
     
    }

