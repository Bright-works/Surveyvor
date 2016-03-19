package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;

@Component("surveyBean")
@Scope("session")
public class SurveyController {

	@Autowired
	private SurveyManager manager;
	
	private List<Survey> list=new ArrayList<Survey>();
	private Survey selected;
	
	public SurveyController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
		getAll();
	}
	//------------ Methodes--------
	
	public List<Survey> getAll() {
		list=(List<Survey>) manager.findSurveys();
		return list;
	}
	
	public String showSurvey(){
		return "./details.xhtml?faces-redirect=true";
	}
	//----------getters and setters--------
	public SurveyManager getManager() {
		return manager;
	}

	public void setManager(SurveyManager manager) {
		this.manager = manager;
	}

	public List<Survey> getList() {
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
}
