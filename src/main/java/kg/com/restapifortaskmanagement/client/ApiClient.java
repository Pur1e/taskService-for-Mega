package kg.com.restapifortaskmanagement.client;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ApiClient {
	
	@PostConstruct
	public void fetchDataFromApi() {
		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "https://api.restful-api.dev/objects";
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		
		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Response: {}", response.getBody());
		} else {
			log.error("Error: {}", response.getStatusCode());
		}
	}
}