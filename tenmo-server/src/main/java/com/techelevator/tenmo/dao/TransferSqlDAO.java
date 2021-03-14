package com.techelevator.tenmo.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

@Component
public class TransferSqlDAO implements TransferDAO {
    private JdbcTemplate jdbcTemplate;
    private TransferDAO transferDAO;
  

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getAllTransfers(int account_id) {
	List<Transfer> allTransfers = new ArrayList<>();
	String sqlGetFromTransfers = "SELECT transfers.*, user1.username AS userFrom, user2.username AS userTo " +
								 "FROM transfers " +
								 "JOIN accounts a ON transfers.account_from = a.account_id " +
								 "JOIN accounts b ON transfers.account_to = b.account_id " +
								 "JOIN users user1 ON a.user_id = user1.user_id " +
								 "JOIN users user2 ON b.user_id = user2.user_id " +
								 "WHERE a.user_id = ? OR b.user_id = ?";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetFromTransfers, account_id, account_id);
	while (results.next()) {
	    Transfer getTransfers = mapRowToTransfer(results);
	    allTransfers.add(getTransfers);
	}
	return allTransfers;
    }

    @Override
    public Transfer getTransfersById(int transfer_id) {
	Transfer getTransfer = new Transfer();
	String sqlGetTransfersById = "SELECT transfers.*, user1.username AS userFrom, user2.username AS userTo, ts.transfer_status_desc, tt.transfer_type_desc " +
			 					 "FROM transfers " +
			 					 "JOIN accounts a ON transfers.account_from = a.account_id " +
			 					 "JOIN accounts b ON transfers.account_to = b.account_id " +
			 					 "JOIN users user1 ON a.user_id = user1.user_id " +
			 					 "JOIN users user2 ON b.user_id = user2.user_id " +
			 					 "JOIN transfer_statuses ts ON transfers.transfer_status_id = ts.transfer_status_id " +
			 					 "JOIN transfer_types tt ON transfers.transfer_type_id = tt.transfer_type_id " +
			 					 "WHERE transfers.transfer_id = ?";
	 SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetTransfersById, transfer_id);
	 while(results.next()) {
	     getTransfer = mapRowToTransfer(results);
	 }
	return getTransfer;
    }

    @Override
    public boolean makeTransfer(int account_from, BigDecimal amount, int account_to) {
		Account sender = getAccountById(account_from);
		Account receiver = getAccountById(account_to);
	
	if (sender.getBalance().compareTo(amount) >= 0) {
	   
	    

	    String sqlAddTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2,2,?,?,?)";
	    jdbcTemplate.update(sqlAddTransfer, sender.getAccount_id(), receiver.getAccount_id(), amount);
	} else if(sender.getBalance().compareTo(amount) == -1) {
	  
	
	}
	return true;  // throws insufficentfundsException
	}
	    @Override
	    public boolean affectAccounts(int account_from, int account_to, BigDecimal amount ) {
		Account sender = new Account();
		 sender = getAccountById(account_from);
		Account receiver = new Account();
		receiver = getAccountById(account_to);
		/*Transfer transfer = new Transfer();
		String sqlGetlastTransfer = "SELECT * FROM transfers WHERE transfer_id = ( SELECT MAX(transfer_id) FROM transfers WHERE transfer_id IS NOT NULL) ";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetlastTransfer);
		while(result.next()) {
		    transfer = mapRowToTransfer(result);
		}*/
		
		BigDecimal senderBalance = sender.getBalance();
		BigDecimal receiverBalance = receiver.getBalance();
		
		 senderBalance = sender.getBalance().subtract(amount);
		    sender.setBalance(senderBalance);
		 receiverBalance = receiver.getBalance().add(amount);
		 
		 String sqlUpdateSendBalance = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		    jdbcTemplate.update(sqlUpdateSendBalance, senderBalance, account_from);
		    String sqlUpdateReceiverBalance = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		    jdbcTemplate.update(sqlUpdateReceiverBalance, receiverBalance, account_to);
		
		return true;
	    
	   
    }
    @Override
    public Account getAccountById(int account_id) {
	Account account = new Account();
	String sqlGetAccount = "SELECT * FROM accounts WHERE account_id =?";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAccount, account_id);
	
	if (results.next()) {
	    account.setAccount_id(results.getInt("account_id"));
	    account.setUser_id(results.getInt("user_id"));
	    account.setBalance(results.getBigDecimal("balance"));
	}
	return account;
    }

    // Map Row to Transfer
    private Transfer mapRowToTransfer(SqlRowSet results) {
	Transfer transfer = new Transfer();
	transfer.setTransferId(results.getInt("transfer_id"));
	transfer.setTransferTypeId(results.getInt("transfer_type_id"));
	transfer.setTransferStatusId(results.getInt("transfer_status_id"));
	transfer.setAccountFrom(results.getInt("account_from"));
	transfer.setAccountTo(results.getInt("account_to"));
	transfer.setAmount(results.getBigDecimal("amount"));
	try {
		transfer.setUserFrom(results.getString("userFrom"));
		transfer.setUserTo(results.getString("userTo"));
	} catch (Exception ex) {}
	try {
		transfer.setTransferType(results.getString("transfer_type_desc"));
		transfer.setTransferStatus(results.getString("transfer_status_desc"));
	} catch (Exception ex) {}
	return transfer;
    }

}
