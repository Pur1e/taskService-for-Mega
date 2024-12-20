package kg.com.restapifortaskmanagement.dto;

import kg.com.restapifortaskmanagement.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto implements Serializable {
	private Long id;
	private String title, description;
	private Status status;
	private LocalDateTime createdAt, updatedAt;
}
