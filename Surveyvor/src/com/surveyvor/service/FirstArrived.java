/**
 * @author Brightworks
 * Algorithm First Arrived for a repartition survey
 */

package com.surveyvor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.surveyvor.exception.FirstArrivedException;
import com.surveyvor.exception.QuotatException;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

@Service
public class FirstArrived implements IResultGeneratorStrategy<List<User>> {
	
	/**
	 * Comparator
	 * @author Brightworks
	 *
	 */
	private class AnswerComparator implements Comparator<Answer> {

		@Override
		public int compare(Answer arg0, Answer arg1) {
			return arg0.getDate().compareTo(arg1.getDate());
		}

	}
	
	/**
	 * sort answers by date
	 * @param answers : list of answers
	 * @return List<Answer> : list of sorted answers 
	 * @throws FirstArrivedException
	 */
	public List<Answer> sortByDate(List<Answer> answers) throws FirstArrivedException {
		List<Answer> result = answers;
		if (answers != null) {
			AnswerComparator ac = new AnswerComparator();
			try {
				Collections.sort(result, ac);
			} catch (Exception e) {
				throw new FirstArrivedException("Error: cannot sort " + answers);
			}
		} else {
			throw new FirstArrivedException("Error: cannot sort empty or null list");
		}
		return result;
	}

	/**
	 * build wishes for each user 
	 * @param wishes
	 * @return Map<Integer, Long> 
	 * @throws FirstArrivedException
	 */
	public Map<Integer, Long> prepareWishList(Map<Long, String> wishes) throws FirstArrivedException {
		Map<Integer, Long> invertedMapping = new HashMap<Integer, Long>();
		if (wishes != null) {
			for (Long key : wishes.keySet()) {
				try {
					Integer wish = Integer.valueOf(wishes.get(key));
					invertedMapping.put(wish, key);
				} catch (Exception e) {
					throw new FirstArrivedException("Error: cannot convert " + wishes.get(key) + " to Integer");
				}
			}
		} else {
			throw new FirstArrivedException("Error: cannot convert null Map");
		}
		return invertedMapping;
	}

	@Override
	/**
	 * build result of algorithm,each choice has a list of user 
	 * @param survey
	 * @param answers
	 * @return Map<Long, List<User>>
	 */
	public Map<Long, List<User>> generateResult(Survey survey, List<Answer> answers) throws FirstArrivedException, QuotatException {
		/*
		 * Some verifications: the survey mustn't be null, the survey must have
		 * ended, and the list of answers mustn't be null or empty
		 */
		if (answers == null || answers.isEmpty() || survey == null || survey.getEndDate().after(new Date())) {
			throw new FirstArrivedException("Error: entry not valid");
		} else {
			// Sort List<Answer> by Date -> List<Answer> sorted
			List<Answer> sortedAnswers = sortByDate(answers);

			/*
			 * Convert String values to Integer + invert Mapping ->
			 * Map<Integer,Long>
			 */
			List<Map<Integer, Long>> wishesList = new ArrayList<Map<Integer, Long>>();
			for (Answer answer : sortedAnswers) {
				wishesList.add(prepareWishList(answer.getValues()));
			}

			/*
			 * For each choice, get the Id and the room left ->
			 * Map<Long,Integer>, and prepare the result
			 */
			Map<Long, Integer> roomLeft = new HashMap<Long, Integer>();
			Map<Long, List<User>> repartition = new HashMap<Long, List<User>>();
			List<Choice> choices = answers.get(0).getQuestion().getChoices();
			
			int sum=0;
			for(int j=0; j<choices.size(); j++)
				sum = choices.get(j).getQuotat()+sum;
			if (sum < answers.size())
				throw new QuotatException();
			
			for (Choice choice : choices) {

				roomLeft.put(choice.getId(), choice.getQuotat());
				List<User> users = new ArrayList<User>();

				repartition.put(choice.getId(), users);
			}
			
			// BEGGINING OF THE REAL ALGORITHM

			// Browse the list of Answer
			int nbWishes = answers.get(0).getValues().size();
			for (int i = 0; i < sortedAnswers.size(); i++) {
				int j = 1;
				boolean attributed = false;
				do {
					long choice = wishesList.get(i).get(j);
					// If the choice is not full
					if (roomLeft.get(choice) > 0) {
						// Add User to the list
						List<User> users = repartition.get(choice);
						users.add(sortedAnswers.get(i).getAnswerer());
						repartition.put(choice, users);

						// Actualize room left
						roomLeft.put(choice, roomLeft.get(choice) - 1);
						attributed = true;
					}
					// Else, browse whish list to find the next choice with
					// room left
					j++;
				} while (j < nbWishes && !attributed);
			}

			return repartition;
		}

	}

}
