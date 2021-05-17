package es.alex.easygestor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.ConexionJson;


/**
 * importante es necesario configurar
 * Permitir el acceso de aplicaciones menos seguras.
 * @author AddPortatil
 *
 */
public class SendEmail {
	
	private String from;
	private  String fromName;
	//private  String SMTP_USERNAME = "anajr991";
	private  String SMTP_USERNAME;
	//private  String SMTP_PASSWORD = "Kuro.Neko.32";
	private  String SMTP_PASSWORD;
	static final String HOST = "smtp.gmail.com";
	static final String SUBJECT = "Plazo de préstamo terminado.";
	static final int PORT = 587;
	
	private String TO = "estaneurona@gmail.com";

	
	static final String BODY = String.join(
    	    System.getProperty("line.separator"),
    	    "<h1 style=\"text-align: center;\"><span style=\"color: #ff0000;\">&iexcl;Plazo de entrega",
    	    "sobrepasado!</span></h1>", 
    	    "<h3><span style=\"color: #000000;\">El plazo de entrega de su libro ha sido sobrepasado, se le",
    	    "ruega que realice la entrega cuento antes. Una mayor demora en la entrega puede suponer",
    	    "cargos adicionales.</span></h3>",
    	    "<hr />",
    	    "<p>Este es un correo automatico, no intente contestar a este mensaje.&nbsp;</p>"
    	);
	
	public SendEmail() {
		
		
	}
	/**
	 * Esta función recoge las variables estaticas y  envia el mensaje.
	 */
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
        msg.setFrom(new InternetAddress(from,fromName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
    	
        
     
        Transport transport = session.getTransport("smtp");
        
    
        transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        
        transport.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Email sent!");
	}
	
	
	
	public String getFROM() {
		return from;
	}
	public void setFROM(String fROM) {
		from = fROM;
	}
	public String getFROMNAME() {
		return fromName;
	}
	public void setFROMNAME(String fROMNAME) {
		fromName = fROMNAME;
	}
	public String getSMTP_USERNAME() {
		return SMTP_USERNAME;
	}
	public void setSMTP_USERNAME(String sMTP_USERNAME) {
		SMTP_USERNAME = sMTP_USERNAME;
	}
	public String getSMTP_PASSWORD() {
		return SMTP_PASSWORD;
	}
	public void setSMTP_PASSWORD(String sMTP_PASSWORD) {
		SMTP_PASSWORD = sMTP_PASSWORD;
	}
	public String getTO() {
		return TO;
	}
	public void setTO(String tO) {
		TO = tO;
	}
	
	
}
