package com.surveyvor.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.PermissionManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.User;

@Component("loginBean")
@Scope("request")
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user = new User();
	
	@Autowired
	private UserController login;
	
	@Autowired
	UserManager userManager;
	
	@Autowired 
	private PermissionManager permissionManager;
	
	private String verifPassword="";
	
	public LoginController() {
	}
	
	//-------------------- Action et methodes -------------//
	
	public String connecter() throws ServletException, IOException{
		try{
			user=userManager.findByMail(user.getMail());
		login.setUser(user);
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	    RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
	                .getRequestDispatcher("/j_spring_security_check");
	    dispatcher.forward((ServletRequest) context.getRequest(),
	                (ServletResponse) context.getResponse());
	    FacesContext.getCurrentInstance().responseComplete(); 
		
	    return null;
		}
		catch(NoResultException exp){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalid !",""));
		
		}
		return null;
		
	}
	
	public String logout(){
		user=new User();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        SecurityContextHolder.clearContext();
        return "/index.xhtml?faces-redirect=true";
    }
	
	public String register() throws ServletException, IOException{
		if(!this.verifPassword.equals(user.getPassword())){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Les mots de passe ne sont pas identiques !",""));
		}
		else{
			try{
			User existed=userManager.findByMail(user.getMail());
			if(existed.getId()>0){
				user=new User();
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "L'adresse email est déja utilisé !",""));	
				}
			}
			catch(NoResultException expt){
				//RequestContext.getCurrentInstance().execute("PF('small-dialog').show();");
				user.setAdmin("ROLE_USER");
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(user.getPassword()));
				userManager.add(this.user);
				FacesContext facesContext = FacesContext.getCurrentInstance();
			    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Vous êtes bien inscrit!",""));	
			    try {
			        UserDetails userDetails = permissionManager.loadUserByUsername(user.getMail());
			        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, verifPassword, userDetails.getAuthorities());
			        SecurityContextHolder.getContext().setAuthentication(auth);
			        return "/user/acceuil.xhtml";
			        
			      } catch (Exception e) {
					  facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,e.getMessage(),""));	
					   
			      }
			}
		}
		return null;
	}
	
	//--------------------- getter and setters -------------//
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public String getVerifPassword() {
		return verifPassword;
	}

	public void setVerifPassword(String verifPassword) {
		this.verifPassword = verifPassword;
	}

}
