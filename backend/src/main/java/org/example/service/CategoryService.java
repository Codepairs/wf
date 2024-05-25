package org.example.service;


import org.example.dto.category.CategoryCreateDto;
import org.example.dto.category.CategoryInfoDto;
import org.example.dto.category.CategoryUpdateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.List;

@Service
public class CategoryService {
    /*
    @Autowired
    private RestTemplate restTemplate;

    public CategoryService (@Autowired @Qualifier("mainServiceRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

    */

}
