package com.example.demo;

import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/tasks")

public class TaskController {
    public List <Task> tasks = new ArrayList<>();

    @GetMapping
    public List<Task> getAllTasks(){
        return tasks;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        task.setId((long) (tasks.size() +1));
        tasks.add(task);
        return task;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        for (Task t: tasks){
            if (t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }




}
