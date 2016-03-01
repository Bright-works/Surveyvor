package com.surveyvor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

	@Id
	@Column(name="Id_Column")
	Long Id_Comment;
	
	@ManyToOne
	@JoinColumn(name="Id_User")
	User user;

	@ManyToOne
	@JoinColumn(name="Id_Survey")
	Survey survey;
	
	String comment;
	
	Date dateComment;
	
	
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
