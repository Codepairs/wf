package org.example.service;


import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import com.example.myapp.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;

@Service
public class UserService {
    private RestTemplate restTemplate;

    public UserService (@Autowired @Qualifier("mainServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
