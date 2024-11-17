package kg.com.restapifortaskmanagement.service;

public interface EmailService {
	
	void sendTaskCreatedEmail(String to, String taskDetails);
	
}
