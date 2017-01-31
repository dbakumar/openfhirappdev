package org.fhirappcloud.controllers;


import org.fhirappcloud.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 


@Controller
public class AppHome {
	
	 
	
	// Login form
	  @RequestMapping(value={"/","/login"})
	  public String login(Model model) {
	    return "login";
	  }

	  // Login form with error
	  @RequestMapping("/login-error")
	  public String loginError(Model model) {
	    model.addAttribute("loginError", true);
	    return "login";
	  }
	
	  @RequestMapping( "/home")
	public String home(Model model){
		    model.addAttribute("success", true);
		    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    User user = (User) auth.getPrincipal();
		    model.addAttribute("useremail", user.getEmail() );
			return "home";
	}
 
}
