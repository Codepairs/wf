package org.example.service;



import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.expense.ExpenseCreateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.expense.ExpenseUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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


    public ResponseEntity<List<ExpenseInfoDto>> read_expenses(@PathVariable UUID id, HttpServletRequest request) {
        String accessToken = (String) request.getSession().getAttribute("jwtToken");
        if (accessToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            return restTemplate.exchange("http://localhost:8080/users/expenses/" + id, HttpMethod.GET, entity, new ParameterizedTypeReference<List<ExpenseInfoDto>>(){});
        } else {
            // Handle unauthorized case here
            return ResponseEntity.status(401).build();
        }
    }


}
