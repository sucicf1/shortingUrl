package com.ivsucic.shorting.url.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Account {
	@Id
	@Column(nullable=false)
	private String accountId;
	@Column(nullable=false)
	private String password;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
