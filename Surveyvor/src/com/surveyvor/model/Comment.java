package com.surveyvor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Comments",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"Id_User","Id_Survey"})})
public class Comment {

	@Id
	@Column(name="Id_Column")
	private Long Id_Comment;
	
	@Valid
	@ManyToOne
	@JoinColumn(name="Id_User")
	private User user;

	@NotNull
	@Valid
	@ManyToOne
	@JoinColumn(name="Id_Survey")
	private Survey survey;
	
	@NotNull
	@Size(min=1)
	private String comment;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateComment;
	
	
	public Comment() {
	}


	public Long getId() {
		return Id_Comment;
	}


	public void setId(Long id) {
		this.Id_Comment = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Survey getSurvey() {
		return survey;
	}


	public void setSurvey(Survey survey) {
		this.survey = survey;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getDateComment() {
		return dateComment;
	}


	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}
	
	
	
	
}
