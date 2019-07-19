package com.ivsucic.shorting.url.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.ivsucic.shorting.url.entities.ShortingUrl;;

public interface ShortingUrlDao extends CrudRepository<ShortingUrl,String> {
	List<ShortingUrl> findShortingUrlByAccountId(String AccountId);
	//ShortingUrl findByShortUrl(String shortUrl);
}
