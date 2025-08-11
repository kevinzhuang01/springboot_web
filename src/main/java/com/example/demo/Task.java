package com.example.demo;

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Task{
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private int priority;
    private LocalDateTime createdAt = LocalDateTime.now();

}