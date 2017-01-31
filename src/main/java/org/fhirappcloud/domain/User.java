package org.fhirappcloud.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.fhirappcloud.config.Role;

@Entity
@Table(name = "users")
public class User {

	public User(){
		
	}
	
	public User(String email, String name){
		this.email = email;
		this.name = name;
 	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@NotNull
	private String email;
	
	@NotNull
	private String name;
	
	
	private String password;
	
	@NotNull
	private boolean enabled = true;  
	
	@NotNull
	private String role = Role.USER.toString();
	  
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user" ,targetEntity = App.class)
	private List<App> apps;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}
}
