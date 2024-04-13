package com.example.myapp.controller;

import com.example.myapp.model.Expense;
import com.example.myapp.service.ExpenseService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping(value = "/expenses")
    public ResponseEntity<?> create(@RequestBody Expense expense){
        expenseService.create(expense);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/expenses")
    public ResponseEntity<List<Expense>> read() {
        final List<Expense> expenses = expenseService.readAll();

        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/expenses/{id}")
    public ResponseEntity<Expense> read(@PathVariable(name = "id") Long id) {
        final Expense expense = expenseService.read(id);

        return expense != null
                ? new ResponseEntity<>(expense, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/expenses/category={id}")
    public ResponseEntity<List<Expense>> readByCategoryId(@PathVariable(name = "id") Long id) {
        final List<Expense> expenses = expenseService.readAllByCategoryId(id);

        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/expenses/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Expense expense) {
        final boolean updated = expenseService.update(expense, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/expenses/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = expenseService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
