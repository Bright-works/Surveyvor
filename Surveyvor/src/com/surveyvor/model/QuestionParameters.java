package com.surveyvor.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class QuestionParameters {
	
	@NotNull
	private Boolean requested;
	
	@NotNull
	private Boolean severalAnswers;
	
	@NotNull
	private Boolean writable;
	
	public QuestionParameters() {
	}

	public Boolean getRequested() {
		return requested;
	}

	public void setRequested(Boolean requested) {
		this.requested = requested;
	}

	public Boolean getSeveralAnswers() {
		return severalAnswers;
	}

	public void setSeveralAnswers(Boolean severalAnswers) {
		this.severalAnswers = severalAnswers;
	}

	public Boolean getWritable() {
		return writable;
	}

	public void setWritable(Boolean writable) {
		this.writable = writable;
	}
	
	
	

}
