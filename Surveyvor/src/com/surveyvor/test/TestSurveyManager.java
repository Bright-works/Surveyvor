package com.surveyvor.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.manager.UserManager;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Question;
import com.surveyvor.model.QuestionParameters;
import com.surveyvor.model.Survey;
import com.surveyvor.model.SurveyParameters;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;


@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "Test-context.xml" })
public class TestSurveyManager {

	@Autowired
	UserManager userManager;
	
	@Autowired
	SurveyManager surveyManager;
	
	
	@Test
	public void testAddSurvey() throws ParseException {
		User user = new User("nom", "prenom", "mail5@mail.com", "password", false);
		User answerer = new User("answerer", "prenom", "mail6@mail.com", "password", false);
		
		userManager.add(user);
		userManager.add(answerer);
		
		String d1 = "2020-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<String> diffusion = new ArrayList<String>();
		diffusion.add("lol@lol.fr");
		
		List<User> answerers = new ArrayList<User>();
		answerers.add(answerer);
		
		List<Choice> choices = new ArrayList<Choice>();
		Choice choice = new Choice("label","description aaaaaaaaaaaaaaaaaaa",2);
		choices.add(choice);
		choice = new Choice("label2","description aaaaaaaaaaaaaaaaaaa",4);
		choices.add(choice);
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("contenulolololol", "descriptionmpop", 1, 3, choices, new QuestionParameters(true, true, true));
		questions.add(question);
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("title", "descriiiiiiiiiption", date, date, diffusion, TypeSurvey.OPINION, answerers, questions, parametres);		
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		
		user.setOwnedSurveys(surveys);
		
		userManager.update(user);
		assertTrue(surveyManager.findSurveys().size()==1);
		}

}