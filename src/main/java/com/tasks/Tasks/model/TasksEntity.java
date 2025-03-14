package com.tasks.Tasks.model;

import com.tasks.Tasks.enumeration.TaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class TasksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public TasksEntity() {
    }

    public TasksEntity(int id, String title, String description, TaskStatus status, UserEntity user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
