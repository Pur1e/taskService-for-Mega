package kg.com.restapifortaskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;
import kg.com.restapifortaskmanagement.model.Task;
import kg.com.restapifortaskmanagement.repository.TaskRepository;
import kg.com.restapifortaskmanagement.service.EmailService;
import kg.com.restapifortaskmanagement.service.TaskMapper;
import kg.com.restapifortaskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
	
	private final TaskRepository repository;
	private final TaskMapper taskMapper;
	private final EmailService emailService;
	
	@Override
	@Cacheable(value = "tasks", key = "'all_tasks'")
	public List<TaskDto> getAllTasks() {
		log.info("Fetching all tasks from database");
		List<Task> tasks = repository.findAll();
		return tasks.stream()
				.map(taskMapper :: fromEntityToDto)
				.toList();
	}
	
	@Override
	public TaskDto getTaskById(Long id) {
		log.info("Fetching task with ID: {}", id);
		Task task = findTaskById(id);
		return taskMapper.fromEntityToDto(task);
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "tasks", key = "'all_tasks'")
	public Long createTask(TaskRequest task) {
		Task t = taskMapper.fromRequestToEntity(task);
		Long taskId = repository.save(t).getId();
		log.info("Task created with ID: {}", taskId);
		
		String taskDetails = String.format("ID: %d\nTitle: %s\nDescription: %s",
				taskId, task.title(), task.description());
		emailService.sendTaskCreatedEmail(task.email(), taskDetails);
		log.info("Email with sent to {}", task.email());
		
		return taskId;
	}
	
	@Override
	@Transactional
	@CacheEvict(value = "tasks", key = "'all_tasks'")
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
	@CacheEvict(value = "tasks", key = "'all_tasks'")
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
