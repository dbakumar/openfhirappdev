package org.fhirappcloud.repo;

import org.fhirappcloud.domain.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Long>{
	 User findByName(String name);
	 User findByEmail (String email);
 }
