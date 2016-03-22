package com.surveyvor.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Brightworks
 * SurveyParameters is the parameter of survey
 * if survey is visible
 * if a choice can be modified
 * if is a private or public survey
 * and if it is a repartitioning survey, which algorithm is selected 
 */
@Embeddable
public class SurveyParameters {
	
	@NotNull
	private Boolean visibility;
	
	@NotNull
	private Boolean questionModify;
	
	@NotNull
	private Boolean privateSurvey;
	
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

	
	public SurveyParameters (Boolean visibility, Boolean questionModify, Boolean privateSurvey, Integer algo) {
		this.visibility = visibility;
		this.questionModify = questionModify;
		this.privateSurvey = privateSurvey;
		this.algo = algo;
	}
	
	
}