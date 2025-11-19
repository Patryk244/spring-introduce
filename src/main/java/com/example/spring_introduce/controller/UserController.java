package com.example.spring_introduce.controller;

import com.example.spring_introduce.domain.User;
import com.example.spring_introduce.service.UserDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserDbService userDbService;

    @GetMapping
    public List<User> findAll() {
        return userDbService.findAllUser();
    }
}