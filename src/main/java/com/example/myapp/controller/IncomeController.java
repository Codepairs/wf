package com.example.myapp.controller;

import com.example.myapp.model.Income;
import com.example.myapp.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping(value = "/incomes")
    public ResponseEntity<?> create(@RequestBody Income income){
        incomeService.create(income);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "/incomes")
    public ResponseEntity<List<Income>> read() {
        final List<Income> incomes = incomeService.readAll();

        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/incomes/{id}")
    public ResponseEntity<Income> read(@PathVariable(name = "id") Long id) {
        final Income income = incomeService.read(id);

        return income != null
                ? new ResponseEntity<>(income, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/incomes/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Income income) {
        final boolean updated = incomeService.update(income, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/incomes/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = incomeService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
