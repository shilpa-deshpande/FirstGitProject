package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAmountExcpetion;
import com.capgemini.model.Account;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialBalanceException
	{
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
		
	}
	
	@Override
	public Account depositAmount(Account account,int amount) throws InvalidAmountExcpetion{
			if ((amount == 0)||(amount < 0)){
				throw new InvalidAmountExcpetion();
			}
		try{
			int amt = account.getAmount();
			int newBalance = amt + amount;
			account.setAmount(newBalance);
			}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
			if(accountRepository.save(account))
			{
				return account;
			}
			
			return null;
	}
	
	@Override
	public Account withdrawAmount(Account account,int amount) throws InvalidAmountExcpetion,InsufficientBalanceException{
		if ((amount == 0)||(amount < 0)){
			throw new InvalidAmountExcpetion();
		}
		try{
			int amt = account.getAmount();
			if ((amt< amount)||((amt-amount)<500)){
				throw new InsufficientBalanceException();
			}
			int newBalance = amt-amount;
			account.setAmount(newBalance);
			}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}
	
	@Override
	public void showBalance(int accountNumber,int amount){
		
	}

}
