package org.example.service;


import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserUpdateDto;
import jakarta.servlet.http.HttpSession;
import org.example.dto.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;

@Service
public class UserService {
    private RestTemplate restTemplate;
    private HttpSession session;



    @Autowired
    public UserService(@Autowired @Qualifier("mainServiceRestTemplate") RestTemplate restTemplate, HttpSession session) {
        this.restTemplate = restTemplate;
        this.session = session;
    }


    public ResponseEntity<Map> login(UserLoginDto user) {
        String login = user.getName();
        String password = user.getPassword();
        String jwtToken = "";
        UUID userId;

        Map<String, Object> params = new HashMap<>();
        params.put("name", login);
        params.put("password", password);
        ResponseEntity<Map> result = restTemplate.postForEntity("http://localhost:8080/users/login", params, Map.class);
        HttpHeaders headers = result.getHeaders();
        if (headers.get("Authorization") != null && headers.get("userId") != null) {
            jwtToken = String.valueOf(headers.get("Authorization"));
            userId = UUID.fromString(String.valueOf(headers.get("userId")));
            session.setAttribute("jwtToken", jwtToken);
            session.setAttribute("userId", userId);
        }
        return result;
    }


    public List readAll() {
        return restTemplate.getForObject("http://localhost:8080/users", List.class);
    }


    public UserInfoDto update(UserUpdateDto user, UUID id) {
        return restTemplate.postForObject("http://localhost:8080/users/" + id.toString(), user, UserInfoDto.class);
    }


    public UserInfoDto delete(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString(), UserInfoDto.class);
    }


    public UserInfoDto create(UserCreateDto user) {
        return restTemplate.postForObject("http://localhost:8080/users", user, UserInfoDto.class);
    }


    public UserInfoDto read(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString(), UserInfoDto.class);
    }

    public UserInfoDto readById(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString(), UserInfoDto.class);
    }

    public List<IncomeInfoDto> getIncomes(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString() + "/incomes", List.class);
    }

    public List<ExpenseInfoDto> getExpenses(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString() + "/expenses", List.class);
    }

    public List<ExpenseInfoDto> getExpensesCategory(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id.toString() + "/expenses", List.class);
    }




}
