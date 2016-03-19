package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Comment;
import com.surveyvor.model.Survey;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;

@Component("surveyBean")
@Scope("session")
public class SurveyController {

	@Autowired
	private SurveyManager manager;
	
	@Autowired
	private UserController userController;
	
	private List<Survey> list=new ArrayList<Survey>();
	private Survey selected;
	private Comment myComment=new Comment();

	private List<Comment> alls=new ArrayList<Comment>();
	public SurveyController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
	}
	//------------ Methodes--------
	public void allComments(){
		alls= manager.getallCommentBySurvey(selected.getId());
	}
	public List<Survey> getAll() {
		list=(List<Survey>) userController.getUser().getOwnedSurveys();
		return list;
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
			manager.addComment(myComment);
			}
		
		myComment= new Comment();
	}
	
	public void deleteSurvey(){
		manager.removeSurvey(selected.getId());
		getAll();
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
	
	
}
