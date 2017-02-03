package org.fhirappcloud.domain;

import org.fhirappcloud.util.Utility;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
 

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientFHIRAccess{
 
	@JsonProperty("access_token")
	String accessToken;
	
	@JsonProperty("token_type")
	String tokenType;
	
	@JsonProperty("expires_in")
	String expiresIn;
	
	@JsonProperty("scope")
	String scope;
	
	@JsonProperty("state")
	String state;
	
	@JsonProperty("patient")
	String patient;
	
 

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}
	
 
 
}
