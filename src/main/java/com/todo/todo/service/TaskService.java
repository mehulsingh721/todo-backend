package com.todo.todo.service;

import java.util.List;

import com.todo.todo.domain.Task;
import com.todo.todo.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private final TaskRepository taskRepository;
	public TaskService(TaskRepository taskRepository){
		this.taskRepository = taskRepository;
	}

	public List<Task> getTasks(){
		return taskRepository.findAll();
	}

	public void createTask(Task task){
		taskRepository.save(task);
	}

	public void updateTask(Task updatedTask, String taskId){
		Task task = taskRepository.findById(taskId).get();

		task.setTask(updatedTask.getTask());
		task.setStatus(updatedTask.getStatus());

		taskRepository.save(task);
	}

	public void deleteTask(String taskId){
		taskRepository.deleteById(taskId);
	}
}
