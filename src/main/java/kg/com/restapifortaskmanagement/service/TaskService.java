package kg.com.restapifortaskmanagement.service;

import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;

import java.util.List;

public interface TaskService {
	
	Long createTask(TaskRequest task);
	
	List<TaskDto> getAllTasks();
	
	TaskDto getTaskById(Long id);
	
	TaskDto updateTask(Long id, TaskRequest updatedTask);
	
	void deleteTask(Long id);
	
}
