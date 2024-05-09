package org.example.service;


import com.example.myapp.dto.expense.ExpenseCreateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.expense.ExpenseUpdateDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.income.IncomeUpdateDto;
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
public class ExpenseService {


    @Autowired
    private RestTemplate restTemplate;

    public ExpenseService (@Autowired @Qualifier("mainServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List readAll() {
        return restTemplate.getForObject("http://localhost:8080/expenses", List.class);
    }


    public ExpenseInfoDto update(ExpenseUpdateDto expense, UUID id) {
        return restTemplate.postForObject("http://localhost:8080/expenses/expensesById/" + id.toString(), expense, ExpenseInfoDto.class);
    }


    public ExpenseInfoDto delete(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/expenses/expensesById/" + id.toString(), ExpenseInfoDto.class);
    }


    public ExpenseInfoDto create(ExpenseCreateDto expense) {
        return restTemplate.postForObject("http://localhost:8080/expenses", expense, ExpenseInfoDto.class);
    }


    public ExpenseInfoDto read(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/expenses/expensesById/" + id.toString(), ExpenseInfoDto.class);
    }


}
