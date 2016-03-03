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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Answers",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"Id_Answerer","Id_Question"})})
public class Answer {
	
	@Valid
	private Long Id_Answer;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Id_Answerer")
	private User answerer;

	@Valid
	@NotNull
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


	public Long getAnswer() {
		return Id_Answer;
	}


	public void setAnswer(Long answer) {
		this.Id_Answer = answer;
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
