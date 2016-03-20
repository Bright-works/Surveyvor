package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.DragDropEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Comment;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Survey;

@Controller
@Component("surveyBean")
@Scope("session")
@RequestMapping(value="/Sondage")
public class SurveyController {

	@Autowired
	private SurveyManager manager;
	
	@Autowired
	private UserController userController;
	
	private List<Survey> list=new ArrayList<Survey>();
	private Survey selected;
	private Comment myComment=new Comment();

	private List<Comment> alls=new ArrayList<Comment>();
	private List<Choice> droppedChoices = new ArrayList<Choice>();
	
	private List<Survey> invitations= new ArrayList<Survey>();
	private Answer answer=new Answer();
	
	public SurveyController() {
	}
	
	@PostConstruct
	public void init(){
	}
	//------------ Methodes--------
	
	public String repondre(){
		if(selected.getParametres().getPrivateSurvey()){
			answer.setAnswerer(userController.getUser());
		}
			answer.setDate(new Date());
			answer.setQuestion(selected.getQuestions().get(0));
			answer.setChoices(selected.getQuestions().get(0).getChoices());
			manager.repondreSondage(answer);
		return "index.xhtml?faces-redirect=true";
	}
	public void allComments(){
		alls= manager.getallCommentBySurvey(selected.getId());
	}
	public List<Survey> getAll() {
		list=(List<Survey>) userController.getUser().getOwnedSurveys();
		return list;
	}
	
	@RequestMapping(path = "/{param}", method = RequestMethod.GET)
	public ModelAndView showResult(@PathVariable("param") Integer param){
		try{
			Survey searched=manager.findSurvey(param);
			if(searched.getParametres().getPrivateSurvey())
				{return new ModelAndView("index");}
			if(searched.getId()!=selected.getId())
			{	selected=searched;}
			
			}catch(Exception exp){
				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cette page est introuvable!", ""));
				return new ModelAndView("index");
			}
		return new ModelAndView("./survey/reponse");
	}
	public String showSurvey(){
		return "./details.xhtml?faces-redirect=true";
	}
	public void addComment(){
		if(selected.getParametres().getPrivateSurvey()){
			myComment.setUser(userController.getUser());
		}
		else{
			myComment.setSurvey(selected);
			myComment.setDateComment(new Date());
			manager.commentSurvey(myComment);
			System.out.println(myComment.getComment());
			}
		
		myComment= new Comment();
	}
	public void deleteSurvey(){
		userController.getUser().getOwnedSurveys().remove(selected);
		userController.userManager.update(userController.getUser());
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, selected.getTitle() +" est bien supprimé!",""));
	}
	public void onChoiceDrop(DragDropEvent ddEvent) {
		Choice choice = ((Choice) ddEvent.getData());
		if (! droppedChoices.contains(choice)) {
			droppedChoices.add(choice);
		}
		
	}
	
	//----------gtters and setters--------
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
	
	public void setList(List<Survey> list) {
		this.list = list;
	}

	public Survey getSelected() {
		return selected;
	}

	public void setSelected(Survey selected) {
		this.selected = selected;
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
	
}
