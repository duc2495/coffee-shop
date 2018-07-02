package coffeeshop.service;

public interface EmailService {
	public void sendEmail(String toEmail, String subject, String content);
}
