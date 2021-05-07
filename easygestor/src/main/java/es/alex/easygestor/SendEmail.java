package es.alex.easygestor;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * importante es necesario configurar
 * Permitir el acceso de aplicaciones menos seguras.
 * @author AddPortatil
 *
 */
public class SendEmail {
	
	static final String FROM = "anajr991@gmail.com";
	static final String FROMNAME = "anajr991";
	static final String SMTP_USERNAME = "anajr991";
	static final String SMTP_PASSWORD = "Kuro.Neko.32";
	static final String HOST = "smtp.gmail.com";
	static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
	static final int PORT = 587;
	
	String TO = "estaneurona@gmail.com";

	
	static final String BODY = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1>Amazon SES SMTP Email Test</h1>",
    	    "<p>This email was sent with Amazon SES using the ", 
    	    "<a href='https://github.com/javaee/javamail'>Javamail Package</a>",
    	    " for <a href='https://www.java.com'>Java</a>."
    	);
	
	public SendEmail(String to) {
		this.TO = to;
		
	}
	
	public void send() throws MessagingException, UnsupportedEncodingException {
		// crear propiedades
		Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
    	
    	//creamos un ojbeto sesion 
    	Session session = Session.getDefaultInstance(props);
    	
    	//creamos un mensaje con nuestra sesion
    	MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
    	
        
     // Create a transport.
        Transport transport = session.getTransport("smtp");
        
     // Connect to Amazon SES using the SMTP username and password you specified above.
        transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Email sent!");
	}
	
}
