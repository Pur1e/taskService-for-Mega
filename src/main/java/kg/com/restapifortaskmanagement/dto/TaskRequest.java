package kg.com.restapifortaskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.com.restapifortaskmanagement.model.Status;

public record TaskRequest(
		@NotNull(message = "Title cannot be null")
		@Size(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
		String title,
		
		@NotNull
		@NotBlank
		String description,
		
		@NotNull(message = "Status cannot be null")
		Status status
) {
}
