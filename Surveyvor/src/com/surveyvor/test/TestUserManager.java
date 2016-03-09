package com.surveyvor.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.surveyvor.manager.UserManager;
import com.surveyvor.model.User;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "Test-context.xml" })
public class TestUserManager {

	@Autowired
	UserManager userManager;
		
	@Test
	public void testAddUser() {
		User user = new User("nom", "prenom", "mail@mail.com", "password", false);
		userManager.add(user);
		List<User> users = (List<User>) userManager.findUsers();
		assertTrue(users.size() == 1 ); 
		}
	
	@Test(expected=org.springframework.transaction.TransactionSystemException.class)
	public void testAddUserNullName() {
		User user = new User(null, "prenom", "mail@mail.com", "password", false);
		userManager.add(user);
		List<User> users = (List<User>) userManager.findUsers();
		assertTrue(users.size() == 1 ); 
		}
	
	
	@Test(expected=org.springframework.transaction.TransactionSystemException.class)
	public void testAddUserNotFormatNameShort() {
		User user = new User("i", "prenom", "mail@mail.com", "password", false);
		userManager.add(user);
		}
	
	@Test(expected=org.springframework.transaction.TransactionSystemException.class)
	public void testAddUserNotFormatNameLong() {
		User user = new User("azertyuiopmlkjhgfdqswxcv", "prenom", "mail@mail.com", "password", false);
		userManager.add(user);
		}
	

}