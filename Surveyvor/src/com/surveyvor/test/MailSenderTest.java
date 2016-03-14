/**
 * @author Léonore des PLAS
 * @date 11 mars 2016
 */
package com.surveyvor.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.surveyvor.model.Survey;
import com.surveyvor.model.User;
import com.surveyvor.service.MailSender;

/**
 * @author Léonore des PLAS
 *
 */
public class MailSenderTest {
	private MailSender ms = new MailSender();
	private static Survey survey;
	private static List<User> users;
	
	@BeforeClass
	public static void init(){
		User creator = new User();
		creator.setMail("hanatori13@gmail.com");
		creator.setName("Léonore");
		survey = new Survey();
		survey.setCreator(creator);
		survey.setTitle("Sondage pour l'affectation des sujets de TER");
		List<String> mailingList = new ArrayList<String>();
		mailingList.add("hanatori13@gmail.com");
		mailingList.add("leonore.desplas@nordnet.fr");
		survey.setDiffusion(mailingList);
		
		users = new ArrayList<User>();
		User user = new User();
		user.setName("Léonore");
		user.setLastName("des Plas");
		user.setMail("hanatori13@gmail.com");
		users.add(user);
	}
	
	@Test
	public void testSendAllResults() throws MessagingException {
		Map<String,List<String>> result = new HashMap<String,List<String>>();
		List<String> choice1 = new ArrayList<String>();
		choice1.add("Léonore des Plas");
		choice1.add("Kandel Achirafi");
		choice1.add("David Sebban");
		result.put("ISL", choice1);
		List<String> choice2 = new ArrayList<String>();
		choice2.add("Sarah ");
		result.put("FSI", choice2);
		
		ms.sendAllResults(survey, users, result);
	}
	
	@Test
	public void testSendOwnResult() throws MessagingException{
		List<String> groupnames = new ArrayList<String>();
		groupnames.add("FSI");
		ms.sendOwnResultOnly(survey, users.get(0), groupnames);
	}

	@Test
	public void testSendGroupResult() throws MessagingException{
		User buddy = new User();
		buddy.setName("Renaud");
		buddy.setLastName("Mattei");
		buddy.setMail("leonore.desplas@nordnet.fr");
		users.add(buddy);
		ms.sendGroupResult(survey, users, "This is a veeeeeeeeery long groupname");
	}
	
	@Test
	public void testSendNotifySurveyEnded() throws MessagingException{
		ms.notifySurveyEnded(survey);
	}
	
	@Test
	public void testSendInvitation() throws MessagingException{
		ms.sendInvitation(survey, "www.test.fr");
	}
}
