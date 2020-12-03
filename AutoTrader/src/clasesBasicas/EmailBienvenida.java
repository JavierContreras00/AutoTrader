package clasesBasicas;

import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.text.pdf.PdfWriter;

public class EmailBienvenida {

	/**
	 * Clase que permite mandar un email de manera automática con el SMTP
	 * 
	 */

	/**
	 * Función que manda el mail
	 * 
	 * @param host         host del ordenador
	 * @param port         puerto del ordenador
	 * @param propietario  El que manda el email
	 * @param contrasenia  contraseña del email
	 * @param destinatario El que recibe el email
	 * @param asunto       Asunto del email
	 * @param mensaje      Mensaje del email
	 * @throws AddressException   Exepciones para la gestión de errores
	 * @throws MessagingException
	 */

	public static void mandarMail(String host, String port, final String propietario, final String contrasenia,
			String destinatario, String asunto, String mensaje) throws AddressException, MessagingException {

		// Establece los properties del servidor SMTP
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Crea una nueva sesion con una autenticacion
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(propietario, contrasenia);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// Crea un email
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(propietario));
		InternetAddress[] toAddresses = { new InternetAddress(destinatario) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(asunto);
		msg.setSentDate(new Date());

		// establecer mensaje de texto
		msg.setContent(mensaje, "text/html");

		// Envia el email
		Transport.send(msg);

	}

	public static void mandarMailConArchivo(String host, String port, final String propietario,
			final String contrasenia, String destinatario, String asunto, String mensaje, String fichero)
			throws AddressException, MessagingException {

		// Establece los properties del servidor SMTP
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		// Crea una nueva sesion con una autenticacion
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(propietario, contrasenia);
			}
		};

		Session session = Session.getInstance(properties, auth);

		// Create a default MimeMessage object.
		Message message = new MimeMessage(session);

		// Set From: header field of the header.
		message.setFrom(new InternetAddress(propietario));

		// Set To: header field of the header.
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));

		// Set Subject: header field
		message.setSubject(asunto);

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Now set the actual message
		messageBodyPart.setText(mensaje);

		// Create a multipar message
		Multipart contenido = new MimeMultipart();

		// Set text message part
		contenido.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(fichero);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fichero);
		contenido.addBodyPart(messageBodyPart);

		// Send the complete message parts
		message.setContent(contenido);

		// Send message
		Transport.send(message);

		System.out.println("Sent message successfully....");

	}

}
