package com.example.myapp.controller;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.exceptions.EmptyCategoriesException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.*;
import com.example.myapp.service.CategoryService;
<<<<<<< HEAD
=======
import com.example.myapp.service.ExpenseService;
import com.example.myapp.service.IncomeService;
import com.example.myapp.service.UserService;
>>>>>>> 7c874bf9201aece73d925cf334f9c183676c67c0
import com.fasterxml.jackson.annotation.JsonView;
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

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;


    @PostMapping()
    public ResponseEntity<CategoryFullDto> create(@Valid @RequestBody CategoryUpdateDto category) throws SQLUniqueException {
        CategoryFullDto dtoCategory = categoryService.create(category);
        return new ResponseEntity<>(dtoCategory, HttpStatus.CREATED);
    }


<<<<<<< HEAD
    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> read() {
        final List<Category> categories = categoryService.readAll();

        return categories != null &&  !categories.isEmpty()
                ? new ResponseEntity<>(categories, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/categories/{id}/incomes")
    public ResponseEntity<List<Income>> read_incomes(@PathVariable(name = "id") Long id) {
        List<Income> incomes = categoryService.getIncomesByUserId(id);
        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/categories/{id}/expenses")
    public ResponseEntity<List<Expense>> read_expenses(@PathVariable(name = "id") Long id) {
        List<Expense> expenses = categoryService.getExpensesByUserId(id);
        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
=======
    @GetMapping()
    public ResponseEntity<List<CategoryFullDto>> read() throws EmptyCategoriesException {
        final List<CategoryFullDto> categories = categoryService.readAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
>>>>>>> 7c874bf9201aece73d925cf334f9c183676c67c0
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
