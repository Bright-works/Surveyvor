/**
 * @author 
 * @date 21 mars 2016
 */
package com.surveyvor.jobScheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ApplicationScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.surveyvor.manager.SurveyManager;
import com.surveyvor.model.Survey;
import com.surveyvor.model.User;
import com.surveyvor.service.MailSender;

/**
 * @author hanatori
 *
 */
@Component("mainJobBean")
@ApplicationScoped
public class MainJob {
	
	@Autowired
	SurveyManager surveyManager;
	
	@Autowired
	MailSender mailSender;
	
	public void sendNotificationMessage() throws MessagingException{
		System.out.println("CHECKING SURVEYS");
		List<Survey> endedSurveys = new ArrayList<Survey>(surveyManager.findJustEndedSurveys());
		for(Survey survey : endedSurveys){
			try {
				System.out.println("Sending mail");
				
				//TODO : Trouver pourquoi je n'arrive pas à appeler une fonction ici
				String url = "";
				User creator = survey.getCreator();
				
				System.out.println("sending to "+creator.getMail());
				
				

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				
				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("no.reply.myapp@gmail.com", "admin.myapp");
					}
				});
				
				
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(creator.getMail()));
				msg.setSubject("Le sondage " + survey.getTitle() + " vient de se terminer");
				
				MimeMultipart mm = new MimeMultipart("alternative");
				
				MimeBodyPart html = new MimeBodyPart();
				html.setContent("<h1>Bonjour " + creator.getName() + ",</h1></br>"
						+ " <p>Vous recevez ce mail car le sondage <b>" + survey.getTitle()
						+ "</b> que vous avez créé vient de se terminer.</p>"
						+ "<p>Pour en consulter les réponses, veuillez cliquer sur le lien suivant:</p>"
						+ "<p><a href='"+url+"'>"+url+"</a></p>"
						+ "<p>Vous pouvez également y accéder à partir de votre profil.</p>", "text/html");
				
				MimeBodyPart txt = new MimeBodyPart();
				txt.setText("Bonjour " + creator.getName() + ",\n"
						+ "Vous recevez ce mail car le sondage '" + survey.getTitle() 
						+ "' que vous avez créé vient de se terminer.\n"
						+ "Pour en consulter les réponses, veuillez copier le lien suivant et le coller "
						+ "dans votre navigateur:\n" + url + "\nVous pouvez également y accéder à partir de votre profil.");
				
				mm.addBodyPart(txt);
				mm.addBodyPart(html);
				msg.setContent(mm);
				Transport.send(msg);
			} catch (MessagingException e) {
				System.err.println("ERROR WHILE SENDING NOTIFICATION MAILS");
				e.printStackTrace();
			}
		}
	}
}
