package com.surveyvor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * 
 * @author Brightworks
 * this is a user of application
 * contains a name, lastName, mail (each mail is unique)
 * a password, role of user (admin or simple user) 
 * List of survey in which the user is invited
 * list of survey created by user
 */

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_User")
	private long Id_User;

	@NotNull
	@Size(min=2, max=15)
	private String name;

	@NotNull
	@Size(min=2, max=15)
	private String lastName;
	
	@NotNull
	@Email
	@Column(unique = true)
	private String mail;

	@NotNull
	@Size(min=4)
	private String password;

	@NotNull
	private String admin;

	@Valid
	@ManyToMany(mappedBy = "answerers")
	List<Survey> invitedSurveys= new ArrayList<Survey>();;

	@Valid
	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "Id_Creator")
	List<Survey> ownedSurveys = new ArrayList<Survey>();


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
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
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
	
	public User (String nom, String prenom, String mail, String password, String admin) {
		this.name = nom;
		this.lastName = prenom;
		this.mail = mail;
		this.password = password;
		this.admin = admin;
	}

	public User clone()
	{
		User user = new User(name,lastName,mail,password,admin);
		user.setId(Id_User);
		return user;
	}

}
