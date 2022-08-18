package com.todo.todo.repository;

import org.springframework.stereotype.Repository;

import com.todo.todo.domain.Task;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskRepository extends JpaRepository<Task, String>{
}
