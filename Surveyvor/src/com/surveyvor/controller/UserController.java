package com.surveyvor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
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
	private Survey survey=new Survey();
	private List<Choice> choix=new ArrayList<Choice>();
	private Question question=new Question();
	private SurveyParameters parameters=new SurveyParameters();
	
	

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	//------------------------methodes et fonctions ----->

	@PostConstruct
	public void init(){
		choix.add(new Choice());
		choix.add(new Choice());
	}
	public TypeSurvey[] getTypes() {
        return TypeSurvey.values();
    }

	public void addNewChoice(){
		choix.add(new Choice());
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

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public LoginController getLogin() {
		return login;
	}

	public void setLogin(LoginController login) {
		this.login = login;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<Choice> getChoix() {
		return choix;
	}

	public void setChoix(List<Choice> choix) {
		this.choix = choix;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public SurveyParameters getParameters() {
		return parameters;
	}

	public void setParameters(SurveyParameters parameters) {
		this.parameters = parameters;
	}
}
