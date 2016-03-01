package com.surveyvor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Answer {
	
	@EmbeddedId
	private IdAnswer answer;
	
	@MapsId("Id_User")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Answerer")
	User answerer;

	@MapsId("Id_Question")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Question")
	Question question;
	
	@ManyToMany // a verifier
	List<Choice> choices;
	
	
	public Answer() {
	}


	public User getAnswerer() {
		return answerer;
	}


	public void setAnswerer(User answerer) {
		this.answerer = answerer;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public List<Choice> getChoices() {
		return choices;
	}


	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	
	
}
