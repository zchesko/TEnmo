package com.techelevator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.List;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dao.AccountSqlDAO;
import com.techelevator.tenmo.dao.TransferSqlDAO;
import com.techelevator.tenmo.dao.UserSqlDAO;

public class TestAccountSqlDAO {
    private static SingleConnectionDataSource dataSource;

    private AccountSqlDAO dao; 
    private JdbcTemplate jdbcTemplate;
    private TransferSqlDAO transferdao;
    private UserSqlDAO tester;

    @BeforeClass
    public static void setupDataSource() {
	dataSource = new SingleConnectionDataSource();
	dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
	dataSource.setUsername("postgres");
	dataSource.setPassword("postgres1");
	dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource() {
	dataSource.destroy();
    }

    @Before
    public void get_object_to_test() {
	jdbcTemplate = new JdbcTemplate(dataSource);
	dao = new AccountSqlDAO(jdbcTemplate);
	tester = new UserSqlDAO(jdbcTemplate);
	transferdao = new TransferSqlDAO(jdbcTemplate);
    }

    @After
    public void rollback() throws SQLException {
	dataSource.getConnection().rollback();
    }

    @Test
    public void return_balance() {
	tester.create("nick", "pw");

	int fakeUserId = tester.findIdByUsername("nick");

	BigDecimal fakeBalance = dao.viewBalance(fakeUserId);
	BigDecimal a = new BigDecimal("1000.00");

	Assert.assertEquals(fakeBalance, a);

    }
	

    @Test
    public void account_by_id() {
	tester.create("nick", "pw");
	User fakeUser = tester.findByUsername("nick");
	
	int accountId = dao.getAccountIdByUserId(fakeUser.getId());
	Account fakeAccount = dao.getAccountById(accountId);
	
	
	Assert.assertEquals(fakeAccount.getUser_id(), fakeUser.getId());
	
    }

    
    }

