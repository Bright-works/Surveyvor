package com.surveyvor.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Answer {
	
	@Valid
	@EmbeddedId
	private IdAnswer answer;
	
	@Valid
	@NotNull
	@MapsId("Id_User")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Answerer")
	private User answerer;

	@Valid
	@NotNull
	@MapsId("Id_Question")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Question")
	private Question question;
	
	@Valid
	@NotNull
	@ManyToMany
	//@Size (min= question.getMinChoice(), max = question.getMaxChoice())// a remplir selon la question voir comment faire)
	private List<Choice> choices;
	
	@NotNull
	private Map<Long,String> values;
	
	
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date date;
	
	
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


	public IdAnswer getAnswer() {
		return answer;
	}


	public void setAnswer(IdAnswer answer) {
		this.answer = answer;
	}


	public Map<Long, String> getValues() {
		return values;
	}


	public void setValues(Map<Long, String> values) {
		this.values = values;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}
	
	

	
	
}
