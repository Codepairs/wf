package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.expense.ExpenseCreateDto;
import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.expense.ExpenseUpdateDto;
import org.example.service.ExpenseService;
import org.example.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ExpenseController {
/*
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @GetMapping(value = "/expenses")
    public ResponseEntity<List<ExpenseInfoDto>> readAll() {
        List<ExpenseInfoDto> expenses = expenseService.readAll();
        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /*
    @GetMapping(value = "/expenses/expensesById/{id}")
    public ResponseEntity<ExpenseInfoDto> read(@PathVariable(name = "id") UUID id) {
        final ExpenseInfoDto expense = expenseService.read(id);
        return expense != null
                ? new ResponseEntity<>(expense, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/expenses/expensesById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody ExpenseUpdateDto expense) {
        final ExpenseInfoDto updated = expenseService.update(expense, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/expenses/expensesById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final ExpenseInfoDto deleted = expenseService.delete(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/expenses")
    public ResponseEntity<?> create(@Valid @RequestBody ExpenseCreateDto expense) {
        final ExpenseInfoDto created = expenseService.create(expense);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
*/

}