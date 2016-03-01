package com.surveyvor.service;

import java.util.List;
import java.util.Map;

import com.surveyvor.model.Answer;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

public class FirstArrived implements IResultGeneratorStrategy<List<User>> {

	@Override
	public Map<Long, List<User>> generateResult(Survey survey, List<Answer> answers) {
		//Convert String values to Integer + invert Mapping -> Map<Integer,Long>
		// TODO
		//Sort List<Answer> by Date -> List<Answer> sorted
		// TODO
		//For each choice, get the Id and quota -> Map<Long,List<User>(quota)>
		// TODO
		// Browse the list of Answer
			// If the choice is not full
				// Add User to the list
			// Else
				// Browse values list to find the next choice with room left
		return null;
	}

}
