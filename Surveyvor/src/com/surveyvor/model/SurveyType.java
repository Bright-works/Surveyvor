package com.surveyvor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SurveyType {

	@Id
	@Column (name="Id_SurveyType")
	Long Id_SurveyType;
	TypeSurvey type;
	
	
	public SurveyType() {
	}


	public Long getId() {
		return Id_SurveyType;
	}


	public void setId(Long id) {
		this.Id_SurveyType = id;
	}


	public TypeSurvey getType() {
		return type;
	}


	public void setType(TypeSurvey type) {
		this.type = type;
	}
	
	
	
}
