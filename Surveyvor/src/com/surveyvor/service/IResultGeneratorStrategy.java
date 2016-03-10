package com.surveyvor.service;

import java.util.List;
import java.util.Map;

import com.surveyvor.model.Answer;
import com.surveyvor.model.Survey;

public interface IResultGeneratorStrategy<T> {
	public Map<Long,T> generateResult(Survey survey, List<Answer> answers) throws Exception;
}
