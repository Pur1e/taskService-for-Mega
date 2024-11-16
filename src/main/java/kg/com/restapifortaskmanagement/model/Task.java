package kg.com.restapifortaskmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TASKS")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Size(max = 50)
	@NotNull
	@Column(name = "TITLE", nullable = false, length = 50)
	private String title;
	
	@Column(name = "DESCRIPTION", columnDefinition = "CHARACTER LARGE OBJECT not null")
	private Object description;
	
	@NotNull
	@Column(name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull
	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;
	
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}