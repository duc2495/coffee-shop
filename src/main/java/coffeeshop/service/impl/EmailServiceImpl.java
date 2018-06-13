package coffeeshop.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import coffeeshop.service.EmailService;

@Component
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	public JavaMailSender javaMailSender;

	@Value("${support.email}")
	String supportEmail;

	@Override
	public void sendEmail(String to, String subject, String content) {
		try {
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
			message.setSubject(subject);
			message.setFrom(supportEmail);
			message.setTo(to);
			message.setText(content, true /* isHtml */);

			javaMailSender.send(message.getMimeMessage());
		} catch (MailException | MessagingException e) {
			logger.error(e.getMessage(), e);
		}

	}

}
