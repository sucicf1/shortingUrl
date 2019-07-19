package com.ivsucic.shorting.url.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivsucic.shorting.url.dao.AccountDao;
import com.ivsucic.shorting.url.entities.Account;
import com.ivsucic.shorting.url.request.AccountReq;
import com.ivsucic.shorting.url.response.AccountResponse;

@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;
	
	public AccountResponse addAccount(AccountReq accReq) {
		AccountResponse accResponse = new AccountResponse();
		String id = accReq.AccountId;
		Optional<Account> account = accountDao.findById(id);
		if(account.isEmpty()) {
			Account accountDb = new Account();
			accountDb.setAccountId(id);
			accountDb.setPassword(this.randomString());
			accountDao.save(accountDb);
			
			accResponse.setPassword(accountDb.getPassword());
			accResponse.setDescription("Your account is opened");
			accResponse.setSuccess(true);
			return accResponse;
		} else {
			accResponse.setSuccess(false);
			accResponse.setDescription("Accout with that accountId already exists.");
			return accResponse;
		}
	}

	private String randomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnoprstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
	}
}
