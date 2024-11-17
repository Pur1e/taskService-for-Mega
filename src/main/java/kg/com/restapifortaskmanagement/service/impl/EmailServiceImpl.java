package kg.com.restapifortaskmanagement.service.impl;

import kg.com.restapifortaskmanagement.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender emailSender;
	
	@Override
	public void sendTaskCreatedEmail(String to, String taskDetails) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("New Task Created");
		message.setText("A new task has been created:\n\n" + taskDetails);
		
		emailSender.send(message);
	}
}
