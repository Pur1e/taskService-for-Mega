package kg.com.restapifortaskmanagement.service.mappers;

import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;
import kg.com.restapifortaskmanagement.model.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskMapper {
	
	public Task fromRequestToEntity(TaskRequest task) {
		return Task.builder()
				.title(task.title())
				.description(task.description())
				.status(task.status())
				.build();
	}
	
	public TaskDto fromEntityToDto(Task task) {
		return TaskDto.builder()
				.id(task.getId())
				.title(task.getTitle())
				.description(task.getDescription())
				.status(task.getStatus())
				.createdAt(task.getCreatedAt())
				.updatedAt(task.getUpdatedAt())
				.build();
	}
}
