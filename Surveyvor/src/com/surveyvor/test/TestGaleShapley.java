package com.surveyvor.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.surveyvor.exception.GaleShapleyException;
import com.surveyvor.exception.QuotatException;
import com.surveyvor.model.Answer;
import com.surveyvor.model.Choice;
import com.surveyvor.model.Question;
import com.surveyvor.model.QuestionParameters;
import com.surveyvor.model.Survey;
import com.surveyvor.model.SurveyParameters;
import com.surveyvor.model.TypeSurvey;
import com.surveyvor.model.User;
import com.surveyvor.service.GaleShapley;

public class TestGaleShapley {

	@Test
	public void testGaleShapleyPetitSameAnswer() throws ParseException, GaleShapleyException, QuotatException
	{
		User user = new User("Sebban","David","neodav13@hotmail.fr","sarahboukris","User");
		user.setId(Long.valueOf(1));
		User user1 = new User("Boukris","Sarah","sarahboukris@hotmail.fr","sarahboukris","User");
		user.setId(Long.valueOf(2));
		User user2 = new User("des Plas","Leonore","leonore@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(3));
		User user3 = new User("Bououarour","Redouane","redouane@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(4));
		User user4 = new User("Achirafi","Kandel","kandel@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(5));
		User user5 = new User("Perries","Laurent","laurent@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(6));
		
		List<User> answerers = new ArrayList<User>();
		answerers.add(user);answerers.add(user1);answerers.add(user2);answerers.add(user3);answerers.add(user4);
		
		
		String d1 = "2020-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<Choice> choices = new ArrayList<Choice>();
		Choice choice1 = new Choice("ISL","Integration System Logiciel",2);
		choice1.setId(Long.valueOf(1));
		choices.add(choice1);
		Choice choice2 = new Choice("FSI","Fiabilité Sécurité Intégration",4);
		choice2.setId(Long.valueOf(2));
		choices.add(choice2);
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		choice1.setQuestion(question); choice2.setQuestion(question);
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(user5);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		user5.setOwnedSurveys(surveys);
		
		List<Choice> choices1 = new ArrayList<Choice>();
		List<Choice> choices2 = new ArrayList<Choice>();
		choices1.add(choice1);choices1.add(choice2);
		choices2.add(choice2);choices2.add(choice1);
		
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		
		Answer answer = new Answer(Long.valueOf(1),user,question,choices1,valeurs,date);
		Answer answer1 = new Answer(Long.valueOf(2),user1,question,choices1,valeurs,date);
		Answer answer2 = new Answer(Long.valueOf(3),user2,question,choices1,valeurs,date);
		Answer answer3 = new Answer(Long.valueOf(4),user3,question,choices1,valeurs,date);
		Answer answer4 = new Answer(Long.valueOf(5),user4,question,choices1,valeurs,date);
		List<Answer> answers = new ArrayList<>();
		answers.add(answer);answers.add(answer1);answers.add(answer2);answers.add(answer3);answers.add(answer4);
		
		GaleShapley algorithme = new GaleShapley();
		Map<Long, List<User>> result = algorithme.generateResult(survey, answers);
		
		for(Entry<Long, List<User>> entry : result.entrySet()) {
		    Long cle = entry.getKey();
		    List<User> valeur = entry.getValue();
		    System.out.println("dans le choix : " + cle);
		    for (int i=0; i<valeur.size(); i++)
		    	System.out.println("user "+ valeur.get(i).getName());
		}
		
	}
	
	/*
	 * 
	 * */
	@Test
	public void testGaleShapleyPetit() throws ParseException, GaleShapleyException, QuotatException
	{
		User user = new User("Sebban","David","neodav13@hotmail.fr","sarahboukris","User");
		user.setId(Long.valueOf(1));
		User user1 = new User("Boukris","Sarah","sarahboukris@hotmail.fr","sarahboukris","User");
		user.setId(Long.valueOf(2));
		User user2 = new User("des Plas","Leonore","leonore@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(3));
		User user3 = new User("Bououarour","Redouane","redouane@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(4));
		User user4 = new User("Achirafi","Kandel","kandel@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(5));
		User user5 = new User("Perries","Laurent","laurent@hotmail.fr","motdepasse","User");
		user.setId(Long.valueOf(6));
		
		List<User> answerers = new ArrayList<User>();
		answerers.add(user);answerers.add(user1);answerers.add(user2);answerers.add(user3);answerers.add(user4);
		
		
		String d1 = "2020-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<Choice> choices = new ArrayList<Choice>();
		Choice choice1 = new Choice("ISL","Integration System Logiciel",2);
		choice1.setId(Long.valueOf(1));
		choices.add(choice1);
		Choice choice2 = new Choice("FSI","Fiabilité Sécurité Intégration",4);
		choice2.setId(Long.valueOf(2));
		choices.add(choice2);
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		choice1.setQuestion(question); choice2.setQuestion(question);
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(user5);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		user5.setOwnedSurveys(surveys);
		
		List<Choice> choices1 = new ArrayList<Choice>();
		List<Choice> choices2 = new ArrayList<Choice>();
		choices1.add(choice1);choices1.add(choice2);
		choices2.add(choice2);choices2.add(choice1);
		
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		
		Answer answer = new Answer(Long.valueOf(1),user,question,choices1,valeurs,date);
		Answer answer1 = new Answer(Long.valueOf(2),user1,question,choices1,valeurs,date);
		Answer answer2 = new Answer(Long.valueOf(3),user2,question,choices2,valeurs,date);
		Answer answer3 = new Answer(Long.valueOf(4),user3,question,choices2,valeurs,date);
		Answer answer4 = new Answer(Long.valueOf(5),user4,question,choices1,valeurs,date);
		List<Answer> answers = new ArrayList<>();
		answers.add(answer);answers.add(answer1);answers.add(answer2);answers.add(answer3);answers.add(answer4);
		
		GaleShapley algorithme = new GaleShapley();
		Map<Long, List<User>> result = algorithme.generateResult(survey, answers);
		
		for(Entry<Long, List<User>> entry : result.entrySet()) {
		    Long cle = entry.getKey();
		    List<User> valeur = entry.getValue();
		    System.out.println("dans le choix : " + cle);
		    for (int i=0; i<valeur.size(); i++)
		    	System.out.println("user "+ valeur.get(i).getName());
		}
		
	}
	/**/
	
	/*@Test
	public void testGaleShapleyMoyen() throws ParseException, GaleShapleyException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 7, 7, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i< 8; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",5);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2020-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer1.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		List<Choice> choiceAnswer2 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer2.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		List<Choice> choiceAnswer3 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer3.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		for (int i= 1; i<=25; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			if(i<=8)
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);
			else if(i>8 && i<=15)
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer2,valeurs,date);
			else
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer3,valeurs,date);
			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		Map<Long, List<User>> result = algorithme.generateResult(survey, answers);
		//Map<Long, Integer> repart = new HashMap<>();
		
		for(Entry<Long, List<User>> entry : result.entrySet()) {
		    Long cle = entry.getKey();
		    List<User> valeur = entry.getValue();
		    System.out.println("dans le choix : " + cle);
		    for (int i=0; i<valeur.size(); i++)
		    	System.out.print("user "+ valeur.get(i).getName());
		    System.out.println();
		}
		
	}*/
	
	/*@Test
	public void testGaleShapleyL3() throws ParseException, GaleShapleyException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 8, 8, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i< 9; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",35);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer1.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		List<Choice> choiceAnswer2 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer2.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		List<Choice> choiceAnswer3 = new ArrayList<>();
		choiceTest.addAll(choices);
		while (!choiceTest.isEmpty())
		{
			int alea = (int)(Math.random() * ((choiceTest.size())));
			choiceAnswer3.add(choiceTest.get(alea));
			choiceTest.remove(alea);
		}
		for (int i= 1; i<=100; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			if(i<=36)
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);
			else if(i>36 && i<=56)
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer2,valeurs,date);
			else
				answer = new Answer(Long.valueOf(1),user,question,choiceAnswer3,valeurs,date);
			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		Map<Long, List<User>> result = algorithme.generateResult(survey, answers);
		//Map<Long, Integer> repart = new HashMap<>();
		
		for(Entry<Long, List<User>> entry : result.entrySet()) {
		    Long cle = entry.getKey();
		    List<User> valeur = entry.getValue();
		    System.out.println("dans le choix : " + cle);
		    for (int i=0; i<valeur.size(); i++)
		    	System.out.print("user "+ valeur.get(i).getName());
		    System.out.println();
		}
		
	}*/
	
	@Test (expected = QuotatException.class)
	public void testGaleShapleyQuotat() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",0);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 
		choiceTest.addAll(choices);

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
		
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleyNullAnswers() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",1);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 
		choiceTest.addAll(choices);

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));			
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, null);
		
	}
	
	@Test
	public void testGaleShapleyAnswersEmpty() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",1);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 
		choiceTest.addAll(choices);

		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
		
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleyNullSurvey() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",0);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 

			User user = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
			user.setId(Long.valueOf(1));
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

			answers.add(answer);
	
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(null, answers);
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleyDateFuture() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",1);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2020-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 
		choiceTest.addAll(choices);

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
		
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleyChoicesNull() throws ParseException, GaleShapleyException, QuotatException
	{
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, null, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,null,valeurs,date);

			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
		
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleyChoicesEmpty() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 1, 1, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);

		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,choices,valeurs,date);

			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
		
	}
	
	@Test (expected = GaleShapleyException.class)
	public void testGaleShapleySameAnswer() throws ParseException, GaleShapleyException, QuotatException
	{

		List<Choice> choices = new ArrayList<Choice>();
		
		List<Question> questions = new ArrayList<Question>();
		Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
		question.setId(Long.valueOf(1));
		questions.add(question);
		
		for (int i= 1; i<= 2; i++)
		{
			Choice choice = new Choice("choix"+i,"description du choix",1);
			choice.setId(Long.valueOf(i));
			choice.setQuestion(question);
			choices.add(choice);
		}
		
		String d1 = "2009-02-16";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = df.parse(d1);
		
		List<User> answerers = new ArrayList<User>();
		Map<Long, String> valeurs = new HashMap<Long, String> ();
		List<Answer> answers = new ArrayList<>();
		List<Choice> choiceTest = new ArrayList<>();
		List<Choice> choiceAnswer1 = new ArrayList<>();
		choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); 
		choiceTest.addAll(choices);

		for (int i= 1; i<=1; i++)
		{
			User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
			user.setId(Long.valueOf(i));
			answerers.add(user);
			Answer answer;
			answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

			answers.add(answer);
			answers.add(answer);
		}
		
		User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		creator.setId(Long.valueOf(100));
		
		SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
		Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
		survey.setId(Long.valueOf(1));
		survey.setCreator(creator);
		List<Survey> surveys = new ArrayList<Survey>();
		surveys.add(survey);
		creator.setOwnedSurveys(surveys);
				
		GaleShapley algorithme = new GaleShapley();
		algorithme.generateResult(survey, answers);
}


@Test (expected = GaleShapleyException.class)
public void testGaleShapleyTooChoice() throws ParseException, GaleShapleyException, QuotatException
{

	List<Choice> choices = new ArrayList<Choice>();
	
	List<Question> questions = new ArrayList<Question>();
	Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
	question.setId(Long.valueOf(1));
	questions.add(question);
	
	for (int i= 1; i<= 2; i++)
	{
		Choice choice = new Choice("choix"+i,"description du choix",1);
		choice.setId(Long.valueOf(i));
		choice.setQuestion(question);
		choices.add(choice);
	}
	
	String d1 = "2009-02-16";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date = df.parse(d1);
	
	List<User> answerers = new ArrayList<User>();
	Map<Long, String> valeurs = new HashMap<Long, String> ();
	List<Answer> answers = new ArrayList<>();
	List<Choice> choiceTest = new ArrayList<>();
	List<Choice> choiceAnswer1 = new ArrayList<>();
	choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(choices.get(1)); choiceAnswer1.add(choices.get(1)); 
	choiceTest.addAll(choices);

	for (int i= 1; i<=1; i++)
	{
		User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
		user.setId(Long.valueOf(i));
		answerers.add(user);
		Answer answer;
		answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

		answers.add(answer);
	}
	
	User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
	creator.setId(Long.valueOf(100));
	
	SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
	Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
	survey.setId(Long.valueOf(1));
	survey.setCreator(creator);
	List<Survey> surveys = new ArrayList<Survey>();
	surveys.add(survey);
	creator.setOwnedSurveys(surveys);
			
	GaleShapley algorithme = new GaleShapley();
	algorithme.generateResult(survey, answers);
}

@Test (expected = GaleShapleyException.class)
public void testGaleShapleyFewChoice() throws ParseException, GaleShapleyException, QuotatException
{

	List<Choice> choices = new ArrayList<Choice>();
	
	List<Question> questions = new ArrayList<Question>();
	Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
	question.setId(Long.valueOf(1));
	questions.add(question);
	
	for (int i= 1; i<= 2; i++)
	{
		Choice choice = new Choice("choix"+i,"description du choix",1);
		choice.setId(Long.valueOf(i));
		choice.setQuestion(question);
		choices.add(choice);
	}
	
	String d1 = "2009-02-16";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date = df.parse(d1);
	
	List<User> answerers = new ArrayList<User>();
	Map<Long, String> valeurs = new HashMap<Long, String> ();
	List<Answer> answers = new ArrayList<>();
	List<Choice> choiceTest = new ArrayList<>();
	List<Choice> choiceAnswer1 = new ArrayList<>();
	choiceAnswer1.add(choices.get(0));
	choiceTest.addAll(choices);

	for (int i= 1; i<=1; i++)
	{
		User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
		user.setId(Long.valueOf(i));
		answerers.add(user);
		Answer answer;
		answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

		answers.add(answer);
	}
	
	User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
	creator.setId(Long.valueOf(100));
	
	SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
	Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
	survey.setId(Long.valueOf(1));
	survey.setCreator(creator);
	List<Survey> surveys = new ArrayList<Survey>();
	surveys.add(survey);
	creator.setOwnedSurveys(surveys);
			
	GaleShapley algorithme = new GaleShapley();
	algorithme.generateResult(survey, answers);
}

@Test (expected = GaleShapleyException.class)
public void testGaleShapleyChoiceNull() throws ParseException, GaleShapleyException, QuotatException
{

	List<Choice> choices = new ArrayList<Choice>();
	
	List<Question> questions = new ArrayList<Question>();
	Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 2, 2, choices, new QuestionParameters(true, true, true));
	question.setId(Long.valueOf(1));
	questions.add(question);
	
		Choice choice = new Choice("choix","description du choix",2);
		choice.setId(Long.valueOf(1));
		choice.setQuestion(question);
		choices.add(choice);
	
	
	
	String d1 = "2009-02-16";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date = df.parse(d1);
	
	List<User> answerers = new ArrayList<User>();
	Map<Long, String> valeurs = new HashMap<Long, String> ();
	List<Answer> answers = new ArrayList<>();
	List<Choice> choiceTest = new ArrayList<>();
	List<Choice> choiceAnswer1 = new ArrayList<>();
	choiceAnswer1.add(choices.get(0)); choiceAnswer1.add(null); 
	choiceTest.addAll(choices);

	for (int i= 1; i<=1; i++)
	{
		User user = new User("Nom"+i,"Prenom"+i,i+"mail@mail.fr","motdepasse"+i,"User");
		user.setId(Long.valueOf(i));
		answerers.add(user);
		Answer answer;
		answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

		answers.add(answer);
	}
	
	User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
	creator.setId(Long.valueOf(100));
	
	SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
	Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
	survey.setId(Long.valueOf(1));
	survey.setCreator(creator);
	List<Survey> surveys = new ArrayList<Survey>();
	surveys.add(survey);
	creator.setOwnedSurveys(surveys);
			
	GaleShapley algorithme = new GaleShapley();
	algorithme.generateResult(survey, answers);
}

@Test
public void testGaleShapleyLimitQuotat() throws ParseException, GaleShapleyException, QuotatException
{

	List<Choice> choices = new ArrayList<Choice>();
	
	List<Question> questions = new ArrayList<Question>();
	Question question = new Question("Choisissez votre filière pour le M2", "Par ordre de préférence", 1, 1, choices, new QuestionParameters(true, true, true));
	question.setId(Long.valueOf(1));
	questions.add(question);
	
		Choice choice = new Choice("choix","description du choix",1);
		choice.setId(Long.valueOf(1));
		choice.setQuestion(question);
		choices.add(choice);
	
	
	
	String d1 = "2009-02-16";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	Date date = df.parse(d1);
	
	List<User> answerers = new ArrayList<User>();
	Map<Long, String> valeurs = new HashMap<Long, String> ();
	List<Answer> answers = new ArrayList<>();
	List<Choice> choiceTest = new ArrayList<>();
	List<Choice> choiceAnswer1 = new ArrayList<>();
	choiceAnswer1.add(choices.get(0)); 
	choiceTest.addAll(choices);

	
		User user = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
		user.setId(Long.valueOf(1));
		answerers.add(user);
		Answer answer;
		answer = new Answer(Long.valueOf(1),user,question,choiceAnswer1,valeurs,date);

		answers.add(answer);
	
	
	User creator = new User("Nom","Prenom","mail@mail.fr","motdepasse","User");
	creator.setId(Long.valueOf(100));
	
	SurveyParameters parametres = new SurveyParameters(true, true, true, 1);
	Survey survey = new Survey("Choix  filières ", "le choix de la filière de M2", date, date, null, TypeSurvey.REPARTITION, answerers, questions, parametres);		
	survey.setId(Long.valueOf(1));
	survey.setCreator(creator);
	List<Survey> surveys = new ArrayList<Survey>();
	surveys.add(survey);
	creator.setOwnedSurveys(surveys);
			
	GaleShapley algorithme = new GaleShapley();
	algorithme.generateResult(survey, answers);
}

}