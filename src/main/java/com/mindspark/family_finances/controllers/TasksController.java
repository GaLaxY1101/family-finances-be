package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.dto.task.AddTaskResponseDto;
import com.mindspark.family_finances.dto.task.TaskResponseDto;
import com.mindspark.family_finances.dto.task.AddTaskRequestDto;
import com.mindspark.family_finances.model.Task;
import com.mindspark.family_finances.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
@Tag(name = "Task controller",
        description = "Here we have endpoints for task management")
public class TasksController {
    private final TaskService taskService;

    @PostMapping("/create-task")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create task",
            description = "Create task endpoint")
    public AddTaskResponseDto createTask(@Valid @RequestBody AddTaskRequestDto taskRegistrationDto,
                                         Authentication authentication
    ) {
        return taskService.createTask(taskRegistrationDto, authentication);
    }

    @PatchMapping("/accept-task/{task_id}")
    @Operation(summary = "Accept task",
            description = "Endpoint for parents. Parent can accept competed task" +
                    "Task status will be changed to accepted")
    public ResponseEntity<String> acceptTask(@PathVariable(name = "task_id") Long taskId
    ) {
        taskService.acceptTask(taskId);
        return ResponseEntity.ok("Task accepted successfully");
    }

    @PatchMapping("/reject-task/{task_id}")
    @Operation(summary = "Accept task",
            description = "Endpoint for parents. Parent can reject competed task." +
                    "Task status will be changed to rejected")
    public ResponseEntity<String> rejectTask(@PathVariable(name = "task_id") Long taskId) {

        taskService.rejectTask(taskId);
        return ResponseEntity.ok("Task reject successfully");
    }

    @PatchMapping("/mark-as-done/{task_id}")
    @Operation(summary = "Mark task as done",
            description = "Endpoint for child. Child can mark task as competed." +
                    "Task status will be changed to completed")
    public ResponseEntity<String> markTaskAsDone(@PathVariable(name = "task_id") Long taskId) {

        taskService.markAsDone(taskId);
        return ResponseEntity.ok("Task marked as done successfully");
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all task",
            description = "Get all task by status")
    public ResponseEntity<Set<TaskResponseDto>> getTasksByStatus(@PathVariable Task.Status status) {

        Set<TaskResponseDto> tasks = taskService.findAllByStatus(status);
        return ResponseEntity.ok(tasks);
    }
}
