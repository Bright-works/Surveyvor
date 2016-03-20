package com.surveyvor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Choice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7469577458867792848L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id_Choice")
	private long Id_Choice;
	
	@NotNull
	private String label;
	
	@NotNull
	@Size(min=15)
	private String description;
	
	@Valid
	@ManyToOne
	@JoinColumn(name="Id_Question", insertable=false, updatable=false)
	private Question question;
	
	@Min(1)
	private Integer quotat;
	
	 @ManyToMany
	 private List<User> usersAttribued;
	
	public Choice() {
	}

	public long getId() {
		return Id_Choice;
	}

	public void setId(long id) {
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
	
	public int getQuotat()
	{
		return quotat;
	}
	
	public void setQuotat(int quotat)
	{
		this.quotat = quotat;
	}

	public List<User> getUsersAttribued() {
		return usersAttribued;
	}

	public void setUsersAttribued(List<User> usersAttribued) {
		this.usersAttribued = usersAttribued;
	}

	public void setQuotat(Integer quotat) {
		this.quotat = quotat;
	}
	
	


}