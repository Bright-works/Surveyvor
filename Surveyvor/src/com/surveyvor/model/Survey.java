package com.surveyvor.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Survey {
	
	@Id
	Long Id;
	
	String title;  
	
	String description;  
	
	Date startDate;  
	
	Date endDate;  
	
	List<String> diffusion; 
	
	@ManyToOne
	@JoinColumn(name="Id_Creator", insertable=false, updatable=false)
	User creator;  
	
	//a voir j'ai mis directement le enum
	TypeSurvey type;  
	
	@ManyToMany
	@JoinTable(name="Surveys_Invited", 
	joinColumns= @JoinColumn(name="Id_Survey"), inverseJoinColumns= @JoinColumn(name="Id_Invited"))
	List<User> answerers;  
	
	@OneToMany
	@JoinColumn(name="Id_Question")
	List<Question> questions; 
	
	@Embedded
	SurveyParameters parametres; 

	
	public Survey() {
	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public List<String> getDiffusion() {
		return diffusion;
	}


	public void setDiffusion(List<String> diffusion) {
		this.diffusion = diffusion;
	}


	public User getCreator() {
		return creator;
	}


	public void setCreator(User creator) {
		this.creator = creator;
	}


	/*public SurveyType getType() {
		return type;
	}


	public void setType(SurveyType type) {
		this.type = type;
	}*/


	public List<User> getAnswerers() {
		return answerers;
	}


	public void setAnswerers(List<User> answerers) {
		this.answerers = answerers;
	}


	public List<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}


	public SurveyParameters getParametres() {
		return parametres;
	}


	public void setParametres(SurveyParameters parametres) {
		this.parametres = parametres;
	}
	
	
	
}
