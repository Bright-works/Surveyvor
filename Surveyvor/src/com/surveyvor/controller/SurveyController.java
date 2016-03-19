package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.DragDropEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Survey;

@Component("surveyBean")
@Scope("session")
public class SurveyController {

	@Autowired
	private SurveyManager manager;
	
	private List<Survey> list=new ArrayList<Survey>();
	private Survey selected;
	private List<Choice> droppedChoices = new ArrayList<Choice>();
	
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
	
	public List<Choice> getDroppedChoices() {
		return droppedChoices;
	}
	
	public void setDroppedChoices(List<Choice> droppedChoices) {
		this.droppedChoices = droppedChoices;
	}
}
