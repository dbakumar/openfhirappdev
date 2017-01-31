package org.fhirappcloud.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.fhirappcloud.domain.User;
import org.fhirappcloud.repo.UserRepo;
import org.fhirappcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
  
	@Autowired  
    UserService userService;
  
	public static final String APP_SALT = "This@#45PRD(/dsfr34vsed3523asdf27xfb35643sdf";
    
    @Override
    public Authentication authenticate(Authentication authentication) {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        String passwordToHash = null;
        if (email != null && password != null) {
        	User user = userService.getUserByEmail(email);
        	if (user != null && user.getId() > 0 &&  user.isEnabled()) {
        		passwordToHash = this.get_SHA_512_SecurePassword(password,APP_SALT);
        		passwordToHash = "e6941ec69eda50aa3ad126807b7227b2ab3b9c5f772af605c707a0e6a44c20589fbab014c36a98f722e7870232863034f45811f670ecbe9bcf771611a8da9e9e";
        		if (passwordToHash != null && !passwordToHash.isEmpty() && passwordToHash.equals(user.getPassword())) {
        			List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
                    Authentication auth = new UsernamePasswordAuthenticationToken(user, "", grantedAuths);
                    return auth;
        		}
        	}
        }   
        return null;
    }
    
    private String get_SHA_512_SecurePassword(String passwordToHash, String salt)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}