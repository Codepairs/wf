package org.example.controller;


import jakarta.validation.Valid;
import org.example.dto.income.IncomeCreateDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.income.IncomeUpdateDto;
import org.example.service.IncomeService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class IncomeController {
    /*
    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }


    @GetMapping(value = "/incomes")
    public ResponseEntity<List<IncomeInfoDto>> readAll() {
        List<IncomeInfoDto> incomes = incomeService.readAll();
        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<IncomeInfoDto> read(@PathVariable(name = "id") UUID id) {
        final IncomeInfoDto income = incomeService.read(id);
        return income != null
                ? new ResponseEntity<>(income, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody IncomeUpdateDto income) {
        final IncomeInfoDto updated = incomeService.update(income, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/incomes/incomesById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final IncomeInfoDto deleted = incomeService.delete(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/incomes")
    public ResponseEntity<?> create(@Valid @RequestBody IncomeCreateDto income) {
        final IncomeInfoDto created = incomeService.create(income);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    */


}
