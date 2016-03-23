package com.surveyvor.service;

import java.util.List;
import java.util.Map;

import com.surveyvor.model.Answer;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

public class ResultGeneratorStrategyContext {

	private IResultGeneratorStrategy<List<User>> strategy;
	
	public void setStrategy(IResultGeneratorStrategy<List<User>> strategy){
		
	}
	
	public Map<Long, List<User>>   GeneratorStrategy(Survey survey, List<Answer> answers) throws Exception {
		return strategy.generateResult(survey, answers);
	}
	

}
