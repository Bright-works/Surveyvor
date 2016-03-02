package com.surveyvor.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class SurveyParameters {
	
	@NotNull
	private Boolean visibility;
	
	@NotNull
	private Boolean questionModify;
	
	@NotNull
	private Boolean privateSurvey;
	
	@NotNull
	private Integer algo;
	
	public SurveyParameters() {
	}

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public Boolean getQuestionModify() {
		return questionModify;
	}

	public void setQuestionModify(Boolean questionModify) {
		this.questionModify = questionModify;
	}

	public Boolean getPrivateSurvey() {
		return privateSurvey;
	}

	public void setPrivateSurvey(Boolean privateSurvey) {
		this.privateSurvey = privateSurvey;
	}

	public Integer getAlgo() {
		return algo;
	}

	public void setAlgo(Integer algo) {
		this.algo = algo;
	}

	
	
}
