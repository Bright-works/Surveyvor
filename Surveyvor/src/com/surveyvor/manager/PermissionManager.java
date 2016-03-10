package com.surveyvor.manager;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.surveyvor.model.User;

@Service
@Qualifier(value="permissionManager")
public class PermissionManager implements UserDetailsService{

	@Autowired
	UserManager manager;
	
	public PermissionManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		User myuser = null;
		Collection<GrantedAuthority> arrayAuths = new ArrayList<GrantedAuthority>();
		try { 
			myuser = manager.findByMail(login); 
			} 
		catch (Exception ex) { throw new UsernameNotFoundException("User " + login+ " not exists", ex); 
			}
		String role= myuser.getAdmin();
		arrayAuths.add(new SimpleGrantedAuthority(role));
		return new org.springframework.security.core.userdetails.User(login,myuser.getPassword(),true,true,true,true,
				arrayAuths);
	}

	public UserManager getManager() {
		return manager;
	}

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

}
