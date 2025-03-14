package com.tasks.Tasks.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "servant")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TasksEntity> tasks = new ArrayList<>();

    public UserEntity() {
    }

    public UserEntity(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public List<TasksEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksEntity> tasks) {
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
