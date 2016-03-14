package com.surveyvor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.UserManager;
import com.surveyvor.model.*;


@Component("userBean")
@Scope("session")
public class UserController implements Serializable {
	
	@Autowired
	UserManager userManager;
	
	private static final long serialVersionUID = 1L;
	
	
	private User user=new User();
	private Survey survey=new Survey();
	private List<Choice> choix=new ArrayList<Choice>();
	private Question question=new Question();
	private List<Question> allquestion=new ArrayList<Question>();
	private SurveyParameters parameters=new SurveyParameters();
	private List<String> diffusion= new ArrayList<String>();
	private String email="";
	
	

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	//------------------------methodes et fonctions ----->

	@PostConstruct
	public void init(){
		allquestion.add(new Question());
		choix.add(new Choice());
		choix.add(new Choice());
	}
	public TypeSurvey[] getTypes() {
        return TypeSurvey.values();
    }

	public void addNewQuestion(){
		allquestion.add(new Question());
	}
	public void addNewChoice(){
		choix.add(new Choice());
	}
	public void deleteChoice(Choice choice){
		choix.remove(choice);
	}
	public void deleteQuestion(Question question){
		allquestion.remove(question);
	}
	public void addEmail(){
		diffusion.add(email);
		email="";
	}
	//------------------------getters and setters ----->
	public User getUser() {
		return user;
	}
	public void test(){
		System.out.println("dsdsdqsdqsdqsdqsdqsd");
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalid !",""));
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

	public List<Question> getAllquestion() {
		return allquestion;
	}

	public void setAllquestion(List<Question> allquestion) {
		this.allquestion = allquestion;
	}

	public List<String> getDiffusion() {
		return diffusion;
	}

	public void setDiffusion(List<String> diffusion) {
		this.diffusion = diffusion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
