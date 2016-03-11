package com.surveyvor.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;

@Component("publicSurveyBean")
@ApplicationScoped
public class PublicSurveyController implements ISurveyController {
	
	@Autowired
	private SurveyManager manager;
	private List<Survey> list;
	private Survey selected;
	
	public PublicSurveyController() {
		
	}
	
	@PostConstruct
	public void initialisation(){
		list=(List<Survey>) manager.findSurveys();
		selected=new Survey();
	}

	@Override
	public List<Survey> getAll() {
		return null;
	}

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
