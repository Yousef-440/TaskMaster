package com.tasks.Tasks.service;

import com.tasks.Tasks.dto.TaskEntityDto;
import com.tasks.Tasks.exception.TaskNotFoundException;
import com.tasks.Tasks.exception.TasksFoundException;
import com.tasks.Tasks.exception.UserNotFoundException;
import com.tasks.Tasks.model.TasksEntity;
import com.tasks.Tasks.model.UserEntity;
import com.tasks.Tasks.repository.TaskEntityRepo;
import com.tasks.Tasks.repository.UserEntityRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class TaskService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Autowired
    private TaskEntityRepo taskEntityRepo;


    public List<TaskEntityDto> getUserTasks(String username) {
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Sorry, User not Found"));

        List<TasksEntity> tasks = taskEntityRepo.findByUser(user);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks for this user.");
        }
        return tasks.stream().map(p->mapToDto(p)).collect(Collectors.toList());
    }


    @Transactional
    public TaskEntityDto createTask(String username, TaskEntityDto dto) {
        TasksEntity entity = mapToEntity(dto);
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Sorry, User not found"));

        if (!taskEntityRepo.existsByTitleAndUser(entity.getTitle(), user)) {

            entity.setUser(user);
            entity = taskEntityRepo.save(entity);
            return mapToDto(entity);

        } else {
            throw new TasksFoundException("Data Duplicate in this user");
        }
    }


    @Transactional
    public TaskEntityDto updateTask(String username,int id,TaskEntityDto taskEntityDto){
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Sorry, User not found"));
        TasksEntity tasks = taskEntityRepo.findById(id)
                .orElseThrow(()->new TaskNotFoundException("The tasks you want to modify do not exist."));

        if(tasks.getUser().getId() != user.getId()){
            throw new TaskNotFoundException("This Task does not belong to a User");
        }

        if (!taskEntityRepo.existsByTitleAndUser(taskEntityDto.getTitle(), user)) {

            tasks.setTitle(taskEntityDto.getTitle());
            tasks.setDescription(taskEntityDto.getDescription());
            tasks.setStatus(taskEntityDto.getStatus());
            TasksEntity tasksUpdated = taskEntityRepo.save(tasks);

            return mapToDto(tasksUpdated);

        } else {
            throw new TasksFoundException("Data Duplicate in this user");
        }
    }


    @Transactional
    public void DeleteTaskById(String username, int id){
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Sorry, User not found"));
        TasksEntity tasks = taskEntityRepo.findById(id)
                .orElseThrow(()->new TaskNotFoundException("The tasks you want to modify do not exist."));

        if(tasks.getUser().getId() != user.getId()){
            throw new TaskNotFoundException("This Task does not belong to a User");
        }
        taskEntityRepo.deleteById(id);
    }


    @Transactional
    public void deleteAllTasksByUsername(String username) {
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        List<TasksEntity> tasks = taskEntityRepo.findByUser(user);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks to delete.");
        }

        taskEntityRepo.deleteAllByUser(user);
    }


    private TaskEntityDto mapToDto(TasksEntity tasksEntity){
        TaskEntityDto taskEntityDto = new TaskEntityDto();
        taskEntityDto.setTitle(tasksEntity.getTitle());
        taskEntityDto.setDescription(tasksEntity.getDescription());
        taskEntityDto.setStatus(tasksEntity.getStatus());

        return taskEntityDto;
    }

    private TasksEntity mapToEntity(TaskEntityDto Dto){
        TasksEntity tasks = new TasksEntity();
        tasks.setTitle(Dto.getTitle());
        tasks.setDescription(Dto.getDescription());
        tasks.setStatus(Dto.getStatus());

        return tasks;
    }
}
