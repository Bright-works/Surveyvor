package com.surveyvor.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question {
	
	@Id
	@Column(name ="Id_Question")
	Long Id_Question;
	
	String contenu;
	
	String description;
	
	@ManyToOne
	@JoinColumn(name="Id_Survey", insertable=false, updatable=false)
	Survey survey;
	
	@OneToMany
	@JoinColumn(name="Id_Choice")
	List<Choice> choices;
	
	@Embedded
	QuestionParameters parametres;
	
	
	public Question() {
	}


	public Long getId() {
		return Id_Question;
	}


	public void setId(Long id) {
		Id_Question = id;
	}


	public String getContenu() {
		return contenu;
	}


	public void setContenu(String contenu) {
		this.contenu = contenu;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Survey getSurvey() {
		return survey;
	}


	public void setSurvey(Survey survey) {
		this.survey = survey;
	}


	public List<Choice> getChoices() {
		return choices;
	}


	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}


	public QuestionParameters getParametres() {
		return parametres;
	}


	public void setParametres(QuestionParameters parametres) {
		this.parametres = parametres;
	}
	
	
	

}
