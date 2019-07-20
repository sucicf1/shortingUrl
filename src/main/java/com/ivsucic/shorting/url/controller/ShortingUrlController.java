package com.ivsucic.shorting.url.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ivsucic.shorting.url.request.RegisterUrlReq;
import com.ivsucic.shorting.url.response.RegisterUrlResponse;
import com.ivsucic.shorting.url.service.ShortingUrlService;

@Controller
public class ShortingUrlController {
	@Autowired
	private ShortingUrlService shortingUrlService;
	
	@PostMapping(value="/register", produces = "application/json")
	public @ResponseBody RegisterUrlResponse registerUrl(HttpServletRequest request,
														 @RequestHeader("Authorization") String authorization, 
														 @RequestBody RegisterUrlReq regUrlReq)  {
		return shortingUrlService.registerUrl(request, authorization, regUrlReq);
	}
	
	@GetMapping(value="/statistic/{AccountId}", produces = "application/json")
	public @ResponseBody Map<String, Integer> getStatistics(@RequestHeader("Authorization") String authorization, 
															@PathVariable("AccountId") String AccountId)  {
		return shortingUrlService.getStatistics(authorization, AccountId);
	}
	
	@GetMapping(value="/s/{hash}")
	public void shortedRedirect(@PathVariable("hash") String hash, HttpServletResponse httpServletResponse) {
		shortingUrlService.setRedirectResponse(hash, httpServletResponse);
	}
}
