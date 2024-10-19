package com.mindspark.family_finances.services;

import com.mindspark.family_finances.dto.AddTaskRequestDto;
import com.mindspark.family_finances.dto.AddTaskResponseDto;
import com.mindspark.family_finances.dto.TaskDtoTiny;
import com.mindspark.family_finances.exception.task.TaskNotFoundException;
import com.mindspark.family_finances.exception.task.TaskOwnershipViolationException;
import com.mindspark.family_finances.mapper.TaskRegistrationMapper;
import com.mindspark.family_finances.mapper.TaskTinyMapper;
import com.mindspark.family_finances.model.Card;
import com.mindspark.family_finances.model.Payment;
import com.mindspark.family_finances.model.Task;
import com.mindspark.family_finances.model.User;
import com.mindspark.family_finances.repository.TaskRepository;
import com.mindspark.family_finances.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskRegistrationMapper taskMapper;
    private final UserService userService;
    private final TaskTinyMapper taskTinyMapper;
    private final PaymentService paymentService;
    private final CardService cardService;
    private final BankAccountService bankAccountService;
    private final PaymentHistoryService paymentHistoryService;

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> {
            log.info("Task with id {} not found", id);
            throw new TaskNotFoundException("No such task");
        });
    }

    @Transactional
    public void deleteById(Long id) {
        if (!taskRepository.existsById(id)) {
            log.info("Task with id {} not found", id);
            throw new TaskNotFoundException("No such task");
        }
    }

    @Transactional
    public void save(Task task) {
        log.info("{} saved", task);
        taskRepository.save(task);
    }

    public Task getReferenceById(Long id) {
        return taskRepository.getReferenceById(id);
    }

    @Transactional
    public AddTaskResponseDto createTask(AddTaskRequestDto taskRegistrationDto, Authentication authentication) {
        User assignee = userRepository.findById(taskRegistrationDto.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("Assignee not found"));

        User assigner = (User) authentication.getPrincipal();

        Task task = taskMapper.toEntity(taskRegistrationDto);

        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(Task.Status.ACTIVE);
        task.setAssignee(assignee);
        task.setAssigner(assigner);

        taskRepository.save(task);
        return new AddTaskResponseDto(
                assignee.getId(),
                task.getName(),
                task.getDescription(),
                task.getReward(),
                task.getDeadline());
    }

    @Transactional
    public void acceptTask(Long taskId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Task task = findById(taskId);

        if(!authenticatedUser.getId().equals(task.getAssigner().getId())) {
            throw new TaskOwnershipViolationException("Attempt to edit not your task");
        }

        task.setStatus(Task.Status.ACCEPTED);
        sendReward(task);
    }

    @Transactional
    public void sendReward(Task task) {

        User assigner = task.getAssigner();
        User assignee = task.getAssignee();
        Card childCard = cardService.findByUserAndBankAccount(task.getAssignee(), bankAccountService.getFamilyBankAccountByUser(assigner));

        Payment payment = paymentService.createPaymentToChildCard(assigner, childCard, task.getReward());
        paymentService.processPayment(payment);
    }

    @Transactional
    public void rejectTask(Long taskId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Task task = findById(taskId);

        if(!authenticatedUser.getId().equals(task.getAssigner().getId())) {
            throw new TaskOwnershipViolationException("Attempt to edit not your task");
        }

        task.setStatus(Task.Status.REJECTED);
    }

    @Transactional
    public void markAsDone(Long taskId) {
        User authenticatedUser = userService.getAuthenticatedUser();
        Task task = findById(taskId);

        if(!authenticatedUser.getId().equals(task.getAssignee().getId())){
            throw new TaskOwnershipViolationException("Attempt to edit not your task");
        }

        task.setStatus(Task.Status.DONE);
    }

    public Set<TaskDtoTiny> findAllByStatus(Task.Status status) {
        return taskRepository.findAllByStatus(status)
                .stream()
                .map(taskTinyMapper::toDto)
                .collect(Collectors.toSet());
    }
}
