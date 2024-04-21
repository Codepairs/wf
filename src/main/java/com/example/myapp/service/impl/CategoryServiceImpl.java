package com.example.myapp.service.impl;

import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.category.CategorySearchDto;
import com.example.myapp.dto.category.CategoryUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.service.CategoryService;
import com.example.myapp.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public UUID create(CategoryCreateDto category) throws SQLUniqueException {
        try {
            Category newCategory = mappingUtils.mapToCategory(category);
            categoryRepository.save(newCategory);
            return newCategory.getId();
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<CategoryInfoDto> readAll(CategorySearchDto categorySearchDto) throws EmptyCategoriesException {
        int size = categorySearchDto.getSize();
        int page = categorySearchDto.getPage();

        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        List<CategoryInfoDto> categories = categoryRepository.findAll(request).stream().map(mappingUtils::mapToCategoryInfoDto).toList();
        if (categories.isEmpty()) {
            throw new EmptyCategoriesException();
        }
        return categories;
    }

    @Override
    public CategoryInfoDto read(UUID id) throws NotFoundByIdException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        return mappingUtils.mapToCategoryInfoDto(categoryRepository.getReferenceById(id));
    }


    @Override
    public CategoryInfoDto update(CategoryUpdateDto updateCategory, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        Category category = categoryRepository.getReferenceById(id);
        try {
            category.setName(updateCategory.getName());
            categoryRepository.save(category);
            return mappingUtils.mapToCategoryInfoDto(category);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public UUID delete(UUID id) throws NotFoundByIdException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
        return id;
    }

    public List<IncomeInfoDto> getIncomes(UUID id) throws NotFoundByIdException {
        try {
            return incomeRepository.findAllByuser_id(id)
                    .stream().map(mappingUtils::mapToIncomeInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
    }

    public List<ExpenseInfoDto> getExpenses(UUID id) throws NotFoundByIdException {
        try {
            return expenseRepository.findAllByuser_id(id)
                    .stream().map(mappingUtils::mapToExpenseInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
    }
}


