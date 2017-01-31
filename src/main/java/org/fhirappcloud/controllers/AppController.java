package org.fhirappcloud.controllers;

import java.util.List;

import org.fhirappcloud.domain.App;
import org.fhirappcloud.domain.User;
import org.fhirappcloud.service.AppService;
import org.fhirappcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class AppController {
	@Autowired
	private UserService userService;
	@Autowired
	private AppService appService;
 	
	@RequestMapping("/create")
	public String createApp(String name, String oauthKey, Model model ){
		    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		   // User currUser = (User) auth.getPrincipal();
		    Long userId= 1L;
		    // For testing 
		    //if (currUser == null) {userId = 1L;}
		    User user = userService.getUser(userId);
		    App newApp = new App();
		    newApp.setName(name);
		    newApp.setOauthKey(oauthKey);
		    newApp.setUser(user);
		    appService.save(newApp);
 		    model.addAttribute("success", true);
			return "app/create";
 	}
	
	
	@RequestMapping("/list")
	public String listApp(String name, String oauthKey, Model model ){
		    //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		   // User currUser = (User) auth.getPrincipal();
		    Long userId= 1L;
		    // For testing 
		    //if (currUser == null) {userId = 1L;}
		    User user = userService.getUser(userId);
		    List<App> appList =  user.getApps();
 		    model.addAttribute("appList", appList);
			return "app/list";
 	}
	
	@RequestMapping(value="/{appId}/code")
	public String showProfilePathVar(@PathVariable("appId") String appId,  Model model){ 
		return "app/codeeditor";
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
