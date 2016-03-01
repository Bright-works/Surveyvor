package com.surveyvor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Choice {
	
	@Id
	@Column(name = "Id_Choice")
	Long Id_Choice;
	
	String label;
	
	String description;
	
	@ManyToOne
	@JoinColumn(name="Id_Question", insertable=false, updatable=false)
	Question question;
	
	public Choice() {
	}

	public Long getId() {
		return Id_Choice;
	}

	public void setId(Long id) {
		this.Id_Choice = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	
	
	

}
