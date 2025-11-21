package com.example.spring_introduce.service;

import com.example.spring_introduce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(userFromDb -> new User(
                        userFromDb.getEmail(),
                        userFromDb.getPassword(),
                        // Spring Security wymaga prefiksu "ROLE_" dla ról
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userFromDb.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik o emailu " + email + " nie istnieje"));
    }
}