package com.example.myapp.security.service;

import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.model.User;
import com.example.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User apiUser = userService.read(username);
            if (apiUser == null) {
                throw new UsernameNotFoundException("User not found");
            }
            log.info("Username: {} Password: {}", apiUser.getName(), apiUser.getPassword());
            return new org.springframework.security.core.userdetails.User(apiUser.getName(), apiUser.getPassword(), Collections.emptyList());

        } catch (NotFoundByIdException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

    }
}