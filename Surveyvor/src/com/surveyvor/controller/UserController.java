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
import com.surveyvor.model.Choice;
import com.surveyvor.model.Question;
import com.surveyvor.model.QuestionParameters;
import com.surveyvor.model.Survey;
import com.surveyvor.model.SurveyParameters;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;

@Component("userBean")
@Scope("session")
public class UserController implements Serializable {

	@Autowired
	UserManager userManager;

	@Autowired
	SurveyManager surveyManager;

	private static final long serialVersionUID = 1L;

	private User user = new User();
	private Survey survey = new Survey();
	private Question question = new Question();
	private List<Question> allquestion = new ArrayList<Question>();
	private SurveyParameters parameters = new SurveyParameters();
	private List<String> diffusion = new ArrayList<String>();
	private String email = "";
	private List<Survey> mySurvy = new ArrayList<Survey>();
	private List<String> selectedQuestionType = new ArrayList<String>();

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	// ------------------------methodes et fonctions ----->

	@PostConstruct
	public void init() {
		addNewQuestion();
		addNewChoice(allquestion.get(0));
		addNewChoice(allquestion.get(0));
		selectedQuestionType.add("0");
	}

	public TypeSurvey[] getTypes() {
		return TypeSurvey.values();
	}

	public void addNewQuestion() {
		Question q = new Question();
		QuestionParameters param = new QuestionParameters();
		q.setDescription("Empty description");
		q.setMaxChoice(1);
		q.setMinChoice(1);
		q.setSurvey(survey);
		q.setChoices(new ArrayList<Choice>());
		// -----
		param.setRequested(true);
		param.setWritable(false);
		param.setSeveralAnswers(false);
		q.setParametres(param);
		selectedQuestionType.add("0");
		allquestion.add(q);
	}

	public void addNewChoice(Question q) {
		Choice choice = new Choice();
		choice.setLabel("");
		choice.setDescription("Non description");
		q.addChoice(choice);
	}

	public void deleteChoice(Question q, Choice choice) {
		q.removeChoice(choice);
	}

	public void deleteQuestion(Question question) {
		int i = allquestion.lastIndexOf(question);
		selectedQuestionType.remove(i);
		allquestion.remove(question);
	}

	public void addEmail() {
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			diffusion.add(email);
			email = "";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le format d'adresse mail n'est pas correct !", ""));
		}
	}

	public String validateMinMaxChoices() {
		if (survey.getType() == TypeSurvey.REPARTITION) {
			int minChoice = allquestion.get(0).getMinChoice();
			int maxChoice = allquestion.get(0).getMaxChoice();
			System.out.println("MIN/MAX: " + minChoice + "/" + maxChoice);
			
			if (minChoice > maxChoice || maxChoice > allquestion.get(0).getChoices().size()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Veuillez rentrer des valeurs minimales et maximales correctes", "minWish"));
				return "2.xhtml";
			} else {
				
				//If maxchoice > 1 -> severalChoice = true
				if(maxChoice>1){
					allquestion.get(0).getParametres().setSeveralAnswers(true);
				}
				
				return "3.xhtml?faces-redirect=true";
			}
		} else {
			int i=0;
			boolean error=false;
			while(i<allquestion.size() && !error){
				int minChoice = allquestion.get(i).getMinChoice();
				int maxChoice = allquestion.get(i).getMaxChoice();
				System.out.println("MIN/MAX: " + minChoice + "/" + maxChoice);
				System.out.println("SELECTED TYPE: "+ selectedQuestionType.get(i));
				
				switch(selectedQuestionType.get(i)){
				case "0": {
					allquestion.get(i).getParametres().setWritable(false);
					allquestion.get(i).getParametres().setSeveralAnswers(true);
					
					if (minChoice > maxChoice || maxChoice > allquestion.get(i).getChoices().size()) {
						error=true;
					} 
				}
					break;
				case "1": {
					allquestion.get(i).getParametres().setWritable(false);
					allquestion.get(i).getParametres().setSeveralAnswers(false);
				}
					break;
				case "2": {
					allquestion.get(i).getParametres().setWritable(true);
					allquestion.get(i).getParametres().setSeveralAnswers(false);
					allquestion.get(i).setChoices(new ArrayList<Choice>());
				}
					break;
				}
				
				i++;

			}
			if (error) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Veuillez rentrer des valeurs minimales et maximales correctes", "minChoice"+i));
				return "2.xhtml";
			} else {
				return "3.xhtml?faces-redirect=true";
			}
		}
	}

	public String addNewSurvey() {

		if (survey.getType() == TypeSurvey.REPARTITION) {
			parameters.setPrivateSurvey(true);
		}
		
		parameters.setAlgo(-1);
		survey.setParametres(parameters);
		survey.setDiffusion(diffusion);
		survey.setCreator(user);
		survey.setQuestions(allquestion);
		surveyManager.addSurvey(survey);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Le sondage a bien été enregistré !", ""));
		survey = new Survey();
		question = new Question();
		allquestion = new ArrayList<Question>();
		parameters = new SurveyParameters();
		diffusion = new ArrayList<String>();
		email = "";
		return "../created.xhtml?faces-redirect=true";
	}

	// ------------------------getters and setters ----->

	public User getUser() {
		return user;
	}

	public List<String> getSelectedQuestionType() {
		return selectedQuestionType;
	}

	public void setSelectedQuestionType(List<String> selectedQuestionType) {
		this.selectedQuestionType = selectedQuestionType;
	}

	public void test() {
		System.out.println("dsdsdqsdqsdqsdqsdqsd");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalid !", ""));
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

}
