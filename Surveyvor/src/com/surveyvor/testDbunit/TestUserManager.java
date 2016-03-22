package com.surveyvor.testDbunit;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.ebi.spring.dbunit.test.DataSet;
import com.excilys.ebi.spring.dbunit.test.DataSetTestExecutionListener;
import com.excilys.ebi.spring.dbunit.test.ExpectedDataSet;
import com.surveyvor.manager.SurveyManager;
import com.surveyvor.manager.UserManager;

import com.surveyvor.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "Test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DataSetTestExecutionListener.class })
@ActiveProfiles(profiles = "implementation")
public class TestUserManager {

	@Autowired
	UserManager userManager;

	@Autowired
	SurveyManager surveyManager;

	@Test
	@DataSet("donnees-add.xml")
	public void testFindUsers() throws ParseException {
		assertTrue(userManager.findUsers().size() == 2);
	}

	@Test
	@DataSet("donnees-add.xml")
	@ExpectedDataSet("expectedAddUser.xml")
	public void testAddUser() {
		User user = new User("Sarah", "Boukris", "sarahboukris@hotmail.fr", "boukris12", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserNull() throws ParseException {
		User user = null;
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserNameNull() throws ParseException {
		User user = new User(null, "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserNameShort() throws ParseException {
		User user = new User("n", "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserNameLimitShort() throws ParseException {
		User user = new User("ne", "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserNameLimitHigh() throws ParseException {
		User user = new User("azertyuiopqsdfg", "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserNotFormatNameLong() {
		User user = new User("asfddededdedeededy", "prenom", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserLastNameNull() throws ParseException {
		User user = new User("name", null, "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserLastNameShort() throws ParseException {
		User user = new User("name", "p", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserLastNameLimitShort() throws ParseException {
		User user = new User("name", "pr", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserLastNameLimitHigh() throws ParseException {
		User user = new User("name", "azertyuiopqsdfg", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserNotFormatLastsNameLong() {
		User user = new User("name", "nasfddededdedeededyame", "mail7@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserMailNull() throws ParseException {
		User user = new User("name", "prenom", null, "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserMailBadFormat() throws ParseException {
		User user = new User("name", "prenom", "mail", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.persistence.PersistenceException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserMailExisting() throws ParseException {
		User user = new User("name", "prenom", "mail5@mail.com", "password", "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserPasswordNull() throws ParseException {
		User user = new User("name", "prenom", "mail7@mail.com", null, "ROLE_USER");
		userManager.add(user);
	}

	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserPasswordShort() throws ParseException {
		User user = new User("name", "prenom", "mail7@mail.com", "pas", "ROLE_USER");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserPasswordLimitShort() throws ParseException {
		User user = new User("name", "prenom", "mail7@mail.com", "pass", "ROLE_USER");
		userManager.add(user);
	}


	@Test(expected = javax.validation.ConstraintViolationException.class)
	@DataSet("donnees-add.xml")
	public void testAddUserRoleNull() throws ParseException {
		User user = new User("name", "prenom", "mail7@mail.com", "password", null);
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAddUserRoleEmpty() throws ParseException {
		User user = new User("name", "prenom", "mail7@mail.com", "password", "");
		userManager.add(user);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testFindSurveyCreated() {
		User creator = userManager.findByMail("mail5@mail.com");
		assertTrue(userManager.allSurveysCreated(creator.getId()).size() == 1);
	}

	@Test
	@DataSet("donnees-add.xml")
	@ExpectedDataSet("donnesRemove.xml")
	public void testRemoveUserExists() {
		User creator = userManager.findByMail("mail5@mail.com");
		userManager.removeUser(creator.getId());
	}

	@Test(expected = java.lang.NullPointerException.class)
	@DataSet("donnees-add.xml")
	public void testRemoveUserNull() {
		userManager.removeUser((Long) null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	@DataSet("donnees-add.xml")
	public void testRemoveUserNotExist() {
		userManager.removeUser((long)10);
	}

	@Test(expected = java.lang.NullPointerException.class)
	@DataSet("donnees-add.xml")
	public void testFindUserNull() {
		userManager.findUser((Long) null);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testFindUserNotExist() {
		assertNull(userManager.findUser(10));
	}

	@Test(expected = javax.persistence.NoResultException.class)
	@DataSet("donnees-add.xml")
	public void testFindByMailUserNull() {
		userManager.findByMail(null);
	}

	@Test(expected = javax.persistence.NoResultException.class)
	@DataSet("donnees-add.xml")
	public void testFindByMailUserNotExist() {
		userManager.findByMail("mail@mail.com");
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testFindByMailUserExist() {
		assertNotNull(userManager.findByMail("mail5@mail.com"));
	}

	@Test
	@DataSet("donnees-add.xml")
	@ExpectedDataSet("donnees.xml")
	public void testRemove() {
		User creator = userManager.findByMail("mail5@mail.com");
		creator.getOwnedSurveys().remove(0);
		userManager.update(creator);
		assertTrue(userManager.allSurveysCreated(creator.getId()).size() == 0);
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAllSurveysCreated() {
		User creator = userManager.findByMail("mail5@mail.com");
		assertTrue(userManager.allSurveysCreated(creator.getId()).size() == 1);
	}

	@Test(expected = java.lang.NullPointerException.class)
	@DataSet("donnees-add.xml")
	public void testAllSurveysCreatedCreatorNull() {
		userManager.allSurveysCreated((Long) null);
	}

	@DataSet("donnees-add.xml")
	public void testAllSurveysCreatedCreatorNotExist() {
		assertTrue(userManager.allSurveysCreated(10).isEmpty());
	}

	@DataSet("donnees-add.xml")
	public void testAllSurveysCreatedByNotCreator() {
		User creator = userManager.findByMail("mail6@mail.com");
		assertTrue(userManager.allSurveysCreated(10).isEmpty());
	}

	@Test
	@DataSet("donnees-add.xml")
	public void testAllSurveysAnswered() {
		User answerer = userManager.findByMail("mail6@mail.com");
		assertTrue(userManager.allSurveysAnswered(answerer).size() == 1);
	}

	@Test(expected = java.lang.NullPointerException.class)
	@DataSet("donnees-add.xml")
	public void testAllSurveysAnsweredCreatorNull() {
		userManager.allSurveysAnswered(null);
	}

	@DataSet("donnees-add.xml")
	public void testAllSurveysAnsweredAnswererNotExist() {
		assertTrue(userManager.allSurveysAnswered(new User()).isEmpty());
	}

	@DataSet("donnees-add.xml")
	public void testAllSurveysAnsweredByNotAnswerer() {
		User notAnswerer = userManager.findByMail("mail5@mail.com");
		assertTrue(userManager.allSurveysAnswered(notAnswerer).isEmpty());
	}
	
	
	@Test
	@DataSet("donnees-add.xml")
	public void testAllSurveysInvited() {
		User invited = userManager.findByMail("mail6@mail.com");
		assertTrue(userManager.allSurveysInvited(invited).size()==1);
	}

	@Test(expected = java.lang.NullPointerException.class)
	@DataSet("donnees-add.xml")
	public void testAllSurveysInvitedUserNull() {
		userManager.allSurveysInvited(null);
	}

	@DataSet("donnees-add.xml")
	public void testAllSurveysInvitedUserNotExist() {
		assertTrue(userManager.allSurveysInvited(new User()).isEmpty());
	}
	
	@DataSet("donnees-add.xml")
	public void testAllSurveysInvitedNotInvited() {
		User notInvited = userManager.findByMail("mail5@mail.com");
		assertTrue(userManager.allSurveysInvited(notInvited).isEmpty());
	}

}
