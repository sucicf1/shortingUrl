package com.ivsucic.shorting.url.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ivsucic.shorting.url.request.RegisterUrlReq;
import com.ivsucic.shorting.url.response.RegisterUrlResponse;
import com.ivsucic.shorting.url.service.ShortingUrlService;

@Controller
public class RegisterUrlController {
	@Autowired
	private ShortingUrlService shortingUrlService;
	
	@PostMapping(value="/register", produces = "application/json")
	public @ResponseBody RegisterUrlResponse registerUrl(@RequestHeader("Authorization") String authorization, 
														 @RequestBody RegisterUrlReq regUrlReq)  {
		return shortingUrlService.registerUrl(authorization, regUrlReq);
	}
}
