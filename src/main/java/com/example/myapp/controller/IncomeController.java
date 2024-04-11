package com.example.myapp.controller;

import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.EmptyIncomesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.IncomeService;
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
@RequestMapping(value = "/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;


    @PostMapping()
    public ResponseEntity<IncomeFullDto> create(@Valid @RequestBody IncomeUpdateDto income) throws SQLUniqueException {
        IncomeFullDto incomeFullDto = incomeService.create(income);
        return new ResponseEntity<>(incomeFullDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<IncomeFullDto>> readAll() throws EmptyIncomesException {
        List<IncomeFullDto> incomes = incomeService.readAll();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @GetMapping(value = "/incomesById")
    public ResponseEntity<IncomeFullDto> readById(@RequestParam(name = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final IncomeFullDto expense = incomeService.read(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);

    }


    @PutMapping(value = "/incomesById")
    public ResponseEntity<IncomeFullDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                 @Valid @RequestBody IncomeUpdateDto income) throws NotFoundByIdException, SQLUniqueException {
        final IncomeFullDto incomeFullDto = incomeService.update(income, id);
        return new ResponseEntity<>(incomeFullDto, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping(value = "/incomesById")
    public ResponseEntity<Void> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        incomeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
