package com.techelevator.tenmo.dao;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	// getAllTransfers, getTransferById, sendTransfer, requestTransfer, getPendingTransfers, updateTransferRequest
	
	List<Transfer> getAllTransfers(int user_id);
		
	
	
	Transfer getTransfersById(int user_id);
	
	Account getAccountById(int account_id);

	public boolean affectAccounts(int account_from, int account_to, BigDecimal amount);

	
	
	 public boolean makeTransfer(int account_from, BigDecimal amount, int account_to);
	
}
