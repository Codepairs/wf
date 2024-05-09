package org.example.service;


import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeCreateDto;
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
public class IncomeService {


    @Autowired
    private RestTemplate restTemplate;

    public IncomeService (@Autowired @Qualifier("mainServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List readAll() {
        return restTemplate.getForObject("http://localhost:8080/incomes", List.class);
    }


    public IncomeInfoDto update(IncomeUpdateDto income, UUID id) {
        return restTemplate.postForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), income, IncomeInfoDto.class);
    }


    public IncomeInfoDto delete(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);
    }


    public IncomeInfoDto create(IncomeCreateDto income) {
        return restTemplate.postForObject("http://localhost:8080/incomes", income, IncomeInfoDto.class);
    }


    public IncomeInfoDto read(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/incomes/incomesById/" + id.toString(), IncomeInfoDto.class);
    }


}
