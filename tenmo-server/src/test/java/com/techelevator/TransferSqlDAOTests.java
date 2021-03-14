package com.techelevator;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import org.junit.*;

public class TransferSqlDAOTests {
	private static SingleConnectionDataSource dataSource;
	private TransferSqlDAO transferDAO;
	private UserSqlDAO userDAO;
	private AccountSqlDAO accountDAO;
	private JdbcTemplate jdbcTemplate;

	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}
	
	@AfterClass
	public static void destroyDataSource() {
		dataSource.destroy();
	}
	
	@Before
	public void get_object_to_test() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transferDAO = new TransferSqlDAO(jdbcTemplate);
		userDAO = new UserSqlDAO(jdbcTemplate);
		accountDAO = new AccountSqlDAO(jdbcTemplate);
	}
	
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	
	@Test
	public void return_all_transfers()  {
		userDAO.create("bigrig", "pw");
		userDAO.create("garth", "pw");
		User fakeUser1 = userDAO.findByUsername("bigrig");
		User fakeUser2 = userDAO.findByUsername("garth");
		
		BigDecimal a = new BigDecimal("50.00");
		int user1AccountId = accountDAO.getAccountIdByUserId(fakeUser1.getId());
		int user2AccountId = accountDAO.getAccountIdByUserId(fakeUser2.getId());
		Account user1Account = accountDAO.getAccountById(user1AccountId);
		Account user2Account = accountDAO.getAccountById(user2AccountId);
		transferDAO.makeTransfer(user1AccountId, a, user2AccountId);
				
		List<Transfer> fakeTransfer = new ArrayList<>();
		fakeTransfer = transferDAO.getAllTransfers(user1AccountId);
		
		Transfer testTransfer = fakeTransfer.get(fakeTransfer.size() - 1);
		
		Assert.assertEquals(testTransfer.getAmount(), a);
	}
	
	@Test
	public void return_id_transfers()  {
		userDAO.create("bigrig", "pw");
		userDAO.create("garth", "pw");
		User fakeUser1 = userDAO.findByUsername("bigrig");
		User fakeUser2 = userDAO.findByUsername("garth");
		
		BigDecimal a = new BigDecimal("50.00");
		int user1AccountId = accountDAO.getAccountIdByUserId(fakeUser1.getId());
		int user2AccountId = accountDAO.getAccountIdByUserId(fakeUser2.getId());
		Account user1Account = accountDAO.getAccountById(user1AccountId);
		Account user2Account = accountDAO.getAccountById(user2AccountId);
		transferDAO.makeTransfer(user1AccountId, a, user2AccountId);
		
		
		List<Transfer> firstList = new ArrayList<>();
		firstList = transferDAO.getAllTransfers(fakeUser1.getId());
		
		Transfer testTransfer = firstList.get(firstList.size() - 1);
		Transfer  secondTransfer = transferDAO.getTransfersById(testTransfer.getTransferId()); 
		
		
		
		Assert.assertEquals(testTransfer.getAmount(), secondTransfer.getAmount());
		
	}
	
	@Test
	  public void process_transfer() {
		userDAO.create("nick", "pw");
		userDAO.create("kurt", "kurt");
		User fakeUser1 = userDAO.findByUsername("nick");
		User fakeUser2 = userDAO.findByUsername("kurt");
		
		BigDecimal request = new BigDecimal("50.00");
		int user1AccountId = accountDAO.getAccountIdByUserId(fakeUser1.getId());
		int user2AccountId = accountDAO.getAccountIdByUserId(fakeUser2.getId());
		Account user1Account = accountDAO.getAccountById(user1AccountId);
		Account user2Account = accountDAO.getAccountById(user2AccountId);
		transferDAO.makeTransfer(user1AccountId, request, user2AccountId);
		transferDAO.affectAccounts(user1AccountId, user2AccountId, request);
		
		BigDecimal fakeBalance1 = accountDAO.viewBalance(fakeUser1.getId());
		BigDecimal fakeBalance2 = accountDAO.viewBalance(fakeUser2.getId());
		
		BigDecimal startingBalance = new BigDecimal("1000.00");
		Assert.assertEquals(fakeBalance1, (startingBalance.subtract(request)));
		Assert.assertEquals(fakeBalance2, startingBalance.add(request));
	}
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
