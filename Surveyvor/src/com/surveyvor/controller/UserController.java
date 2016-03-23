package com.surveyvor.controller;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.apache.commons.validator.routines.EmailValidator;
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
import com.surveyvor.service.MailSender;

@Component("userBean")
@Scope("session")
public class UserController implements Serializable {

	@Autowired
	UserManager userManager;

	@Autowired
	SurveyManager surveyManager;
	
	@Autowired
	MailSender mailSender;

	private static final long serialVersionUID = 1L;
	private boolean conneted;
	private User user = new User();
	private Survey survey = new Survey();
	private Question question = new Question();
	private List<Question> allquestion = new ArrayList<Question>();
	private SurveyParameters parameters = new SurveyParameters();
	private List<String> diffusion = new ArrayList<String>();
	private String email = "";
	private List<Survey> mySurvy = new ArrayList<Survey>();
	private List<String> selectedQuestionType = new ArrayList<String>();
	private Date currentDate = new Date();
	private Date nexDate = new Date();

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
		survey.setStartDate(new Date());
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(survey.getStartDate().getTime());
		cal.roll(Calendar.DAY_OF_YEAR, true);
		if(cal.get(Calendar.DAY_OF_YEAR)==1){
			cal.roll(Calendar.YEAR, true);
		}
		survey.setEndDate(new Date(cal.getTimeInMillis()));
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

	public boolean verifierEmail(String mail) {
		return EmailValidator.getInstance().isValid(mail);
	}

	public void addEmail() {
		boolean allgood = true;
		int i = 0;
		String[] tmp = email.trim().split(";");
		List<String> diffusionReal = new ArrayList<String>();
		System.out.println("Liste de diffusion: " + tmp.length + " " + email);
		while (i < tmp.length && allgood) {
			allgood = verifierEmail(tmp[i]);
			System.out.println(allgood);
			diffusionReal.add(tmp[i]);
			System.out.println(tmp[i]);
			i++;
		}
		if (allgood) {
			for (String mail : diffusionReal) {
				if (!diffusion.contains(mail)) {
					diffusion.add(mail);
				}
			}
			email = "";
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le format d'adresse mail n'est pas correct !", ""));
		}
	}

	public void deleteMail(String mail) {
		diffusion.remove(mail);
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

				// If maxchoice > 1 -> severalChoice = true
				if (maxChoice > 1) {
					allquestion.get(0).getParametres().setSeveralAnswers(true);
				}

				return "3.xhtml?faces-redirect=true";
			}
		} else {
			int i = 0;
			boolean error = false;
			while (i < allquestion.size() && !error) {
				int minChoice = allquestion.get(i).getMinChoice();
				int maxChoice = allquestion.get(i).getMaxChoice();
				System.out.println("MIN/MAX: " + minChoice + "/" + maxChoice);
				System.out.println("SELECTED TYPE: " + selectedQuestionType.get(i));

				switch (selectedQuestionType.get(i)) {
				case "0": {
					allquestion.get(i).getParametres().setWritable(false);
					allquestion.get(i).getParametres().setSeveralAnswers(true);

					if (minChoice > maxChoice || maxChoice > allquestion.get(i).getChoices().size()) {
						error = true;
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
						"Veuillez rentrer des valeurs minimales et maximales correctes", "minChoice" + i));
				return "2.xhtml";
			} else {
				return "3.xhtml?faces-redirect=true";
			}
		}
	}
	
	private void reset(){
		//Reset
		survey = new Survey();
		question = new Question();
		allquestion = new ArrayList<Question>();
		parameters = new SurveyParameters();
		diffusion = new ArrayList<String>();
		selectedQuestionType = new ArrayList<String>();
		addNewQuestion();
		addNewChoice(allquestion.get(0));
		addNewChoice(allquestion.get(0));
		selectedQuestionType.add("0");
		survey.setStartDate(new Date());
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(survey.getStartDate().getTime());
		cal.roll(Calendar.DAY_OF_YEAR, true);
		if(cal.get(Calendar.DAY_OF_YEAR)==1){
			cal.roll(Calendar.YEAR, true);
		}
		survey.setEndDate(new Date(cal.getTimeInMillis()));
		email = "";
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
		try {
			
			List<String> diffusion = survey.getDiffusion();
			
			surveyManager.addSurvey(survey);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Le sondage a bien �t� enregistr� !", ""));
			
			String title = survey.getTitle();
			reset();

			// Refresh public list on publicsurveycontroler
			FacesContext context = FacesContext.getCurrentInstance();
			PublicSurveyController psv = context.getApplication().evaluateExpressionGet(context, "#{publicSurveyBean}",
					PublicSurveyController.class);
			psv.refreshList();
			
			//Refresh user
			user = userManager.findUser(user.getId());

			List<Survey> ownedSurveys = user.getOwnedSurveys();
			Survey justAddedSurvey = new Survey();
			for(int i=0; i<ownedSurveys.size(); i++){
				if(ownedSurveys.get(i).getTitle().compareTo(title)==0){
					justAddedSurvey = ownedSurveys.get(i);
				}
			}
			justAddedSurvey.setDiffusion(diffusion);
			sendInvitationMails(justAddedSurvey);

			return "../created.xhtml?faces-redirect=true";

		} catch (Exception e) {
			System.out.println("Exception !!!!!");
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Il y a eu une erreur lors de l'envoi en base de donn�es,"
							+ " vous avez probablement oubli� de remplir des champs",
							""));
			e.printStackTrace();
			return "1.xhtml?faces-redirect=true";
		}
	}
	
	public void sendInvitationMails(Survey survey){
		try {
			String url = "http://"+Inet4Address.getLocalHost().getHostAddress()+":8080/Surveyvor/survey/reponse.xhtml";
			url = url + "?surveyId=" + survey.getId() + "&userId=";
			
			mailSender.sendInvitation(survey, url);
		} catch (UnknownHostException | MessagingException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Il y a eu une erreur lors de l'envoi des messages",
							""));
			e.printStackTrace();
		}
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
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Email ou mot de passe invalide !", ""));
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

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		System.out.println("YA TALKIN' TO ME?");
		currentDate = new Date();
		return currentDate;
	}

	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the date just after survey.startDate
	 */
	public Date getNexDate() {
		if(survey.getStartDate()!=null){
			System.out.println("YES I'M TALKIN' TO YOU!");
			Calendar cal = new GregorianCalendar();
			cal.setTimeInMillis(survey.getStartDate().getTime());
			cal.roll(Calendar.DAY_OF_YEAR, true);
			if(cal.get(Calendar.DAY_OF_YEAR)==1){
				cal.roll(Calendar.YEAR, true);
			}
			nexDate = new Date(cal.getTimeInMillis());
			if(survey.getEndDate().before(survey.getStartDate())){
				System.out.println("before");
				survey.setEndDate(nexDate);
			}
		}else{
			nexDate = new Date();
		}
		return nexDate;
	}

	/**
	 * @param nexDate the nexDate to set
	 */
	public void setNexDate(Date nexDate) {
		this.nexDate = nexDate;
	}

	public boolean isConneted() {
		return conneted;
	}

	public void setConneted(boolean conneted) {
		this.conneted = conneted;
	}

}
