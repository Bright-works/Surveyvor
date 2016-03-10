package com.surveyvor.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.UserManager;
import com.surveyvor.model.*;


@SessionScoped
@Component("userBean")
public class UserController implements Serializable {
	
	@Autowired
	UserManager userManager;
	
	private static final long serialVersionUID = 1L;
	
	
	@ManagedProperty(value="#{loginBean}")
	private LoginController login;
	
	private User user=new User();
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}

	
	//------------------------methodes et fonctions ----->

	public String login() throws ServletException, IOException{
		user=userManager.findByMail(user.getMail());
		return login.connecter();
	}

	//------------------------getters and setters ----->
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public UserManager getUsermanager() {
		return userManager;
	}


	public void setUsermanager(UserManager usermanager) {
		this.userManager = usermanager;
	}
	
	
}
