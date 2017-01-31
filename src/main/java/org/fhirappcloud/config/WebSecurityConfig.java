package org.fhirappcloud.config;

import org.fhirappcloud.auth.CustomAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private CustomAuthProvider customAuthProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().ignoringAntMatchers("/fhirclient/**", "/user/create", "/app/create");
    	http.authorizeRequests().antMatchers("/fhirclient/**", "/login", "/login-error", "/user/create", "/app/create").permitAll().anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .defaultSuccessUrl("/app/list")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().rememberMe();
    }
   
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(customAuthProvider);
    	/*
    	auth.userDetailsService(new CustomUserAuthService());  
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");*/
    } 
}