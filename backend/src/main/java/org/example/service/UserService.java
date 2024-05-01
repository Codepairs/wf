package org.example.service;


import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();


    public List<UserInfoDto> readAll(UserSearchDto user) {
        return restTemplate.getForObject("http://localhost:8080/users", List.class);
    }

    public UUID create(UserCreateDto user) {
        return restTemplate.postForObject("http://localhost:8080/users", user, UUID.class);
    }

    public UserInfoDto readById(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id, UserInfoDto.class);
    }

    public List<IncomeInfoDto> getIncomes(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id + "/incomes", List.class);
    }

    public List<ExpenseInfoDto> getExpenses(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id + "/expenses", List.class);
    }

    public List<ExpenseInfoDto> getExpensesCategory(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/users/" + id + "/expenses", List.class);
    }


}
