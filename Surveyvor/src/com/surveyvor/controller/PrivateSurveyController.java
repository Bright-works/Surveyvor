package com.surveyvor.controller;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Question;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

@Controller
@Component("privateSurveyBean")
@Scope("request")
public class PrivateSurveyController {

	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private SurveyController surveyControler;
	
	private boolean ready = false;

	private boolean allgood = true;

	@PostConstruct
	public void init() {
		System.out.println("CREATING "+this);
		checkAccess();
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public PrivateSurveyController() {
		// TODO Auto-generated constructor stub
	}

	public boolean checkAccess() {
		System.out.println("Called before anything else");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		UserController userControler = context.getApplication().evaluateExpressionGet(context, "#{userBean}",
				UserController.class);

		if (params.get("surveyId") != null && params.get("userId") != null) {
			// Get survey id param in url
			Long surveyId = Long.valueOf(params.get("surveyId"));

			// Find corresponding survey
			Survey survey = surveyManager.findSurvey(surveyId);
			System.out.println("id : " + surveyId);
			System.out.println(survey);

			if (survey != null) {
				System.out.println(survey.getQuestions().size());

				survey.setDiffusion(surveyManager.getDiffusion(survey));
				for(String mail : survey.getDiffusion()){
					System.err.println(mail);
				}
				
				List<Question> questions = survey.getQuestions();
				List<Question> noduplicate = new ArrayList<Question>();
				for(Question question : questions){
					boolean duplicate = false;
					for(Question q : noduplicate){
						if(question.getId()==q.getId()){
							duplicate = true;
							break;
						}
					}
					if(!duplicate){
						noduplicate.add(question);
					}
				}
				survey.setQuestions(noduplicate);
				
				// Give it to the controler
				surveyControler.setSelected(survey);

				// Get the param user id in url
				String userId = params.get("userId");

				// If it is a private survey, we have to check if he has access
				// to
				// it
				if (survey.getParametres().getPrivateSurvey()) {

					// If he is already connected, he has to be the right one
					if (userControler.isConneted()) {
						User user = userControler.getUser();
						if (user == null || user.getMail()
								.compareTo(new String(Hex.decode(userId), StandardCharsets.UTF_8).trim()) != 0) {
							
							System.out.println(new String(Hex.decode(userId), StandardCharsets.UTF_8).trim());
							System.out.println(user.getMail());
							
							FacesContext facesContext = FacesContext.getCurrentInstance();
							facesContext.addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Vous n'Žtes pas autorisŽ d'accŽder ˆ ce sondage par ce lien. "
													+ "Veuillez vous connecter avec le bon compte.",
											""));
							survey = new Survey();
							surveyControler.setSelected(survey);
							allgood = false;
						}
						// If he is not, he has to connect
					} else {
						// TODO redirect to login??
						FacesContext facesContext = FacesContext.getCurrentInstance();
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Vous n'êtes pas autorisé à accéder à ce sondage. " + "Veuillez vous connecter.", ""));
						survey = new Survey();
						surveyControler.setSelected(survey);
						allgood = false;
					}
				}
			} else{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Nous n'avons pas trouvé le sondage auquel vous essayez d'accéder. ", ""));
			}

		} else {
			Survey survey = surveyControler.getSelected();
			if (survey == null) {
				System.out.println("selected is null");
				surveyControler.setSelected(new Survey());
			} else {
				if (survey.getParametres() != null && survey.getParametres().getPrivateSurvey()) {
					allgood = checkIfInDiffusion(userControler.getUser(), survey);
				}
			}
		}
		return allgood;
	}

	public boolean checkIfInDiffusion(User user, Survey survey) {
		boolean isIn = false;

		if (user != null) {
			List<String> diffusion = survey.getDiffusion();
			for (String mail : diffusion) {
				if (user.getMail().compareTo(mail) == 0) {
					isIn = true;
					break;
				}
			}
		} else {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Vous n'êtes pas autorisé à accéder à ce sondage. " + "Veuillez vous connecter avec le bon compte.",
					""));
		}
		return isIn;
	}

	/**
	 * @return the allgood
	 */
	public boolean isAllgood() {
		return allgood;
	}

	/**
	 * @param allgood
	 *            the allgood to set
	 */
	public void setAllgood(boolean allgood) {
		this.allgood = allgood;
	}


}
