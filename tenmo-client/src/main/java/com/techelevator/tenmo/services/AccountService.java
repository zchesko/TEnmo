package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.User;

public class AccountService {

    private String BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String url) {
	this.BASE_URL = url;

    }

    public BigDecimal viewBalance(int user_id, String token) {
	HttpHeaders headers = new HttpHeaders();
	headers.setBearerAuth(token);
	HttpEntity entity = new HttpEntity(headers);

	ResponseEntity<BigDecimal> result = restTemplate.exchange(BASE_URL + "/accounts/balance/" + user_id,
		HttpMethod.GET, entity, BigDecimal.class);
	BigDecimal answer = result.getBody();
	System.out.println("Your balance is: " + " $" + answer);
	return answer;
    }

    // View Current Balance

    /*
     * private HttpEntity<User> makeAuthEntity (AuthenticatedUser currentUser) {
     * HttpHeaders headers = new HttpHeaders();
     * headers.setBearerAuth(currentUser.getToken());
     * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity entity = new
     * HttpEntity<>(headers); return entity; }
     */
}
