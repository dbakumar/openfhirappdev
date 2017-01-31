package org.fhirappcloud.service;


import org.fhirappcloud.domain.User;
import org.fhirappcloud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
 	public void save(User user) {
		userRepo.save(user);
 	}
	
	public User getUser(Long id){
		return userRepo.findOne(id);
	}
	 
	public User getUserByEmail(String email){
		return userRepo.findByEmail(email);
	}
	

}
