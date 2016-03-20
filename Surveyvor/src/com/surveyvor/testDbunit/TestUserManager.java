package com.surveyvor.testDbunit;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.excilys.ebi.spring.dbunit.test.ExpectedDataSet;
import com.surveyvor.manager.UserManager;

import com.surveyvor.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "Test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@ActiveProfiles(profiles = "implementation")
public class TestUserManager {

	@Autowired
	UserManager userManager;

	@Test
	@DataSet("donnees.xml")
	@ExpectedDataSet("donnees.xml")
	public void testFindUsers() throws ParseException {
		assertTrue(userManager.findUsers().size() == 2);
	}

	@Test
	@DataSet("donnees.xml")
	@ExpectedDataSet("expectedAddUser.xml")
	public void testAddUser() {
		User user = new User("Sarah", "Boukris", "sarahboukris@hotmail.fr", "boukris12", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	@DataSet("donnees.xml")
	public void testAddUserNull() throws ParseException {
		User user = null;
		userManager.add(user);
	}

	@Test(expected = org.springframework.transaction.TransactionSystemException.class)
	@DataSet("donnees.xml")
	public void testAddUserNameShort() throws ParseException {
		User user = new User("n", "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	/*
	 * @Test(expected =
	 * org.springframework.transaction.TransactionSystemException.class)
	 * 
	 * @DatabaseSetup("donnees.xml") public void testAddUserNameLimitShort()
	 * throws ParseException { User user = new User("ne", "prenom",
	 * "mail7@mail.com", "password", "ROLE_USER"); userManager.add(user); }
	 * 
	 * @Test(expected =
	 * org.springframework.transaction.TransactionSystemException.class)
	 * 
	 * @DatabaseSetup("donnees.xml") public void testAddUserNameLimitHigh()
	 * throws ParseException { User user = new User("azertyuiopqsdfg", "prenom",
	 * "mail7@mail.com", "password", "ROLE_USER"); userManager.add(user); }
	 * 
	 * @Test(expected =
	 * org.springframework.transaction.TransactionSystemException.class)
	 * 
	 * @DatabaseSetup("donnees.xml") public void testAddUserNotFormatNameLong()
	 * { User user = new User("azertyuiopmlkjhgfdqswxcv", "prenom",
	 * "mail@mail.com", "password", "ROLE_USER"); userManager.add(user); }
	 * 
	 * @Test
	 * 
	 * @DatabaseSetup("donnees-add.xml") public void testFindSurveyCreated() {
	 * User creator = userManager.findByMail("mail5@mail.com");
	 * assertTrue(userManager.allSurveysCreated(creator.getId()).size() == 1); }
	 */
}
