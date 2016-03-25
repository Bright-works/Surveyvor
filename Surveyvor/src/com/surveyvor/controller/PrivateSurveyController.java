package com.surveyvor.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Question;
import com.surveyvor.model.Result;
import com.surveyvor.model.Survey;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;
import com.surveyvor.service.FirstArrived;
import com.surveyvor.service.GaleShapley;
import com.surveyvor.service.ResultGeneratorStrategyContext;

@Controller
@Component("privateSurveyBean")
@Scope("request")
public class PrivateSurveyController {

	@Autowired
	private SurveyManager surveyManager;

	@Autowired
	private SurveyController surveyControler;
	
	private List<Result> resulat = new ArrayList<Result>();

	private boolean ready = false;

	private boolean allgood = true;
	
	private int algo;

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

	public void generateResult() {
		GaleShapley galeShapley= new GaleShapley();
		ResultGeneratorStrategyContext algoContext = new ResultGeneratorStrategyContext();
		try {
			if (algo == 0) {
				algoContext.setStrategy(new FirstArrived());
			} else {
				algoContext.setStrategy(new GaleShapley());
			}
			System.out.println("le nombre des reponses = "+surveyControler.getSelected().getQuestions().get(0).getListAnswers().get(0).getChoices().size());
			//System.out.println(result.get(selected.getQuestions().get(0).getAnswer().getChoix().getId()));

			Map<Long, List<User>> result = algoContext.GeneratorStrategy(surveyControler.getSelected(),
					surveyControler.getSelected().getQuestions().get(0).getListAnswers());
			resulat = new ArrayList<Result>();
			
			for(Entry<Long, List<User>> entry : result.entrySet()) {
				Long cle = entry.getKey();
				List<User> valeur = entry.getValue();
				for(int i=0;i<valeur.size();i++){
					Result res = new Result();
					res.setUser(valeur.get(i));
					res.setChoix(surveyManager.getChoiceByID(cle));
					resulat.add(res);
				}
			}
			System.out.println("taille resultat = "+resulat.size());

		} catch (Exception exp) {
			exp.printStackTrace();
		}
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
						List<Choice> choices = question.getChoices();
						List<Choice> noduplicateChoice = new ArrayList<Choice>();
						for(Choice choice : choices){
							boolean duplicateChoice = false;
							for(Choice c : noduplicateChoice){
								if(c.getId()==choice.getId()){
									duplicateChoice = true;
									break;
								}
							}
							if(!duplicateChoice){
								noduplicateChoice.add(choice);
							}
						}
						question.setChoices(noduplicateChoice);
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
					
					if(survey.getType().equals(TypeSurvey.REPARTITION)){
						surveyControler.setChoices(survey.getQuestions().get(0).getChoices());
					}

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
					survey.setDiffusion(surveyManager.getDiffusion(survey));
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
	public List<Result> getResulat() {
		return resulat;
	}

	public void setResulat(List<Result> resulat) {
		this.resulat = resulat;
	}
	public int getAlgo() {
		return algo;
	}

	public void setAlgo(int algo) {
		this.algo = algo;
	}
}
