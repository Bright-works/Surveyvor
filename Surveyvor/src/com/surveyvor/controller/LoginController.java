package com.surveyvor.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
	
	public String connecter(){
		
		return "/user/acceuil.xhtml?faces-rediect=true";
	}
	
	//--------------------- getter and setters -------------//
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	

}
