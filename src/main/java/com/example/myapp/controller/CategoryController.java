package com.example.myapp.controller;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.CategoryService;
import com.example.myapp.service.ExpenseService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @PostMapping()
    public ResponseEntity<CategoryFullDto> create(@Valid @RequestBody CategoryUpdateDto category) throws SQLUniqueException {
        CategoryFullDto dtoCategory = categoryService.create(category);
        return new ResponseEntity<>(dtoCategory, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<CategoryFullDto>> read() throws EmptyCategoriesException {
        final List<CategoryFullDto> categories = categoryService.readAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping(value = "/incomesById")
    public ResponseEntity<List<IncomeFullDto>> getIncomesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<IncomeFullDto> incomes = categoryService.getIncomes(id);
        return new ResponseEntity<>(incomes, HttpStatus.OK);

    }

    @GetMapping(value = "/expensesById")
    public ResponseEntity<List<ExpenseFullDto>> getExpensesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<ExpenseFullDto> expenses = categoryService.getExpenses(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping(value = "categoryById")
    public ResponseEntity<CategoryFullDto> read(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final CategoryFullDto category = categoryService.read(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PutMapping(value = "/categoryById")
    public ResponseEntity<CategoryFullDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id, @Valid @RequestBody CategoryUpdateDto category) throws SQLUniqueException, NotFoundByIdException {
        final CategoryFullDto dtoCategory = categoryService.update(category, id);
        return new ResponseEntity<>(dtoCategory, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/categoryById")
    public ResponseEntity<Void> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
