package org.example.service;


import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.category.CategoryUpdateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import com.example.myapp.dto.user.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.List;

@Service
public class CategoryService {


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;


    public List readAll() {
        return restTemplate.getForObject("http://localhost:8080/categories", List.class);
    }

    public CategoryInfoDto read(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/categories/categoryById/" + id.toString(), CategoryInfoDto.class);
    }

    public CategoryInfoDto create(CategoryCreateDto category) {
        return restTemplate.postForObject("http://localhost:8080/categories", category, CategoryInfoDto.class);
    }


    public CategoryInfoDto delete(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/categories/categoryById/" + id.toString(), CategoryInfoDto.class);
    }


    public CategoryInfoDto update(CategoryUpdateDto category, UUID id) {
        return restTemplate.postForObject("http://localhost:8080/categories/categoryById/" + id.toString(), category, CategoryInfoDto.class);
    }

    public List<ExpenseInfoDto> getExpenses(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/category/expensesById/" + id.toString(), List.class);
    }


    public List<IncomeInfoDto> getIncomes(UUID id) {
        return restTemplate.getForObject("http://localhost:8080/category/incomesById/" + id.toString(), List.class);
    }



}
