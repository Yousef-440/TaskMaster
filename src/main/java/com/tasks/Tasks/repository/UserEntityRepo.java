package com.tasks.Tasks.repository;

import com.tasks.Tasks.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity , Integer> {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);
}
