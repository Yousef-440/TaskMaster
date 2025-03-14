package com.tasks.Tasks.controller;

import com.tasks.Tasks.dto.TaskEntityDto;
import com.tasks.Tasks.exception.InvalidException;
import com.tasks.Tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("/AllTasks")
    public ResponseEntity<List<TaskEntityDto>> getAllTask(@RequestParam String username
    ,@AuthenticationPrincipal UserDetails userDetails){
        String name = userDetails.getUsername();

        if (!name.equals(username)) {
            throw new InvalidException("You do not have permission to edit this user.");
        }
        return ResponseEntity.ok(service.getUserTasks(username));
    }

    @PostMapping("/create")
    public ResponseEntity<TaskEntityDto> createTask(@RequestParam String username, @RequestBody TaskEntityDto task,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String name = userDetails.getUsername();

        if (!name.equals(username)) {
            throw new InvalidException("You do not have permission to edit this user.");
        }

        return new ResponseEntity<>(service.createTask(username, task) , HttpStatus.CREATED);
    }

    @PutMapping("update/{TaskId}")
    public ResponseEntity<TaskEntityDto> updateTask(@RequestParam String username , @PathVariable int TaskId,
               @RequestBody TaskEntityDto taskEntityDto ,@AuthenticationPrincipal UserDetails userDetails){
        String name = userDetails.getUsername();

        if (!name.equals(username)) {
            throw new InvalidException("You do not have permission to edit this user.");
        }
        return new ResponseEntity<>(service.updateTask(username , TaskId , taskEntityDto) , HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> DeleteTask(@RequestParam String username, @PathVariable int id,
                                             @AuthenticationPrincipal UserDetails userDetails){
        String name = userDetails.getUsername();

        if (!name.equals(username)) {
            throw new InvalidException("You do not have permission to edit this user.");
        }
        service.DeleteTaskById(username , id);
        return ResponseEntity.ok("Task Deleted successfully");
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteTasksByUsername(@RequestParam String username
    , @AuthenticationPrincipal UserDetails userDetails) {
        String name = userDetails.getUsername();

        if (!name.equals(username)) {
            throw new InvalidException("You do not have permission to edit this user.");
        }
        service.deleteAllTasksByUsername(username);
        return ResponseEntity.ok().build();
    }

}

