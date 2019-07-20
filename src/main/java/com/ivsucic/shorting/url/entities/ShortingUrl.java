package com.ivsucic.shorting.url.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class ShortingUrl {
	@Id
	@Column(nullable=false)
	private String originalUrl;

	@Column(nullable=false)
	private String accountId;

	@Column(nullable=false)
	private String shortUrl;
	
	private Integer hitCount = 0;
	private Integer redirectType;
	
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Integer getHitCount() {
		return hitCount;
	}
	public void increaseHitCountByOne() {
		++this.hitCount;
	}
	public Integer getRedirectType() {
		return redirectType;
	}
	public void setRedirectType(Integer redirectType) {
		this.redirectType = redirectType;
	}
}
