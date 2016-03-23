package com.surveyvor.exception;
/**
 * 
 * @author Brightworks
 *	Exception for Gale-Shapley Algorithm 
 * if quotat is smaller than number of answerer 
 */
public class QuotatException extends Exception {
	private static final long serialVersionUID = -6833705333904051331L;

	public QuotatException(String value) {
		super(value);
	}

	public QuotatException() {
		super();
	}
}