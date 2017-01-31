package org.fhirappcloud.controllers;

import org.fhirappcloud.domain.User;
import org.fhirappcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/create")
	  @ResponseBody
	  public String create(String email, String name) {
	    User user = null;
	    try {
	      user = new User(email, name);
	      userService.save(user);
	    }
	    catch (Exception ex) {
	      return "Error creating the user: " + ex.toString();
	    }
	    return "User succesfully created! (id = " + user.getId() + ")";
	  }
 
	
	
	@RequestMapping("/secure")
	public String secure(Model model ){
 		    model.addAttribute("success", true);
			return "secure";
 	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
