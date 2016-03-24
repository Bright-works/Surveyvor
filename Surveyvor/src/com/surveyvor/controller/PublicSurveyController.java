package com.surveyvor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;

@Component("publicSurveyBean")
@ApplicationScoped
public class PublicSurveyController {

	@Autowired
	private SurveyManager manager;

	private List<Survey> list;
	private Survey selected;
	private String searchString;
	private List<Survey> matchingSurveys;
	private String filter;
	public PublicSurveyController() {

	}

	@PostConstruct
	public void initialisation() {
		refreshList();
		selected = new Survey();
		searchString = "";

	}
	
	public void refreshList(){
		try {
			list = new ArrayList<Survey>(manager.findPublicSurveys());
		} catch (Exception e) {
			list = new ArrayList<Survey>();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Probléme d'accés à la base de données", ""));
		}
	}

	public List<Survey> getAll() {
		return new ArrayList<Survey>(manager.findPublicSurveys());
	}

	public String search() {
		matchingSurveys = new ArrayList<Survey>();
		for (Survey current : list) {
			String titleLow = current.getTitle().toLowerCase();
			if (titleLow.contains(searchString.toLowerCase())) {
				matchingSurveys.add(current);
			}
		}
		return "/survey/searchSurvey.xhtml?faces-redirect=true";
	}

	public void recherche(){
		try{
			matchingSurveys=manager.findByTitle(filter);
		}
		catch(NoResultException exp){
			matchingSurveys= new ArrayList<Survey>();

			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Probléme d'accés à la base de données", ""));
		}
		
	}
	public String show(Survey survey) {
		//System.out.println("TEST");
	
		FacesContext context = FacesContext.getCurrentInstance();
		SurveyController sv = context.getApplication().evaluateExpressionGet(context, "#{surveyBean}",
				SurveyController.class);
		sv.setSelected(survey);
		//return "http://localhost:8080/Surveyvor/reponse/Sondage/"+survey.getId();
		return "./survey/reponse.xhtml?faces-redirect=true";
	}

	public List<Survey> getMatchingSurveys() {
		return matchingSurveys;
	}

	public void setMatchingSurveys(List<Survey> matchingSurveys) {
		this.matchingSurveys = matchingSurveys;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
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

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	

}
