package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController
@RequestMapping("/tasks")

public class TaskController {
    public List <Task> tasks = new ArrayList<>();
    // Get Task
    @GetMapping
    public List<Task> getAllTasks(){
        return tasks;
    }
   // Create Task
    @PostMapping
    public Task createTask(@RequestBody Task task){
        task.setId((long) (tasks.size() +1));
        tasks.add(task);
        return task;
    }

    // Updating task
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask){
        for (int i= 0; i < tasks.size();i++){
            if (tasks.get(i).getId().equals(id)){
                updatedTask.setId(id);
                tasks.set(i,updatedTask);
                return updatedTask;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task not Found!");
    }

    //Get Completed Task

    @GetMapping("/completed")
    public List<Task> getCompletedTask(){
        return tasks.stream().filter(Task::getCompleted).toList();
    }
    
    //Gets Sorted Tasks
    @GetMapping("/sorted")
    public List<Task> getTasksSorted(@RequestParam(defaultValue= "asc") String order){
        if(!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order parameter, Use 'asc' or 'desc' .");
        }

        List <Task> sortedTasks = new ArrayList<>(tasks);
        if (order.equalsIgnoreCase("desc")){
            Collections.sort(sortedTasks,(a,b)-> b.getTitle().compareTo(a.getTitle())); 
        }
        else{
            Collections.sort(sortedTasks,(a,b) -> a.getTitle().compareTo(b.getTitle()));
        }

        return sortedTasks;
    }
    //
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        for (Task t: tasks){
            if (t.getId().equals(id)){
                return t;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task Not Found");
    }
    
    @PutMapping("/{id}/description")
    public Task getDescriptionById(@PathVariable Long id,@RequestBody Map<String,String> updatedMap){
        String newDescription = updatedMap.get("description");
        for( Task t: tasks){
            if (t.getId().equals(id)){
                if (newDescription != null){
                    t.setDescription(newDescription);
                }
            }
            return t;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Task Not Found");
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        boolean removed = tasks.removeIf(task->task.getId().equals(id));

        if (!removed){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found!");
        }
        return "Task Deleted Successfully";
    }


}
