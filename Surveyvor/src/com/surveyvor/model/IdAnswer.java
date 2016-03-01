package com.surveyvor.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IdAnswer {
	
	@Column(name="Id_User")
	Long Id_User;
	
	@Column(name="Id_Question")
	Long Id_Question;
	
	
	
	public IdAnswer() {}

	public IdAnswer(Long idUser, Long idQuestion) {
		super();
		Id_User = idUser;
		Id_Question = idQuestion;
	}

	public Long getIdUser() {
		return Id_User;
	}

	public void setIdUser(Long idUser) {
		Id_User = idUser;
	}

	public Long getIdQuestion() {
		return Id_Question;
	}

	public void setIdQuestion(Long idQuestion) {
		Id_Question = idQuestion;
	}

	
	

}
