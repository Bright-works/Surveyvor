package com.surveyvor.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.*;


@Component("userBean")
@Scope("session")
public class UserController implements Serializable {
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	SurveyManager surveyManager;
	
	private static final long serialVersionUID = 1L;
	
	private boolean conneted;
	private User user=new User();
	private Survey survey=new Survey();
	private List<Choice> choix=new ArrayList<Choice>();
	private Question question=new Question();
	private List<Question> allquestion=new ArrayList<Question>();
	private SurveyParameters parameters=new SurveyParameters();
	private List<String> diffusion= new ArrayList<String>();
	private String email="";
	private List<Survey> mySurvy=new ArrayList<Survey>();
	
	

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	//------------------------methodes et fonctions ----->

	@PostConstruct
	public void init(){
		addNewQuestion();
		addNewChoice();
		addNewChoice();
	}
	public TypeSurvey[] getTypes() {
        return TypeSurvey.values();
    }
	public void addNewQuestion(){
		Question q=new Question();
		QuestionParameters param= new QuestionParameters();
		q.setDescription("Empty description");
		q.setMaxChoice(1);
		q.setMinChoice(1);
		q.setSurvey(survey);
		q.setChoices(choix);
		//-----
		param.setRequested(true);
		param.setWritable(true);
		param.setSeveralAnswers(false);
		q.setParametres(param);
		allquestion.add(q);
	}
	public void addNewChoice(){
		Choice choice=new Choice();
		choice.setLabel("");
		choice.setDescription("Non description");
		choix.add(choice);
	}
	public void deleteChoice(Choice choice){
		choix.remove(choice);
	}
	public void deleteQuestion(Question question){
		allquestion.remove(question);
	}
	public boolean verifierEmail(String mail){
		Pattern pattern= Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher=pattern.matcher(mail);
		if(matcher.matches()){
			return true;}
		else return false;
	}
	public void addEmail(){
		if(verifierEmail(email)){
			diffusion.add(email);
			email="";
			}
		else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le format d'adresse mail n'est pas correct !",""));
		}
	}
	public String addNewSurvey(){
		if(survey.getType()==TypeSurvey.OPINION){
			parameters.setPrivateSurvey(false);
		}
		else{
			allquestion.get(0).setChoices(choix);
		}
		parameters.setAlgo(0);
		survey.setParametres(parameters);
		survey.setDiffusion(diffusion);
		survey.setCreator(user);
		survey.setQuestions(allquestion);
		//try{
			surveyManager.addSurvey(survey);
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sondage est bien enregistré !",""));
		    survey=new Survey();
			choix=new ArrayList<Choice>();
			question=new Question();
			allquestion=new ArrayList<Question>();
			parameters=new SurveyParameters();
			diffusion= new ArrayList<String>();
			email="";
		    
		/*}
		catch(Exception exp){
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, exp.getMessage(),""));
		} */
		return "../created.xhtml?faces-redirect=true";
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

	public SurveyManager getSurveyManager() {
		return surveyManager;
	}

	public void setSurveyManager(SurveyManager surveyManager) {
		this.surveyManager = surveyManager;
	}

	public List<Survey> getMySurvy() {
		return mySurvy;
	}

	public void setMySurvy(List<Survey> mySurvy) {
		this.mySurvy = mySurvy;
	}
	public boolean isConneted() {
		return conneted;
	}

	public void setConneted(boolean conneted) {
		this.conneted = conneted;
	}
	
}
