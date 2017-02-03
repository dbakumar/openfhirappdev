package org.fhirappcloud.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.fhirappcloud.domain.App;
import org.fhirappcloud.domain.PatientFHIRAccess;
import org.fhirappcloud.domain.User;
import org.fhirappcloud.httpclient.service.OauthHTTPService;
import org.fhirappcloud.service.AppService;
import org.fhirappcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fhirclient")
public class FhirClientController {
	@Autowired
	private HttpServletRequest httpRequest;
	
	@Autowired
	private OauthHTTPService httpOAuthService;
	
	@Autowired
	private AppService appService;
	
	@RequestMapping("/test")
	  public String login(Model model) {
	    return "fhirclient/test";
	  }
	
	@RequestMapping(value="/app/{appId}/launch")
	public String showProfilePathVar(@PathVariable("appId") String appId, String launch, String iss, Model model, HttpSession session){
		App app = appService.getApp(Long.parseLong(appId));
		
		PatientFHIRAccess patientFHIRAccess = httpOAuthService.getAuthCode("https://open-ic.epic.com/Argonaut/oauth2/authorize", app.getOauthKey(), "http://localhost:8080/fhirclient/app/1/redirect", launch, "testvijaystate");
	    //System.out.println(httpRequest.getPathInfo()); 
	   // System.out.println(httpRequest.getQueryString());
	    System.out.println(patientFHIRAccess.getAccessToken());
	 
	    session.setAttribute("patientFHIRAccess" ,patientFHIRAccess);
	    model.addAttribute( "patientFHIRAccess" ,patientFHIRAccess);
	    
	    return "redirect:/fhirclient/app/execute";
	}
	
	@RequestMapping(value="/app/execute")
	public String executeClientApp(  Model model, HttpSession session){
		
		 PatientFHIRAccess patientFHIRAccess = (PatientFHIRAccess) session.getAttribute("patientFHIRAccess");
		 model.addAttribute( "patientFHIRAccess" ,patientFHIRAccess);
		return "/fhirclient/test";
	}
	
	@RequestMapping(value="/app/{appId}/redirect")
	public String showProfilePathVar(@PathVariable("appId") String appId , Model model){
 	    System.out.println(httpRequest.getPathInfo()); 
	    System.out.println(httpRequest.getQueryString());
	    return "test";
	}
	
	
	
	
}
