package com.example.spring_introduce.service;

import com.example.spring_introduce.domain.User;
import com.example.spring_introduce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDbService {

    private final UserRepository userRepository;

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}