import java.util.*;
import javax.mail.*;	
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;
	
/**
 * This class is able to Email a generated report to a recipient.
 * @author Amzad
 *
 */
public class Email {
	Properties settings;
	String username, password;
	
	/**
	 * Default constructer for the Email class. 
	 * @param recipient Who
	 * @param subject What
	 * @param content The Stuff
	 */
	public Email(String recipient, String subject, String content) {
		username = "cs370testemail@gmail.com";
		password = "cs370spring2018";
		
		settings = new Properties();
		settings.put("mail.smtp.auth", "true");
		settings.put("mail.smtp.starttls.enable", "true");
		settings.put("mail.smtp.host", "smtp.gmail.com");
		settings.put("mail.smtp.port", "587");
		
		createSession(recipient, subject, content);
		
	}
	
	/**
	 * This method creates the email session and sends the email.
	 */
	private void createSession(String recipient, String subject, String content) {
		Session session = Session.getInstance(settings, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);

			System.out.println("Email Sent");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
