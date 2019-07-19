package com.ivsucic.shorting.url.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ivsucic.shorting.url.dao.AccountDao;
import com.ivsucic.shorting.url.dao.ShortingUrlDao;
import com.ivsucic.shorting.url.entities.Account;
import com.ivsucic.shorting.url.entities.ShortingUrl;
import com.ivsucic.shorting.url.request.RegisterUrlReq;
import com.ivsucic.shorting.url.response.RegisterUrlResponse;

@Service
public class ShortingUrlService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ShortingUrlDao shortingDao;
	
	public RegisterUrlResponse registerUrl(String authorization, RegisterUrlReq regUrlReq) {
		RegisterUrlResponse regUrlResponse = new RegisterUrlResponse();
		regUrlResponse.setShortUrl(null);
		String accountId = this.authenticateUser(authorization);
		if ( accountId != null) {
			ShortingUrl shortingUrl = new ShortingUrl();
			
			shortingUrl.setAccountId(accountId);
			shortingUrl.setOriginalUrl(regUrlReq.url);
			shortingUrl.setRedirectType(regUrlReq.redirectType);
			String hash = Integer.toString(regUrlReq.url.hashCode());
			String shortUrl = "/shorted/" + hash;
			shortingUrl.setShortUrl(shortUrl);
			shortingDao.save(shortingUrl);
			
			regUrlResponse.setShortUrl(shortUrl);
		}
		return regUrlResponse;
	}
	
	// if successfully authenticated the user return accountId else null
	private String authenticateUser(String authorization) {
		if (authorization.startsWith("Basic ")) {
			String encoded = authorization.substring(6);
			String decoded = new String(Base64.getDecoder().decode(encoded));
			Integer indexColon = decoded.indexOf(":");
			String accountId = decoded.substring(0, indexColon);
			String password = decoded.substring(indexColon+1, decoded.length());
			Optional<Account> account = accountDao.findById(accountId);
			if (account.isEmpty()) {
				return null;
			}
			Account accountDb = account.get();
			if (password.equals(accountDb.getPassword())) {
				return accountId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
