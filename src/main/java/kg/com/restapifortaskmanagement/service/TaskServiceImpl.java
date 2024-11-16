package kg.com.restapifortaskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;
import kg.com.restapifortaskmanagement.model.Task;
import kg.com.restapifortaskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository repository;
	private final TaskMapper taskMapper;
	
	@Override
	public List<TaskDto> getAllTasks() {
		return List.of();
	}
	
	@Override
	public TaskDto getTaskById(Long id) {
		log.info("Fetching task with ID: {}", id);
		Task task = findTaskById(id);
		return taskMapper.fromEntityToDto(task);
	}
	
	@Override
	@Transactional
	public Long createTask(TaskRequest task) {
		Task t = taskMapper.fromRequestToEntity(task);
		Long taskId = repository.save(t).getId();
		log.info("Task created with ID: {}", taskId);
		return taskId;
	}
	
	@Override
	@Transactional
	public TaskDto updateTask(Long id, TaskRequest updatedTask) {
		log.info("Updating task with ID: {}", id);
		Task task = findTaskById(id);
		
		log.debug("Existing task before update: {}", task);
		task.setTitle(updatedTask.title());
		task.setDescription(updatedTask.description());
		task.setStatus(updatedTask.status());
		
		Task savedTask = repository.save(task);
		
		log.info("Task with ID {} updated successfully", id);
		log.debug("Updated task: {}", savedTask);
		
		return taskMapper.fromEntityToDto(savedTask);
	}
	
	@Override
	@Transactional
	public void deleteTask(Long id) {
		repository.deleteById(id);
		log.info("Задача с ID {} удалена", id);
	}
	
	private Task findTaskById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> {
					log.error("Task with ID {} not found", id);
					return new EntityNotFoundException("Task with id: " + id + " not found");
				});
	}
}
