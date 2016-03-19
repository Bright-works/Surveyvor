package com.surveyvor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Question implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="Id_Question")
	private Long Id_Question;
	
	@NotNull
	@Size(min = 15)
	private String contenu;
	
	@NotNull
	@Size(min = 15)
	private String description;
	
	@Valid
	@ManyToOne
	@JoinColumn(name="Id_Survey", insertable=false, updatable=false)
	private Survey survey;
	
	@Min(1)
	private int minChoice;
	
	@NotNull
	private int maxChoice;
	
	@Valid
	@NotNull
	@Size(min = 2)
	@OneToMany (orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="Id_Question")
	private List<Choice> choices;
	
	@NotNull
	@Valid
	@Embedded
	private QuestionParameters parametres;
	
	
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


	public Long getId_Question() {
		return Id_Question;
	}


	public void setId_Question(Long id_Question) {
		Id_Question = id_Question;
	}


	public int getMinChoice() {
		return minChoice;
	}


	public void setMinChoice(int minChoice) {
		this.minChoice = minChoice;
	}


	public int getMaxChoice() {
		return maxChoice;
	}


	public void setMaxChoice(int maxChoice) {
		this.maxChoice = maxChoice;
	}
	
	
	public Question ( String contenu, String description, int minChoice, int maxChoice, List<Choice> choices, QuestionParameters parametres) {
		this.contenu = contenu;
		this.description = description;
		//this.survey = survey;
		this.minChoice = minChoice;
		this.maxChoice = maxChoice;
		this.choices = choices;
		this.parametres = parametres;

	}
	

}
