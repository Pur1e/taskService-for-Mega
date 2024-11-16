package kg.com.restapifortaskmanagement.repository;

import kg.com.restapifortaskmanagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
