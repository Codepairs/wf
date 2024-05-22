package com.example.myapp.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.security.config.AuthenticationConfigConstants;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.security.entrypoint.AuthEntryPointJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;



    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserCreateDto creds = new ObjectMapper()
                .readValue(request.getInputStream(), UserCreateDto.class);
        log.info("Credentials: {}", creds.toString());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getName(),
                        creds.getPassword(),
                        new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));
        response.addHeader(AuthenticationConfigConstants.HEADER_STRING, AuthenticationConfigConstants.TOKEN_PREFIX + token);
        log.info("User: {}", ((User) auth.getPrincipal()).getUsername());
        response.addHeader(AuthenticationConfigConstants.USER_STRING, userRepository.findByName(((User) auth.getPrincipal()).getUsername()).get().getId().toString());

    }
}