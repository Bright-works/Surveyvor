/**
 * @author LÈonore des PLAS
 * @date 11 mars 2016
 */
package com.surveyvor.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

import com.surveyvor.model.Survey;
import com.surveyvor.model.User;

/**
 * @author LÈonore des PLAS
 *
 */
@Service
public class MailSender {
	Session session;

	public MailSender() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("no.reply.myapp@gmail.com", "admin.myapp");
			}
		});
	}
	
	public void sendInvitation(Survey survey, String url) throws MessagingException{
		List<String> mailingList = survey.getDiffusion();
		
		for(String mailAdress : mailingList){
			
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAdress));
			msg.setSubject("Invitation au sondage " + survey.getTitle());
			
			MimeMultipart mm = new MimeMultipart("alternative");
			
			MimeBodyPart html = new MimeBodyPart();
			html.setContent("<h1>Bonjour,</h1></br>"
					+ "<p>Vous recevez ce mail car vous Ítes invitÈ au sondage '" + survey.getTitle() + "'.</p>"
					+ "<p>Pour y rÈpondre, veuillez cliquer sur le lien suivant:</p>"
					+ "<p><a href='"+url+"'>"+url+"</a></p>", "text/html");
			
			MimeBodyPart txt = new MimeBodyPart();
			txt.setText("Bonjour,\n"
					+ "Vous recevez ce mail car vous Ítes invitÈ au sondage '" + survey.getTitle() + "'.\n"
					+ "Pour y rÈpondre, veuillez copier le lien suivant et le coller dans votre navigateur:\n"
					+ url);
			
			mm.addBodyPart(txt);
			mm.addBodyPart(html);
			msg.setContent(mm);
			Transport.send(msg);
		}
	}
	
	public void sendResetPassword(String email, String url) throws MessagingException{		
		
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.setSubject("Surveyvor reset password");
			
			MimeMultipart mm = new MimeMultipart("alternative");
			
			MimeBodyPart html = new MimeBodyPart();
			html.setContent("<h1>Bonjour,</h1></br>"
					+ "<p>Votre mot de passe est oublié, pour résoudre ce problème, vous trouverez ci-dessous un lien"
					+ " pour définir votre nouveau mot de passe.</p>"
					+ "<p><a href='"+url+"'>"+url+"</a></p>", "text/html");
			
			MimeBodyPart txt = new MimeBodyPart();
			txt.setText("Bonjour,\n"
					+ "Votre mot de passe est oublié, pour résoudre ce problème, vous trouverez ci-dessous un lien.\n"
					+ "pour définir votre nouveau mot de passe:\n"
					+ url);
			
			mm.addBodyPart(txt);
			mm.addBodyPart(html);
			msg.setContent(mm);
			Transport.send(msg);
	}
	
	public void notifySurveyEnded(Survey survey) throws MessagingException{
		//TODO Mettre le vrai lien vers la visualisation des rÈponses du sondage
		String url = "";
		User creator = survey.getCreator();
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(creator.getMail()));
		msg.setSubject("Le sondage " + survey.getTitle() + " vient de se terminer");
		
		MimeMultipart mm = new MimeMultipart("alternative");
		
		MimeBodyPart html = new MimeBodyPart();
		html.setContent("<h1>Bonjour " + creator.getName() + ",</h1></br>"
				+ " <p>Vous recevez ce mail car le sondage <b>" + survey.getTitle()
				+ "</b> que vous avez crÈÈ vient de se terminer.</p>"
				+ "<p>Pour en consulter les rÈponses, veuillez cliquer sur le lien suivant:</p>"
				+ "<p><a href='"+url+"'>"+url+"</a></p>"
				+ "<p>Vous pouvez Ègalement y accÈder ‡ partir de votre profil.</p>", "text/html");
		
		MimeBodyPart txt = new MimeBodyPart();
		txt.setText("Bonjour " + creator.getName() + ",\n"
				+ "Vous recevez ce mail car le sondage '" + survey.getTitle() 
				+ "' que vous avez crÈÈ vient de se terminer.\n"
				+ "Pour en consulter les rÈponses, veuillez copier le lien suivant et le coller "
				+ "dans votre navigateur:\n" + url + "\nVous pouvez Ègalement y accÈder ‡ partir de votre profil.");
		
		mm.addBodyPart(txt);
		mm.addBodyPart(html);
		msg.setContent(mm);
		Transport.send(msg);
	}
	
	public void sendOwnResultOnly(Survey survey, User user, List<String> groups) throws MessagingException{
		String groupnames = "";
		for(int i=0; i<groups.size()-1; i++){
			groupnames = groupnames.concat(groups.get(i)+", ");
		}
		groupnames = groupnames.concat(groups.get(groups.size()-1)+".");
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
		msg.setSubject("Affectation relative au sondage " + survey.getTitle());
		
		MimeMultipart mm = new MimeMultipart("alternative");
		
		MimeBodyPart html = new MimeBodyPart();
		html.setContent("<h1>Bonjour " + user.getName() + ",</h1></br>"
				+ " <p>Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage <b>" + survey.getTitle()
				+ "</b> qui est maintenant terminÈ.</p>" 
				+ "<p>Vous Ítes affectÈ au(x) groupe(s) suivant(s): "+groupnames+"</p>"
				+ "<p>Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
				+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".</p>", "text/html");
		
		MimeBodyPart txt = new MimeBodyPart();
		txt.setText("Bonjour " + user.getName() + ",\n"
				+ "Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage '" + survey.getTitle()
				+ "' qui est maintenant terminÈ.\n"
				+ "Vous Ítes affectÈ au(x) groupe(s) suivant(s): " + groupnames + "\n"
				+ "Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
				+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".");
		
		mm.addBodyPart(txt);
		mm.addBodyPart(html);
		msg.setContent(mm);
		Transport.send(msg);
	}
	
	public void sendGroupResult(Survey survey, List<User> users, String groupname) throws MessagingException{
		String listUsersHtml = "<ul>";
		String listUsersTxt = "";
		for(User user : users){
			listUsersHtml = listUsersHtml.concat("<li>"+user.getName()+" "+user.getLastName()+"</li>");
			listUsersTxt = listUsersTxt.concat("\t- "+user.getName()+" "+user.getLastName()+"\n");
		}
		listUsersHtml = listUsersHtml.concat("</ul>");
		for (User user : users) {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
			msg.setSubject("Affectation relative au sondage " + survey.getTitle());
			
			MimeMultipart mm = new MimeMultipart("alternative");
			
			MimeBodyPart html = new MimeBodyPart();
			html.setContent("<h1>Bonjour " + user.getName() + ",</h1></br>"
					+ " <p>Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage <b>" + survey.getTitle()
					+ "</b> qui est maintenant terminÈ.</p>" 
					+ "<p>Vous avez ÈtÈ affectÈ au groupe <b>"+groupname+"</b>.</p>"
					+ "<p>La constitution de ce groupe est la suivante: </p>"
					+ listUsersHtml
					+ "<p>Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
					+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".</p>", "text/html");
			
			MimeBodyPart txt = new MimeBodyPart();
			txt.setText("Bonjour " + user.getName() + ",\n"
					+ "Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage '" + survey.getTitle()
					+ "' qui est maintenant terminÈ.\n"
					+ "Vous Ítes affectÈ au groupe: '"+groupname+"'.\n"
					+ "La constitution de ce groupe est la suivante:\n"
					+ listUsersTxt
					+ "Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
					+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".");
			
			mm.addBodyPart(txt);
			mm.addBodyPart(html);
			msg.setContent(mm);
			Transport.send(msg);
		}
	}

	public void sendAllResults(Survey survey, List<User> users, Map<String, List<String>> result)
			throws MessagingException {
		//Construction of the table + list
		String table = 
				"<table width='100%' cellspacing='2' cellpadding='0' border='0' align='center' bgcolor='#ff6600'>"
				+ "<tr bgcolor='#ffffff'>"
					+ "<th height='50'>Etudiant</th>"
					+ "<th>Groupe</th>"
				+ "</tr>";
		String listText = "";
		for(String groupname : result.keySet()){
			listText = listText.concat("\t- Groupe '"+groupname+"':\n");
			for(String username : result.get(groupname)){
				String tr = "<tr bgcolor='#ffffff'>"
							+ "<td width='60%' height='25' align='center'>" + username + "</td>"
							+ "<td width='40%' height='25' align='center'>" + groupname + "</td>";
				table = table.concat(tr);
				listText = listText.concat("\t\t- "+username+"\n");
			}
		}
		table = table.concat("</table>");
		
		for (User user : users) {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("no.reply.myapp@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
			msg.setSubject("Affectation relative au sondage " + survey.getTitle());
			
			MimeMultipart mm = new MimeMultipart("alternative");
			
			MimeBodyPart html = new MimeBodyPart();
			html.setContent("<h1>Bonjour " + user.getName() + ",</h1></br>"
					+ " <p>Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage '" + survey.getTitle()
					+ "' qui est maintenant terminÈ.</p>" 
					+ "<p>Voici la liste des affectations:</p>"
					+ table 
					+ "<p>Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
					+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".</p>", "text/html");
			
			MimeBodyPart txt = new MimeBodyPart();
			txt.setText("Bonjour " + user.getName() + ",\n"
					+ "Vous recevez ce mail car vous avez ÈtÈ invitÈ au sondage '" + survey.getTitle()
					+ "' qui est maintenant terminÈ.\n"
					+ "Voici la liste des affectations:\n"
					+ listText
					+ "Si vous avez une rÈclamation vis ‡ vis de votre affectation, ne rÈpondez pas ‡ ce mail."
					+ "Addressez-vous ‡ l'adresse " + survey.getCreator().getMail() + ".");
			
			mm.addBodyPart(txt);
			mm.addBodyPart(html);
			msg.setContent(mm);
			Transport.send(msg);
		}
	}
}
