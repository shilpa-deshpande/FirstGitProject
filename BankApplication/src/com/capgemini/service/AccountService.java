package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAmountExcpetion;
import com.capgemini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException;
	
	Account depositAmount(Account account, int amount) throws InvalidAmountExcpetion; 
	
	Account withdrawAmount(Account account, int amount) throws InvalidAmountExcpetion,InsufficientBalanceException;
	
	void showBalance(int accountNumber , int amount);

}