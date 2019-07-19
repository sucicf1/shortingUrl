package com.ivsucic.shorting.url.dao;

import org.springframework.data.repository.CrudRepository;
import com.ivsucic.shorting.url.entities.ShortingUrl;;

public interface ShortingUrlDao extends CrudRepository<ShortingUrl,String> {
	
}
