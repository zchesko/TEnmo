package com.techelevator.tenmo.dao;

import java.math.BigDecimal;



import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface AccountDAO {
    
    BigDecimal viewBalance(int user_id); 
	    
    Account getAccountById(int account_id);
    
    int getAccountIdByUserId(int user_id);
}
