package kg.com.restapifortaskmanagement.controller;

import jakarta.validation.Valid;
import kg.com.restapifortaskmanagement.dto.TaskDto;
import kg.com.restapifortaskmanagement.dto.TaskRequest;
import kg.com.restapifortaskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api.restful-api.dev/objects")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
	
	private final TaskService service;
	
	@PostMapping
	public ResponseEntity<Long> createTask(@RequestBody @Valid TaskRequest task) {
		Long createdTask = service.createTask(task);
		return ResponseEntity.ok(createdTask);
	}
	
	@GetMapping
	public ResponseEntity<List<TaskDto>> getAllTasks() {
		List<TaskDto> tasks = service.getAllTasks();
		log.info("Fetched {} tasks", tasks.size());
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
		TaskDto task = service.getTaskById(id);
		return ResponseEntity.ok(task);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest updatedTask) {
		TaskDto task = service.updateTask(id, updatedTask);
		return ResponseEntity.ok(task);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		service.deleteTask(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}