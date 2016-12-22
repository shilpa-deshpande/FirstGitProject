package com.capgemini.test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAmountExcpetion;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}

	/*
	 * create account
	 * 1. when the amount is less than 500 system should throw exception
	 * 2. when the valid info is passed account should be created successfully
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		
		assertEquals(account, accountService.createAccount(101, 5000));
		
	}
	
	
	/*
	 * DepositAmount
	 * 1. When amount is zero , system should throw exception
	 * 2. when the valid amount is passed , amount should be deposited successfully
	 * 
	 */
	@Test(expected=com.capgemini.exceptions.InvalidAmountExcpetion.class)
	public void testForAmountZero() throws InvalidAmountExcpetion{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		accountService.depositAmount(account, 0);
	}
	
	@Test
	public void testForValidAmount() throws InvalidAmountExcpetion{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.depositAmount(account, 300);
		
		assertTrue(account.getAmount() >= 5300);
	}
	
	/*
	 * WithdrawAmount
	 * 1. when amount to be withdrawn is zeop, system should throw exception
	 * 2. When amount to be withdrawn is greater than balance, system should throw exception
	 * 3. when valid amount is passed, amount should be withdrawn 
	 * 
	 */
	@Test(expected = com.capgemini.exceptions.InvalidAmountExcpetion.class)
	public void testForAmountZeroForWithdraw() throws InvalidAmountExcpetion, InsufficientBalanceException{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		accountService.withdrawAmount(account, 0);
		
	}
	
	@Test(expected = com.capgemini.exceptions.InsufficientBalanceException.class)
	public void testForInvalidAmountForWithdraw() throws InvalidAmountExcpetion, InsufficientBalanceException{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		accountService.withdrawAmount(account, 7000);
	}
	
	@Test
	public void testForValidAmountForWithdraw() throws InvalidAmountExcpetion, InsufficientBalanceException{
		Account account = new Account();
		
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.withdrawAmount(account, 3000);
		
		assertTrue(account.getAmount()<5000);
		assertTrue(account.getAmount()==2000);
		
	}
	
}
