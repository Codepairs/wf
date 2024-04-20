package com.example.myapp.controller;

import com.example.myapp.dto.create.IncomeCreateDto;
import com.example.myapp.dto.info.IncomeInfoDto;
import com.example.myapp.dto.search.IncomeSearchDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.EmptyIncomesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.IncomeService;
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

    @GetMapping()
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
}