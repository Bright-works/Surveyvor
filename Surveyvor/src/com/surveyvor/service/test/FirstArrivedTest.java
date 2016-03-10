package com.surveyvor.service.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.surveyvor.exception.FirstArrivedException;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Question;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;
import com.surveyvor.service.FirstArrived;

public class FirstArrivedTest {
	private static List<User> users;
	private static Survey survey;
	private List<Answer> answers;
	private Answer answer;
	private Answer answer2;
	private Answer answer3;
	private Answer answer4;
	private FirstArrived fa = new FirstArrived();

	@BeforeClass
	public static void init() {
		// First, we need to prepare a bunch of users
		users = new ArrayList<User>();
		User usr;
		for (int i = 0; i < 9; i++) {
			usr = new User();
			usr.setAdmin(false);
			usr.setId(i);
			usr.setLastName("Foo" + i);
			usr.setName("John" + i);
			usr.setMail("john.foo" + i + "@gmail.com");
			users.add(usr);
		}

		survey = new Survey();
	}

	@Before
	public void reset() {
		answers = new ArrayList<Answer>();

		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2016);

		Question question = new Question();
		// 3 choices
		List<Choice> choices1 = new ArrayList<Choice>();
		Choice choice1 = new Choice();
		choice1.setId((long) 1);
		choice1.setQuotat(1);
		Choice choice2 = new Choice();
		choice2.setId((long) 2);
		choice2.setQuotat(3);
		Choice choice3 = new Choice();
		choice3.setId((long) 3);
		choice3.setQuotat(2);
		choices1.add(choice1);
		choices1.add(choice2);
		choices1.add(choice3);
		question.setChoices(choices1);

		answer = new Answer();
		answer.setQuestion(question);
		answer.setChoices(choices1);
		Map<Long, String> values = new HashMap<Long, String>();
		values.put(choice1.getId(), "1");
		values.put(choice2.getId(), "2");
		values.put(choice3.getId(), "3");
		answer.setValues(values);
		answer.setAnswerer(users.get(0));
		date.set(Calendar.DAY_OF_YEAR, 3);
		answer.setDate(new Date(date.getTimeInMillis()));

		answer2 = new Answer();
		answer2.setQuestion(question);		
		answer2.setChoices(choices1);
		Map<Long, String> values2 = new HashMap<Long, String>();
		values2.put(choice1.getId(), "2");
		values2.put(choice2.getId(), "3");
		values2.put(choice3.getId(), "1");
		answer2.setValues(values2);
		answer2.setAnswerer(users.get(1));
		date.set(Calendar.DAY_OF_YEAR, 2);
		answer2.setDate(new Date(date.getTimeInMillis()));

		answer3 = new Answer();		
		answer3.setQuestion(question);		
		answer3.setChoices(choices1);
		Map<Long, String> values3 = new HashMap<Long, String>();
		values3.put(choice1.getId(), "1");
		values3.put(choice2.getId(), "3");
		values3.put(choice3.getId(), "2");
		answer3.setValues(values3);
		answer3.setAnswerer(users.get(2));
		date.set(Calendar.DAY_OF_YEAR, 10);
		answer3.setDate(new Date(date.getTimeInMillis()));

		answer4 = new Answer();
		answer4.setQuestion(question);		
		answer4.setChoices(choices1);
		Map<Long, String> values4 = new HashMap<Long, String>();
		values4.put(choice1.getId(), "2");
		values4.put(choice2.getId(), "1");
		values4.put(choice3.getId(), "3");
		answer4.setValues(values4);
		answer4.setAnswerer(users.get(3));
		date.set(Calendar.DAY_OF_YEAR, 132);
		answer4.setDate(new Date(date.getTimeInMillis()));
	}

	/*
	 * TESTS ON SORT METHOD
	 */
	@Test
	public void testAllDatesEquals() throws FirstArrivedException {
		// Setup answers
		Date date = new Date();
		for (int i = 0; i < 9; i++) {
			Answer answer = new Answer();
			answer.setAnswerer(users.get(i));
			answer.setDate(date);
			// We don't need the rest, only date and users
			answers.add(answer);
		}

		// The list shouldn't change since all values have the same date
		Assert.assertEquals(answers, fa.sortByDate(answers));
	}

	@Test
	public void testDatesAlreadyOrdered() throws FirstArrivedException {
		// Setup answers
		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2016);
		for (int i = 0; i < 9; i++) {
			Answer answer = new Answer();
			answer.setAnswerer(users.get(i));
			// Each answer has a different day
			date.set(Calendar.DAY_OF_YEAR, i);
			answer.setDate(new Date(date.getTimeInMillis()));
			// We don't need the rest, only date and users
			answers.add(answer);
		}

		// The list should remain the same since all values have the same date
		Assert.assertEquals(answers, fa.sortByDate(answers));
	}

	@Test
	public void testOneElementList() throws FirstArrivedException {
		Date date = new Date();
		Answer answer = new Answer();
		answer.setAnswerer(users.get(0));
		answer.setDate(date);
		answers.add(answer);

		// The list should remain the same, there is only 1 element
		Assert.assertEquals(answers, fa.sortByDate(answers));
	}

	@Test
	public void testOddList() throws FirstArrivedException {
		// Setup answers
		answers.add(answer);
		answers.add(answer2);
		answers.add(answer3);

		List<Answer> expected = new ArrayList<Answer>();
		expected.add(answer2);
		expected.add(answer);
		expected.add(answer3);

		List<Answer> result = fa.sortByDate(answers);
		System.out.println(result.get(0).getDate());
		System.out.println(result.get(1).getDate());
		System.out.println(result.get(2).getDate());

		Assert.assertEquals(expected, fa.sortByDate(answers));
	}

	@Test
	public void testEvenList() throws FirstArrivedException {
		answers.add(answer);
		answers.add(answer2);
		answers.add(answer3);
		answers.add(answer4);

		List<Answer> expected = new ArrayList<Answer>();
		expected.add(answer2);
		expected.add(answer);
		expected.add(answer3);
		expected.add(answer4);

		Assert.assertEquals(expected, fa.sortByDate(answers));
	}

	@Test
	public void testEmptyList() throws FirstArrivedException {
		Assert.assertEquals(new ArrayList<Answer>(), fa.sortByDate(answers));
	}

	@Test(expected = FirstArrivedException.class)
	public void testVoidList() throws FirstArrivedException {
		fa.sortByDate(null);
	}

	@Test(expected = FirstArrivedException.class)
	public void testAnswerNoDate() throws FirstArrivedException {
		// Setup answers
		Date date = new Date();
		for (int i = 0; i < 9; i++) {
			Answer answer = new Answer();
			answer.setAnswerer(users.get(i));
			answer.setDate(date);
			// We don't need the rest, only date and users
			answers.add(answer);
		}
		// Remove one date
		Answer nodate = answers.get(3);
		nodate.setDate(null);
		answers.set(3, nodate);

		fa.sortByDate(answers);
	}

	@Test
	public void testSmallestDateAtTheEnd() throws FirstArrivedException {
		// Setup answers
		answers.add(answer3);
		answers.add(answer4);
		answers.add(answer);
		answers.add(answer2);

		List<Answer> expected = new ArrayList<Answer>();
		expected.add(answer2);
		expected.add(answer);
		expected.add(answer3);
		expected.add(answer4);

		Assert.assertEquals(expected, fa.sortByDate(answers));
	}

	@Test
	public void testBiggestDateAtTheEnd() throws FirstArrivedException {
		// Setup answers
		answers.add(answer3);
		answers.add(answer2);
		answers.add(answer);
		answers.add(answer4);

		List<Answer> expected = new ArrayList<Answer>();
		expected.add(answer2);
		expected.add(answer);
		expected.add(answer3);
		expected.add(answer4);

		Assert.assertEquals(expected, fa.sortByDate(answers));
	}

	@Test
	public void testBiggestDateBeforeEnd() throws FirstArrivedException {
		// Setup answers
		answers.add(answer3);
		answers.add(answer2);
		answers.add(answer4);
		answers.add(answer);

		List<Answer> expected = new ArrayList<Answer>();
		expected.add(answer2);
		expected.add(answer);
		expected.add(answer3);
		expected.add(answer4);

		Assert.assertEquals(expected, fa.sortByDate(answers));
	}

	/*
	 * TESTS ON INVERT AND CONVERT MAPPING METHOD
	 */

	@Test
	public void testPrepareWishListNormal() throws FirstArrivedException {
		Map<Long, String> wishes = new HashMap<Long, String>();
		wishes.put((long) 256395, "1");
		wishes.put((long) 258976, "596");

		Map<Integer, Long> expected = new HashMap<Integer, Long>();
		expected.put(1, (long) 256395);
		expected.put(596, (long) 258976);

		Assert.assertEquals(expected, fa.prepareWishList(wishes));
	}

	@Test(expected = FirstArrivedException.class)
	public void testPrepareWishListNotInteger() throws FirstArrivedException {
		Map<Long, String> wishes = new HashMap<Long, String>();
		wishes.put((long) 256395, "1");
		wishes.put((long) 258976, "596");
		wishes.put((long) 265978, "test");

		fa.prepareWishList(wishes);
	}

	@Test(expected = FirstArrivedException.class)
	public void testPrepareWishListFloat() throws FirstArrivedException {
		Map<Long, String> wishes = new HashMap<Long, String>();
		wishes.put((long) 256395, "1");
		wishes.put((long) 258976, "596");
		wishes.put((long) 265978, "63.45");

		fa.prepareWishList(wishes);
	}

	@Test(expected = FirstArrivedException.class)
	public void testPrepareWishListNull() throws FirstArrivedException {
		Map<Long, String> wishes = null;

		fa.prepareWishList(wishes);
	}

	@Test
	public void testPrepareWishListEmpty() throws FirstArrivedException {
		Map<Long, String> wishes = new HashMap<Long, String>();
		Map<Integer, Long> expected = new HashMap<Integer, Long>();

		Assert.assertEquals(expected, fa.prepareWishList(wishes));
	}

	/*
	 * TESTS ON GLOBAL METHOD
	 */

	@Test(expected = FirstArrivedException.class)
	public void testSurveyNull() throws FirstArrivedException {
		fa.generateResult(null, answers);
	}

	@Test(expected = FirstArrivedException.class)
	public void testListAnswersNull() throws FirstArrivedException {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2016);
		date.set(Calendar.DAY_OF_YEAR, 32);
		survey.setEndDate(new Date(date.getTimeInMillis()));

		fa.generateResult(survey, null);
	}

	@Test(expected = FirstArrivedException.class)
	public void testListAnswersEmpty() throws FirstArrivedException {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2014);
		date.set(Calendar.DAY_OF_YEAR, 32);
		survey.setEndDate(new Date(date.getTimeInMillis()));

		fa.generateResult(survey, answers);
	}

	@Test(expected = FirstArrivedException.class)
	public void testEndDateAfterNow() throws FirstArrivedException {
		// Set valid answers
		answers.add(answer2);
		answers.add(answer);
		answers.add(answer3);
		answers.add(answer4);

		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2017);
		date.set(Calendar.DAY_OF_YEAR, 32);
		survey.setEndDate(new Date(date.getTimeInMillis()));

		fa.generateResult(survey, answers);
	}

	@Test
	public void simpleTest() throws FirstArrivedException {
		// Set valid answers
		answers.add(answer2);
		answers.add(answer);
		answers.add(answer3);
		answers.add(answer4);
		
		Calendar date = new GregorianCalendar();
		date.set(Calendar.YEAR, 2014);
		date.set(Calendar.DAY_OF_YEAR, 32);
		survey.setEndDate(new Date(date.getTimeInMillis()));
		
		//Expected result
		Map<Long,List<User>> expected = new HashMap<Long,List<User>>();
		List<User> usersChoice1 = new ArrayList<User>();
		usersChoice1.add(users.get(0));
		expected.put((long) 1, usersChoice1);
		
		List<User> usersChoice2 = new ArrayList<User>();
		usersChoice2.add(users.get(3));
		expected.put((long) 2, usersChoice2);

		List<User> usersChoice3 = new ArrayList<User>();
		usersChoice3.add(users.get(1));
		usersChoice3.add(users.get(2));
		expected.put((long) 3, usersChoice3);
		
		Map<Long,List<User>> result = fa.generateResult(survey, answers);
		
		
		System.out.println();
		
		Assert.assertEquals(expected, result);
		
	}
	
	

}
