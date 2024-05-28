package com.example.myapp.controller;

import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.category.CategorySearchDto;
import com.example.myapp.dto.category.CategoryUpdateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.search.criteria.SearchCriteria;
import com.example.myapp.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MappingUtils categoryMappingUtils;


    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody CategoryCreateDto category) throws SQLUniqueException {
        UUID id = categoryService.create(category);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PostMapping("/pagination")
    public ResponseEntity<List<CategoryInfoDto>> getCategoryAll(@Valid @RequestBody CategorySearchDto categorySearchDto) throws EmptyCategoriesException {
        List<CategoryInfoDto> categories = categoryService.readAll(categorySearchDto);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    @GetMapping(value = "/incomesById")
    public ResponseEntity<List<IncomeInfoDto>> getIncomesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<IncomeInfoDto> incomes = categoryService.getIncomes(id);
        return new ResponseEntity<>(incomes, HttpStatus.OK);

    }

    @GetMapping(value = "/expensesById")
    public ResponseEntity<List<ExpenseInfoDto>> getExpensesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<ExpenseInfoDto> expenses = categoryService.getExpenses(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping(value = "/categoryById")
    public ResponseEntity<CategoryInfoDto> getCategoryAll(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        CategoryInfoDto category = categoryService.read(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PutMapping(value = "/categoryById")
    public ResponseEntity<CategoryInfoDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id, @Valid @RequestBody CategoryUpdateDto category) throws SQLUniqueException, NotFoundByIdException {
        CategoryInfoDto dtoCategory = categoryService.update(category, id);
        return new ResponseEntity<>(dtoCategory, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/categoryById")
    public ResponseEntity<UUID> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UUID deletedId = categoryService.delete(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    @PostMapping("/getByFilter")
    public ResponseEntity<List<CategoryInfoDto>> getByFilter(@Valid @RequestBody List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException {
        return new ResponseEntity<>(categoryService.getByFilter(conditions, pageable), HttpStatus.OK);
    }

    @GetMapping("/categoryByName")
    public ResponseEntity<CategoryInfoDto> getCategoryByName(@RequestParam(value = "name") @Valid @PathVariable String name) throws SQLUniqueException {
        CategoryInfoDto category = categoryService.getCategoryByName(name);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
