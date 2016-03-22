package com.surveyvor.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//import com.surveyvor.model.annotation.SizeChoice;

@Entity
@Table(name="Answers",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"Id_Answerer","Id_Question"})})
public class Answer {
	
	@Valid
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id_Answer;
	
	@Valid
	@ManyToOne
	@JoinColumn(name = "Id_Answerer")
	private User answerer;

	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name="Id_Question", insertable=false, updatable=false)
	private Question question;
	
	@Transient
	private Choice choix=new Choice();
	
	
	@Valid
	@ManyToMany
	private List<Choice> choices;
	//
	@ElementCollection
	private Map<Long,String> valeurs;
	
	private String opinionText;
	
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


	public long getAnswer() {
		return Id_Answer;
	}


	public void setAnswer(long answer) {
		this.Id_Answer = answer;
	}


	public Map<Long, String> getValues() {
		return valeurs;
	}


	public void setValues(Map<Long, String> values) {
		this.valeurs = values;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getOpinionText() {
		return opinionText;
	}

	public void setOpinionText(String opinionText) {
		this.opinionText = opinionText;
	}
	

	public Map<Long, String> getValeurs() {
		return valeurs;
	}

	public void setValeurs(Map<Long, String> valeurs) {
		this.valeurs = valeurs;
	}

	
	public long getId_Answer() {
		return Id_Answer;
	}

	public void setId_Answer(long id_Answer) {
		Id_Answer = id_Answer;
	}

	public Choice getChoix() {
		return choix;
	}

	public void setChoix(Choice choix) {
		this.choix = choix;
	}


	public Answer(Long id_Answer, User answerer, Question question, List<Choice> choices, Map<Long, String> valeurs,
			Date date) {
		super();
		Id_Answer = id_Answer;
		this.answerer = answerer;
		this.question = question;
		this.choices = choices;
		this.valeurs = valeurs;
		this.date = date;
	}
	
}
