package com.ivsucic.shorting.url.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	public RegisterUrlResponse registerUrl(HttpServletRequest request, String authorization, RegisterUrlReq regUrlReq) {
		RegisterUrlResponse regUrlResponse = new RegisterUrlResponse();
		regUrlResponse.setShortUrl(null);
		String accountId = this.authenticateUser(authorization);
		if (accountId != null) {
			ShortingUrl shortingUrl = new ShortingUrl();

			shortingUrl.setAccountId(accountId);
			shortingUrl.setOriginalUrl(regUrlReq.url);
			if (regUrlReq.redirectType != 301 && regUrlReq.redirectType != 302) {
				shortingUrl.setRedirectType(302);
			} else {
				shortingUrl.setRedirectType(regUrlReq.redirectType);
			}
			String hash = Integer.toString(regUrlReq.url.hashCode());
			String shortUrl = "/s/" + hash;
			shortingUrl.setShortUrl(shortUrl);
			shortingDao.save(shortingUrl);

			try {
				URL requestURL;
				requestURL = new URL(request.getRequestURL().toString());
				String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
				regUrlResponse.setShortUrl(requestURL.getProtocol() + "://" + requestURL.getHost() + port + shortUrl);
			} catch (MalformedURLException e) {
				regUrlResponse.setShortUrl("Error");
			}
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
			String password = decoded.substring(indexColon + 1, decoded.length());
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

	public Map<String, Integer> getStatistics(String authorization, String accountId) {
		Map<String, Integer> hitCounts = new TreeMap<String, Integer>();

		String accountId2 = this.authenticateUser(authorization);
		if (accountId2 != null && accountId.equals(accountId2)) {
			List<ShortingUrl> shortingUrls = shortingDao.findShortingUrlByAccountId(accountId2);
			for (ShortingUrl shortingUrl : shortingUrls) {
				Map.Entry<String, Integer> entry = newEntry(shortingUrl.getOriginalUrl(), shortingUrl.getHitCount());
				hitCounts.put(entry.getKey(), entry.getValue());
			}
		}
		return hitCounts;
	}

	private static <K, V> Map.Entry<K, V> newEntry(K key, V value) {
		return new AbstractMap.SimpleEntry<>(key, value);
	}

	public void setRedirectResponse(String hash, HttpServletResponse httpServletResponse) {
		ShortingUrl shortingUrl = shortingDao.findShortingUrlByShortUrl("/s/" + hash);
		shortingUrl.increaseHitCountByOne();
		shortingDao.save(shortingUrl);
		httpServletResponse.setHeader("Location", shortingUrl.getOriginalUrl());
	    httpServletResponse.setStatus(shortingUrl.getRedirectType());
	}
}
