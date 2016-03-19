package com.surveyvor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Comments")
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	private String comment;
	
	@NotNull
	@Temporal(TemporalType.DATE)
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