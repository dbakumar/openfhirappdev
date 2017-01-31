package org.fhirappcloud.service;


import java.util.List;

import org.fhirappcloud.domain.App;
import org.fhirappcloud.repo.AppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppService {
	@Autowired
	private AppRepo appRepo;
	
	public void save(App app) {
		appRepo.save(app);
 	}
	
	public App getApp(Long appId){
		return appRepo.findOne(appId);
	}
	
	public List<App> appList(){
		return (List<App>) appRepo.findAll();
	}
	

}
