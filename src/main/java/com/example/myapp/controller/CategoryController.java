package com.example.myapp.controller;

import com.example.myapp.dto.create.CategoryCreateDto;
import com.example.myapp.dto.info.CategoryInfoDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.info.IncomeInfoDto;
import com.example.myapp.dto.search.CategorySearchDto;
import com.example.myapp.dto.service.CategoryDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MappingUtils categoryMappingUtils;


    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody CategoryCreateDto category) throws SQLUniqueException {
        CategoryDto dtoCategory = categoryMappingUtils.mapToCategory(category);
        UUID id = categoryService.create(dtoCategory);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @GetMapping()
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
}