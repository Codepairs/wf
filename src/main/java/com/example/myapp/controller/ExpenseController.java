package com.example.myapp.controller;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.ExpenseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequestMapping(value = "/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @PostMapping()
    public ResponseEntity<ExpenseFullDto> create(@Valid @RequestBody ExpenseUpdateDto expense) throws SQLUniqueException {
        ExpenseFullDto expenseFullDto = expenseService.create(expense);
        return new ResponseEntity<>(expenseFullDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseFullDto>> readAll() throws EmptyExpenseException {
        List<ExpenseFullDto> expenses = expenseService.readAll();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping(value = "/expensesById")
    public ResponseEntity<ExpenseFullDto> readById(@RequestParam(name = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final ExpenseFullDto expense = expenseService.read(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);

    }


    @PutMapping(value = "/expensesById")
    public ResponseEntity<ExpenseFullDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                  @Valid @RequestBody ExpenseUpdateDto expense) throws NotFoundByIdException, SQLUniqueException {
        final ExpenseFullDto expenseFullDto = expenseService.update(expense, id);
        return new ResponseEntity<>(expenseFullDto, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping(value = "/expensesById")
    public ResponseEntity<Void> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        expenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
