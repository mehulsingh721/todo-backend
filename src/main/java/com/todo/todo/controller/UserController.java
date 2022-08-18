package com.todo.todo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import com.todo.todo.domain.AppUser;
import com.todo.todo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return (ResponseEntity<List<AppUser>>) ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Map<String, String>> getUsersById(@PathVariable("userId") String userId) {
        return (ResponseEntity<Map<String, String>>) ResponseEntity.ok().body(userService.userById(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser>saveUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/register").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PutMapping("/users/update/{userId}")
    public ResponseEntity<AppUser>updateUser(@RequestBody AppUser newUser, @PathVariable("userId") String userId){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/update").toUriString());
        userService.updateUser(newUser, userId);
        return ResponseEntity.created(uri).body(newUser);
    }

    @DeleteMapping(path = "/users/delete/{userId}")
    public void deleteCampaign(@PathVariable("userId") String userId){
        userService.deleteUser(userId);
    }
}
