package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task createTask(Task task) {
        task.setId(idCounter.getAndIncrement());
        if (task.getCreatedAt() == null) {
            task.setCreatedAt(LocalDateTime.now());
        }
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        existingTask.setPriority(updatedTask.getPriority());
        // Do not update createdAt
        return existingTask;
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        tasks.remove(task);
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSorted(String order) {
        Comparator<Task> comparator = Comparator.comparingInt(Task::getPriority);
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return tasks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
