package com.todo.todo.controller;

import java.util.List;

import com.todo.todo.domain.Task;
import com.todo.todo.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;

	@GetMapping
	public ResponseEntity<List<Task>> getTasks(){
		List<Task> tasks = taskService.getTasks();
		return ResponseEntity.ok(tasks);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createTask(@RequestBody Task task){
		taskService.createTask(task);
		return ResponseEntity.ok("Task Added");
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateTask(@RequestBody Task updatedTask, @RequestParam String taskId){
		taskService.updateTask(updatedTask, taskId);
		return ResponseEntity.ok("Task Updated");
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteTask(@RequestParam String taskId){
		taskService.deleteTask(taskId);
		return ResponseEntity.ok("Task Deleted");
	}
}
