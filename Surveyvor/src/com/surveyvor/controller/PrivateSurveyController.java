package com.surveyvor.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

@Controller
@Component("privateSurveyBean")
@Scope("request")
@RequestMapping(value = "/survey")
public class PrivateSurveyController extends SurveyController {

	@Autowired
	private SurveyManager surveyManager;

	private boolean ready = false;

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
		boolean allgood = true;
		System.out.println("Called before anything else");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();

		SurveyController surveyControler = context.getApplication().evaluateExpressionGet(context, "#{surveyBean}",
				SurveyController.class);

		if (!params.isEmpty() && params.get("surveyId")!=null) {
			// Get survey id param in url
			Long surveyId = Long.valueOf(params.get("surveyId"));

			// Find corresponding survey
			Survey survey = surveyManager.findSurvey(surveyId);
			System.out.println("id : " + surveyId);
			System.out.println(survey);

			// Give it to the controler
			surveyControler.setSelected(survey);

			// Get the param user id in url
			String userId = params.get("userId");

			// If it is a private survey, we have to check if he has access to
			// it
			if (survey.getParametres().getPrivateSurvey()) {

				UserController userControler = context.getApplication().evaluateExpressionGet(context, "#{userBean}",
						UserController.class);

				// If he is already connected, he has to be the right one
				if (userControler.isConneted()) {
					User user = userControler.getUser();
					if (user == null
							|| user.getMail().compareTo(new String(Hex.decode(userId), StandardCharsets.UTF_8)) != 0) {
						FacesContext facesContext = FacesContext.getCurrentInstance();
						facesContext.addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"Vous n'êtes pas autorisé à accéder à ce sondage par ce lien. "
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
					facesContext.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Vous n'êtes pas autorisé à accéder à ce sondage. "
											+ "Veuillez vous connecter.",
									""));
					survey = new Survey();
					surveyControler.setSelected(survey);
					allgood = false;
				}
			}

		} else {
			if (surveyControler.getSelected() == null) {
				System.out.println("selected is null");
				surveyControler.setSelected(new Survey());
			}
		}
		return allgood;
	}

	public boolean checkIfInDiffusion(User user, Survey survey) {
		boolean isIn = false;
		
		if (user!=null) {
			List<String> diffusion = survey.getDiffusion();
			for (String mail : diffusion) {
				if (user.getMail().compareTo(mail) == 0) {
					isIn = true;
					break;
				}
			}
		}else{
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Vous n'êtes pas autorisé à accéder à ce sondage. "
									+ "Veuillez vous connecter avec le bon compte.",
							""));
		}
		return isIn;
	}

	/*
	 * @RequestMapping(path = "/reponse", method = RequestMethod.GET) public
	 * ModelAndView accessPrivateSurveyByUrl(HttpServletRequest
	 * request, @RequestParam Long surveyId,
	 * 
	 * @RequestParam String userId) { System.out.println("TRYING TO ACCESS URL"
	 * ); ModelAndView md = new ModelAndView();
	 * 
	 * User user = new User();
	 * 
	 * // Checking if there is a User connected, and if so if it is the good //
	 * one userControler = (UserController)
	 * request.getSession().getAttribute("userBean"); if
	 * (userControler.isConneted()) { user = userControler.getUser(); if (user
	 * == null || user.getMail().compareTo(new String(Hex.decode(userId),
	 * StandardCharsets.UTF_8)) != 0) { md.setViewName("redirect:index"); } } //
	 * Getting the survey Survey survey = surveyManager.findSurvey(surveyId);
	 * System.out.println("id : " + surveyId); System.out.println(survey);
	 * 
	 * // Setting the selected survey to the one with id
	 * surveyControler.setSelected(survey);
	 * md.setViewName("redirect:/survey/reponse.xhtml"); return md; }
	 */

}
