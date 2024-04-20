package com.example.myapp.controller;

import com.example.myapp.dto.create.ExpenseCreateDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.search.ExpenseSearchDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.ExpenseService;
import com.example.myapp.utils.MappingUtils;
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

    @Autowired
    private MappingUtils mappingUtils;


    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody ExpenseCreateDto expense) throws SQLUniqueException, NotFoundByIdException {
        UUID id = expenseService.create(expense);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ExpenseInfoDto>> readAll(@Valid @RequestBody ExpenseSearchDto expenseSearchDto) throws EmptyExpenseException {
        List<ExpenseInfoDto> expenses = expenseService.readAll(expenseSearchDto);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping(value = "/expensesById")
    public ResponseEntity<ExpenseInfoDto> readById(@RequestParam(name = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        ExpenseInfoDto expense = expenseService.read(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);

    }


    @PutMapping(value = "/expensesById")
    public ResponseEntity<ExpenseInfoDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                 @Valid @RequestBody ExpenseUpdateDto expense) throws NotFoundByIdException, SQLUniqueException {
        ExpenseInfoDto expenseInfoDto = expenseService.update(expense, id);
        return new ResponseEntity<>(expenseInfoDto, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping(value = "/expensesById")
    public ResponseEntity<UUID> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UUID deletedId = expenseService.delete(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }
}
