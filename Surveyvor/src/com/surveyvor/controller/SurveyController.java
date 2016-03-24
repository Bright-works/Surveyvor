package com.surveyvor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.DragDropEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Comment;
import com.surveyvor.model.Question;
import com.surveyvor.model.Result;
import com.surveyvor.model.Survey;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;
import com.surveyvor.service.FirstArrived;
import com.surveyvor.service.GaleShapley;
import com.surveyvor.service.ResultGeneratorStrategyContext;

@Controller
@Component("surveyBean")
@Scope("session")
@RequestMapping(value = "/Sondage")
public class SurveyController {

	@Autowired
	private SurveyManager manager;

	@Autowired
	private UserManager userManager;

	@Autowired
	private UserController userController;

	private List<Survey> list = new ArrayList<Survey>();
	private List<Survey> listInvited = new ArrayList<Survey>();

	private Survey selected = new Survey();
	private Comment myComment = new Comment();

	private List<Comment> alls = new ArrayList<Comment>();
	private List<Choice> droppedChoices = new ArrayList<Choice>();

	private List<Survey> invitations = new ArrayList<Survey>();
	private List<Result> resulat = new ArrayList<Result>();
	private Answer answer = new Answer();
	private int algo;

	public SurveyController() {
	}

	@PostConstruct
	public void init() {
		System.out.println("CREATING " + this);
	}

	// ------------ Methodes--------
	public void generateResult() {
		ResultGeneratorStrategyContext algoContext = new ResultGeneratorStrategyContext();
		try {
			if (algo == 0) {
				algoContext.setStrategy(new FirstArrived());
			} else {
				algoContext.setStrategy(new GaleShapley());
			}
			Map<Long, List<User>> result = algoContext.GeneratorStrategy(selected,
					selected.getQuestions().get(0).getListAnswers());
			System.out.println(result.get(selected.getQuestions().get(0).getAnswer().getChoix().getId()));

		} catch (Exception exp) {

		}
	}

	public void updateDateSurvey() {
		manager.updateSurvey(selected);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date est modifiŽe", ""));
	}

	public BarChartModel inialisationGraphic(Question q) {
		BarChartModel barModel = new BarChartModel();
		ChartSeries boys = new ChartSeries();
		int max = 0;
		for (int i = 0; i < q.getChoices().size(); i++) {
			int taille = (manager.numberOfAnswersForChoice(q, q.getChoices().get(i))).size();
			if (taille > max) {
				max = taille;
			}
			System.err.println(taille);
			boys.set(q.getChoices().get(i).getLabel(), taille);
		}

		barModel.addSeries(boys);
		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("options");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Nombre de vote");
		yAxis.setMin(0);
		yAxis.setMax(max);
		return barModel;
	}

	public void onSelectedMultichoix(Question question) {
		if (question.getAnswer().getChoices().size() > question.getMaxChoice()) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nombre de choix autorisï¿½ n'est pas respectï¿½ !", ""));
		}
		if (question.getAnswer().getChoices().size() < question.getMinChoice()) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Nombre de choix autorisï¿½ n'est pas respectï¿½ !", ""));
		}
	}

	public void prepareAnswers() {
		if (selected.getQuestions() != null) {
			int size = selected.getQuestions().size();
			for (int i = 0; i < size; i++) {
				List<Choice> listchoix = new ArrayList<Choice>();
				Answer ans = new Answer();
				ans.setChoices(listchoix);
				ans.setChoix(new Choice());
				selected.getQuestions().get(i).setAnswer(ans);
			}
		}
	}

	public String addAnswers() {
		try {

			if (selected.getType().equals(TypeSurvey.REPARTITION)) {
				Answer ans = selected.getQuestions().get(0).getAnswer();
				ans.setAnswerer(userController.getUser());
				Map<Long, String> resultats = ans.getValeurs();
				for (int i = 0; i < ans.getChoices().size(); i++) {
					resultats.put(ans.getChoices().get(i).getId(), String.valueOf(i));
				}
				ans.setValeurs(resultats);
				ans.setDate(new Date());
				ans.setQuestion(selected.getQuestions().get(0));

				manager.repondreSondage(ans);
			} else {
				for (int i = 0; i < selected.getQuestions().size(); i++) {
					Answer ans = selected.getQuestions().get(i).getAnswer();
					if (selected.getParametres().getPrivateSurvey()) {
						ans.setAnswerer(userController.getUser());
					}
					ans.setDate(new Date());
					Question q = selected.getQuestions().get(i);
					ans.setQuestion(q);
					if (q.getParametres().getSeveralAnswers() == false && q.getParametres().getWritable() == false) {
						ans.getChoices().add(ans.getChoix());
					}
					manager.repondreSondage(ans);
				}
			}
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, " Merci pour votre participation !", ""));
		} catch (JpaSystemException | ConstraintViolationException | PersistenceException e) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous avez déjà répondu à ce sondage.", ""));
		}
		return "/index.xhtml";

	}

	public void allComments() {
		System.out.println("manager " + manager);
		System.out.println("selected " + selected);
		if (selected.getId() != null) {
			alls = manager.getallCommentBySurvey(selected.getId());
		} else {
			alls = new ArrayList<Comment>();
		}
	}

	public List<Survey> getAll() {
		Long id = userController.getUser().getId();
		list = (List<Survey>) userController.getUserManager().allSurveysCreated(id);
		return list;
	}

	@RequestMapping(path = "/{param}", method = RequestMethod.GET)
	public ModelAndView showResult(@PathVariable("param") Integer param) {
		try {
			Survey searched = manager.findSurvey(param);
			if (searched.getParametres().getPrivateSurvey()) {
				return new ModelAndView("index");
			}
			if (searched.getId() != selected.getId()) {
				selected = searched;
			}

		} catch (Exception exp) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette page est introuvable!", ""));
			return new ModelAndView("index");
		}
		return new ModelAndView("./survey/reponse");
	}

	public String showSurvey() {
		return "./details.xhtml?faces-redirect=true";
	}

	public void addComment() {
		if (selected.getParametres().getPrivateSurvey()) {
			myComment.setUser(userController.getUser());
		}
		myComment.setSurvey(selected);
		myComment.setDateComment(new Date());
		manager.commentSurvey(myComment);
		// System.out.println(myComment.getComment());

		myComment = new Comment();
	}

	public void deleteSurvey(Survey s) {
		int index = -1;
		for (int i = 0; i < userController.getUser().getOwnedSurveys().size(); i++) {
			if (userController.getUser().getOwnedSurveys().get(i).getId() == s.getId()) {
				index = i;
				break;
			}
		}
		manager.removeCommentAllOfSurvey(s);
		manager.removeSurvey(s.getId());
		userController.getUser().getOwnedSurveys().remove(index);
		userController.userManager.update(userController.getUser());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, s.getTitle() + " est bien supprimï¿½!", ""));
	}

	public void onChoiceDrop(DragDropEvent ddEvent) {
		Choice choice = ((Choice) ddEvent.getData());
		if (!droppedChoices.contains(choice)) {
			droppedChoices.add(choice);
		}

	}

	public Long getNumberAnswers(Survey survey) {
		return manager.getNumberAnswers(survey);
	}

	public String getAnswerView() {
		return "reponse.xhtml?faces-redirect=true";
	}

	public void redirectIndex(ComponentSystemEvent event) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous êtes déjà connecté", ""));
			facesContext.getExternalContext().getFlash().setKeepMessages(true);
			facesContext.getExternalContext().redirect("index.xhtml");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return;
	}

	// ----------gtters and setters--------
	public SurveyManager getManager() {
		return manager;
	}

	public void setManager(SurveyManager manager) {
		this.manager = manager;
	}

	public List<Survey> getList() {
		getAll();
		return list;
	}

	public int getAlgo() {
		return algo;
	}

	public void setAlgo(int algo) {
		this.algo = algo;
	}

	public void setList(List<Survey> list) {
		this.list = list;
	}

	public Survey getSelected() {
		return selected;
	}

	public void setSelected(Survey selected) {
		this.selected = selected;
		prepareAnswers();
	}

	public UserController getUserController() {
		return userController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public List<Comment> getAlls() {
		allComments();
		return alls;
	}

	public void setAlls(List<Comment> alls) {
		this.alls = alls;
	}

	public Comment getMyComment() {
		return myComment;
	}

	public void setMyComment(Comment myComment) {
		this.myComment = myComment;
	}

	public List<Choice> getDroppedChoices() {
		return droppedChoices;
	}

	public void setDroppedChoices(List<Choice> droppedChoices) {
		this.droppedChoices = droppedChoices;
	}

	public List<Survey> getInvitations() {
		listInvited = new ArrayList<Survey>(userManager.allSurveysInvited(userController.getUser()));
		return invitations;
	}

	public void setInvitations(List<Survey> invitations) {
		this.invitations = invitations;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public List<Result> getResulat() {
		return resulat;
	}

	public void setResulat(List<Result> resulat) {
		this.resulat = resulat;
	}

	/**
	 * @return the listInvited
	 */
	public List<Survey> getListInvited() {
		getInvitations();
		return listInvited;
	}

	/**
	 * @param listInvited
	 *            the listInvited to set
	 */
	public void setListInvited(List<Survey> listInvited) {
		this.listInvited = listInvited;
	}

}
