package com.mindspark.family_finances.controllers;

import com.mindspark.family_finances.dto.AddTaskResponseDto;
import com.mindspark.family_finances.dto.TaskDtoTiny;
import com.mindspark.family_finances.dto.AddTaskRequestDto;
import com.mindspark.family_finances.model.Task;
import com.mindspark.family_finances.services.PaymentService;
import com.mindspark.family_finances.services.TaskService;
import com.mindspark.family_finances.services.UserService;
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
public class TasksController {

    private final TaskService taskService;
    private final UserService userService;
    private final PaymentService paymentService;


    @PostMapping("/create-task")
    @ResponseStatus(HttpStatus.CREATED)
    public AddTaskResponseDto createTask(@Valid @RequestBody AddTaskRequestDto taskRegistrationDto,
                                         Authentication authentication
    ) {
        return taskService.createTask(taskRegistrationDto, authentication);
    }

    @PatchMapping("/accept-task/{task_id}")
    public ResponseEntity<String> acceptTask(@PathVariable(name = "task_id") Long taskId
    ) {
        taskService.acceptTask(taskId);
        return ResponseEntity.ok("Task accepted successfully");
    }

    @PatchMapping("/reject-task/{task_id}")
    public ResponseEntity<String> rejectTask(@PathVariable(name = "task_id") Long taskId) {

        taskService.rejectTask(taskId);
        return ResponseEntity.ok("Task reject successfully");
    }

    @PatchMapping("/mark-as-done/{task_id}")
    public ResponseEntity<String> markTaskAsDone(@PathVariable(name = "task_id") Long taskId) {

        taskService.markAsDone(taskId);
        return ResponseEntity.ok("Task marked as done successfully");
    }

    @GetMapping("/{status}")
    public ResponseEntity<Set<TaskDtoTiny>> getTasksByStatus(@PathVariable Task.Status status) {

        Set<TaskDtoTiny> tasks = taskService.findAllByStatus(status);
        return ResponseEntity.ok(tasks);
    }
}
