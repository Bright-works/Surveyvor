package com.surveyvor.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class User {

	@Id
	@Column(name = "ID_User")
	Long Id_User;

	String name;

	String lastName;

	String mail;

	String password;

	Boolean admin;


	@ManyToMany(mappedBy = "answerers")
	List<Survey> invitedSurveys;

	@OneToMany
	@JoinColumn(name = "Id_Survey")
	List<Survey> ownedSurveys;


	public User() {
	}

	public long getId() {
		return Id_User;
	}
	public void setId(long id) {
		Id_User = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public List<Survey> getInvitedSurveys() {
		return invitedSurveys;
	}
	public void setInvitedSurveys(List<Survey> invitedSurveys) {
		this.invitedSurveys = invitedSurveys;
	}
	public List<Survey> getOwnedSurveys() {
		return ownedSurveys;
	}
	public void setOwnedSurveys(List<Survey> ownedSurveys) {
		this.ownedSurveys = ownedSurveys;
	}



}
