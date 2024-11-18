package kg.com.restapifortaskmanagement;

import jakarta.persistence.EntityNotFoundException;
import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;
import kg.com.restapifortaskmanagement.model.Status;
import kg.com.restapifortaskmanagement.model.Task;
import kg.com.restapifortaskmanagement.repository.TaskRepository;
import kg.com.restapifortaskmanagement.service.EmailService;
import kg.com.restapifortaskmanagement.service.impl.TaskServiceImpl;
import kg.com.restapifortaskmanagement.service.mappers.TaskMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
	
	@Mock
	private TaskRepository repository;
	
	@Mock
	private TaskMapper taskMapper;
	
	@Mock
	private EmailService emailService;
	
	@InjectMocks
	private TaskServiceImpl taskService;
	
	private Task task;
	private TaskDto taskDto;
	private TaskRequest taskRequest;
	
	@BeforeEach
	void setUp() {
		LocalDateTime now = LocalDateTime.now();
		task = new Task(1L, "Sample Task", "Description", Status.NEW, now, now);
		taskDto = new TaskDto(1L, "Sample Task", "Description", Status.NEW, now, now);
		taskRequest = new TaskRequest("Sample Task", "Description", Status.NEW, "user@example.com");
	}
	
	@Test
	void createTask_ShouldReturnTaskId() {
		when(taskMapper.fromRequestToEntity(taskRequest)).thenReturn(task);
		when(repository.save(task)).thenReturn(task);
		
		Long taskId = taskService.createTask(taskRequest);
		
		assertNotNull(taskId);
		assertEquals(1L, taskId);
		verify(repository, times(1)).save(task);
		verify(emailService, times(1)).sendTaskCreatedEmail(eq("user@example.com"), anyString());
	}
	
	@Test
	void getTaskById_ShouldReturnTaskDto() {
		when(repository.findById(1L)).thenReturn(Optional.of(task));
		when(taskMapper.fromEntityToDto(task)).thenReturn(taskDto);
		
		TaskDto result = taskService.getTaskById(1L);
		
		assertNotNull(result);
		assertEquals(taskDto, result);
		verify(repository, times(1)).findById(1L);
	}
	
	@Test
	void updateTask_ShouldReturnUpdatedTaskDto() {
		TaskRequest updatedRequest = new TaskRequest("Updated Task", "Updated description", Status.COMPLETED, "user@example.com");
		Task updatedTask = new Task(1L, "Updated Task", "Updated description", Status.COMPLETED, task.getCreatedAt(), LocalDateTime.now());
		TaskDto updatedDto = new TaskDto(1L, "Updated Task", "Updated description", Status.COMPLETED, task.getCreatedAt(), LocalDateTime.now());
		
		when(repository.findById(1L)).thenReturn(Optional.of(task));
		when(repository.save(task)).thenReturn(updatedTask);
		when(taskMapper.fromEntityToDto(updatedTask)).thenReturn(updatedDto);
		
		TaskDto result = taskService.updateTask(1L, updatedRequest);
		
		assertNotNull(result);
		assertEquals(updatedDto, result);
		verify(repository, times(1)).save(task);
	}
	
	@Test
	void getTaskById_ShouldThrowEntityNotFoundException_WhenTaskDoesNotExist() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> taskService.getTaskById(1L));
		
		assertEquals("Task with id: 1 not found", exception.getMessage());
		verify(repository, times(1)).findById(1L);
	}
	
	@Test
	void updateTask_ShouldThrowEntityNotFoundException_WhenTaskDoesNotExist() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> taskService.updateTask(1L, taskRequest));
		
		assertEquals("Task with id: 1 not found", exception.getMessage());
		verify(repository, times(1)).findById(1L);
	}
	
}
	

