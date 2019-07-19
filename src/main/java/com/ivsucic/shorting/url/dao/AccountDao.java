package com.ivsucic.shorting.url.dao;

import org.springframework.data.repository.CrudRepository;
import com.ivsucic.shorting.url.entities.Account;

public interface AccountDao extends CrudRepository<Account,String> {
	
}
