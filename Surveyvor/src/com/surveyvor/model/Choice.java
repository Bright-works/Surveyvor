package com.surveyvor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Choice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id_Choice")
	private Long Id_Choice;

	@NotNull
	private String label;
	
	@NotNull
	@Size(min=15)
	private String description;
	
	@Valid
	//@NotNull
	@ManyToOne
	@JoinColumn(name="Id_Question", insertable=false, updatable=false)
	private Question question;
	
	private Integer quotat;
	
	
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

	public Choice(String label, String description, int quotat) {
		super();
		this.label = label;
		this.description = description;
		this.quotat = quotat;
	}
	
	public Long getId_Choice() {
		return Id_Choice;
	}

	public void setId_Choice(Long id_Choice) {
		Id_Choice = id_Choice;
	}

	public Integer getQuotat() {
		return quotat;
	}

	public void setQuotat(Integer quotat) {
		this.quotat = quotat;
	}
	
	
	

}
