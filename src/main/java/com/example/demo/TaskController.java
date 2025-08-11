package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/tasks")

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    //Gets all Tasks
    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    } 

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
        return taskService.updateTask(id, updatedTask);
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks(){
        return taskService.getCompletedTask();
    }

    @GetMapping("/sorted")
    public List<Task> getTasksSorted(@RequestParam(defaultValue = "asc")String order){
        return taskService.getTasksSorted(order);
    }
    //Get Completed Task

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
