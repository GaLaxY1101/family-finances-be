package com.mindspark.family_finances.repository;

import com.mindspark.family_finances.dto.TaskDtoTiny;
import com.mindspark.family_finances.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Set<Task> findAllByStatus(Task.Status status);
}
