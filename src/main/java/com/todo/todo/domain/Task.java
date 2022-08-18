package com.todo.todo.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Task {
	@Id
	@Column(name = "id", length = 8, unique = true, nullable = false)
	private String id = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	private String task;
	private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", task='" + task + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
