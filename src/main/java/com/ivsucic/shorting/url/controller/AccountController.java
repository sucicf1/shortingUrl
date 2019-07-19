package com.ivsucic.shorting.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ivsucic.shorting.url.request.AccountReq;
import com.ivsucic.shorting.url.response.AccountResponse;
import com.ivsucic.shorting.url.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping(value="/account", produces = "application/json")
	public @ResponseBody AccountResponse newAccount(@RequestBody AccountReq accReq)  {
		return accountService.addAccount(accReq);
	}
}
