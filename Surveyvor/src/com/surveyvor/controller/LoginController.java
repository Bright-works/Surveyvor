package com.surveyvor.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.surveyvor.model.User;

@ManagedBean(name="loginBean")
@SessionScoped
@Component
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user = new User();
	
	
	public LoginController() {
	}
	
	//-------------------- Action et methodes -------------//
	
	public String connecter() throws ServletException, IOException{
		
		 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
	                .getRequestDispatcher("/j_spring_security_check");
	        dispatcher.forward((ServletRequest) context.getRequest(),
	                (ServletResponse) context.getResponse());
	        FacesContext.getCurrentInstance().responseComplete();
	        return null;
	}
	
	public String logout(){
        SecurityContextHolder.clearContext();
        return "/index.xhtml?faces-redirect=true";
    }
	//--------------------- getter and setters -------------//
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	

}
