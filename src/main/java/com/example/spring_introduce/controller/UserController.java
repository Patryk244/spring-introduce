package com.example.spring_introduce.controller;

import com.example.spring_introduce.domain.Roles;
import com.example.spring_introduce.domain.User;
import com.example.spring_introduce.domain.dto.UserDto;
import com.example.spring_introduce.service.UserDbService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserDbService userDbService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userDbService.findAllUser();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        return userDbService.findByEmail(email)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody UserDto userDto) {
        User user = new User(
                null,
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword(),
                Roles.ADMIN
        );
        User savedUser = userDbService.saveUser(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }
}