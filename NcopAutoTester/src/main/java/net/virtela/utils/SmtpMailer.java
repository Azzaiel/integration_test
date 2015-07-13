package net.virtela.utils;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.virtela.model.Mail;
/**
 * @author rreyles@virtela.net
 */
public class SmtpMailer {

	public static void sendErrorMail(StackTraceElement[] stackTrace, String module) {
		final StringBuffer errorDump = new StringBuffer();
		for (StackTraceElement trace : stackTrace) {
			errorDump.append(trace);
			errorDump.append(Constants.HTML_NEW_LINE);
		}
		String subject = "Error Occured on " + module;
		final String body = errorDump.toString();
		sendMail(new Mail("rreyles@virtela.net", body, subject));

	}

	public static void sendErrorMail(String error, String module) {
		String subject = "Error Occured on " + module;
		final String body = error;
		sendMail(new Mail("rreyles@virtela.net", body, subject));
	}

	public static boolean sendMail(Mail mail) {

		final Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.host", "mail.virtela.net");
		props.put("mail.smtp.port", "25");
		final Session session = Session.getInstance(props, null);

		try {
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getSender()));
			
			message.setRecipients(Message.RecipientType.TO, mail.getRecipient());
			if (mail.getCc().length() != 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getCc()));
			}

			final Multipart multipart = new MimeMultipart();
			final MimeBodyPart messageBodyPart = new MimeBodyPart();
			final List<String> mailAttachments = mail.getAttchments();
			if (mailAttachments.size() > 0) {
				for (String attachment : mailAttachments) {
					final File file = new File(attachment);
					if (file.exists()) {
						final MimeBodyPart attachMent = new MimeBodyPart();
						final DataSource source = new FileDataSource(attachment);
						attachMent.setDataHandler(new DataHandler(source));
						attachMent.setFileName(file.getName());
						multipart.addBodyPart(attachMent);
					}
				}
			}

			messageBodyPart.setContent(mail.getBody(), "text/html");
			message.setSubject(mail.getSubject());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
}
