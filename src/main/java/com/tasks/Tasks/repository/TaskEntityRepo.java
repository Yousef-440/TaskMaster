package com.tasks.Tasks.repository;

import com.tasks.Tasks.model.TasksEntity;
import com.tasks.Tasks.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskEntityRepo extends JpaRepository<TasksEntity , Integer> {
    List<TasksEntity> findByUser(UserEntity user);

    boolean existsByTitleAndUser(String title, UserEntity user);

    void deleteAllByUser(UserEntity user);
}
