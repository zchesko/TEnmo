package com.techelevator.tenmo.dao;

import java.math.BigDecimal;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;

@Component
public class AccountSqlDAO implements AccountDAO {

    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;
    private TransferDAO transferDAO;
    

    public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal viewBalance(int user_id) {
	String sqlBalance = "Select balance FROM accounts WHERE user_id = ? ";
	return jdbcTemplate.queryForObject(sqlBalance, BigDecimal.class, user_id);

    }

    @Override
    public Account getAccountById(int account_id) {
	Account account = new Account();
	String sqlGetAccount = "SELECT * FROM accounts WHERE account_id =?";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAccount, account_id);
	
	while(results.next()) {
	    account.setAccount_id(results.getInt("account_id"));
	    account.setUser_id(results.getInt("user_id"));
	    account.setBalance(results.getBigDecimal("balance"));
	}
	return account;
    }
    @Override
    public int getAccountIdByUserId(int user_id) {
	String sqlGetAcctId = "SELECT account_id FROM accounts WHERE user_id =?";
	int account_id = jdbcTemplate.queryForObject(sqlGetAcctId, int.class, user_id);
	return account_id;
}
}
