//package com.surveyvor.testDbunit;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import static org.junit.Assert.assertTrue;
//
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.ConsoleAppender;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PatternLayout;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//
//import com.github.springtestdbunit.DbUnitTestExecutionListener;
//import com.github.springtestdbunit.annotation.DatabaseSetup;
//import com.github.springtestdbunit.annotation.DbUnitConfiguration;
//import com.github.springtestdbunit.annotation.ExpectedDatabase;
//import com.surveyvor.manager.SurveyManager;
//import com.surveyvor.manager.UserManager;
//import com.surveyvor.model.Choice;
//import com.surveyvor.model.Question;
//import com.surveyvor.model.QuestionParameters;
//import com.surveyvor.model.Survey;
//import com.surveyvor.model.SurveyParameters;
//import com.surveyvor.model.TypeSurvey;
//import com.surveyvor.model.User;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "Test-context.xml" })
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//        DbUnitTestExecutionListener.class })
//public class TestSurveyManager {
//
//	@Autowired
//	UserManager userManager;
//
//	
//	@Test
//    @DatabaseSetup("donnees.xml")
//    public void testFind() throws ParseException {
//		User user = new User("nom", "prenom", "mail7@mail.com", "password", "ROLE_USER");
//		userManager.add(user);
//		assertTrue(userManager.findUsers().size() == 3);
//	}
//	
//	@Test
//    @DatabaseSetup("donnees-add.xml")
//	public void testFindSurveyCreated() {
//		User creator = userManager.findByMail("mail5@mail.com");
//		assertTrue( userManager.allSurveysCreated(creator.getId()).size()==1 ); 
//		}
//}
