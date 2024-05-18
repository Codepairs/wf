package com.example.myapp.controller;

import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.income.IncomeCreateDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.income.IncomeSearchDto;
import com.example.myapp.dto.income.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.*;
import com.example.myapp.search.criteria.SearchCriteria;
import com.example.myapp.service.IncomeService;
import com.example.myapp.utils.MappingUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private MappingUtils mappingUtils;


    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody IncomeCreateDto incomeCreateDto) throws SQLUniqueException, NotFoundByIdException {
        UUID id = incomeService.create(incomeCreateDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/pagination")
    public ResponseEntity<List<IncomeInfoDto>> readAll(@Valid @RequestBody IncomeSearchDto incomeSearchDto) throws EmptyIncomesException, EmptyExpenseException {
        List<IncomeInfoDto> incomesInfoDto = incomeService.readAll(incomeSearchDto);
        return new ResponseEntity<>(incomesInfoDto, HttpStatus.OK);
    }

    @GetMapping(value = "/incomesById")
    public ResponseEntity<IncomeInfoDto> readById(@RequestParam(name = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        IncomeInfoDto incomeInfoDto = incomeService.read(id);
        return new ResponseEntity<>(incomeInfoDto, HttpStatus.OK);

    }


    @PutMapping(value = "/incomesById")
    public ResponseEntity<IncomeInfoDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id, @Valid @RequestBody IncomeUpdateDto income) throws NotFoundByIdException, SQLUniqueException {
        IncomeInfoDto incomeInfoDto = incomeService.update(income, id);
        return new ResponseEntity<>(incomeInfoDto, HttpStatus.OK);

    }

    @Transactional
    @DeleteMapping(value = "/incomesById")
    public ResponseEntity<UUID> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UUID deletedId = incomeService.delete(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    @PostMapping("/getByFilter")
    public ResponseEntity<List<IncomeInfoDto>> getByFilter(@Valid @RequestBody List<SearchCriteria<?>>  conditions, Pageable pageable) throws EmptyCategoriesException {
        return new ResponseEntity<>(incomeService.getByFilter(conditions, pageable), HttpStatus.OK);
    }
}