package org.telran.forum.accounting.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.telran.forum.accounting.model.UserAccount;

@Repository
public interface AccountRepository extends CrudRepository<UserAccount, String> {
	
}