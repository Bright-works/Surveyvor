package com.surveyvor.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class IdAnswer {
	
	@NotNull
	@Column(name="Id_User")
	private Long Id_User;
	
	@NotNull
	@Column(name="Id_Question")
	private Long Id_Question;
	
	
	
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
