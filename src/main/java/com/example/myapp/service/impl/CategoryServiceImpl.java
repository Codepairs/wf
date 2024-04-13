package com.example.myapp.service.impl;
import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.exceptions.EmptyCategoriesException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
<<<<<<< HEAD
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
=======
>>>>>>> 7c874bf9201aece73d925cf334f9c183676c67c0
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.service.CategoryService;
import com.example.myapp.utils.ExpenseMappingUtils;
import com.example.myapp.utils.IncomeMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.myapp.utils.CategoryMappingUtils;

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
    private CategoryMappingUtils categoryMappingUtils;

    @Autowired
    private IncomeMappingUtils incomeMappingUtils;

    @Autowired
    private ExpenseMappingUtils expenseMappingUtils;

    @Override
    public CategoryFullDto create(CategoryUpdateDto category) throws SQLUniqueException {
        try {
            Category newCategory = categoryMappingUtils.categoryUpdateToCategory(category);
            return categoryMappingUtils.categoryToCategoryFull(categoryRepository.save(newCategory));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<CategoryFullDto>  readAll() throws EmptyCategoriesException {
        List<CategoryFullDto> categories = categoryRepository.findAll()
                .stream().map(categoryMappingUtils::categoryToCategoryFull).toList();
        if (categories.isEmpty()) {
           throw new EmptyCategoriesException();
        }
        return categories;
    }

    @Override
    public CategoryFullDto read(UUID id) throws NotFoundByIdException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        return categoryMappingUtils.categoryToCategoryFull(categoryRepository.getReferenceById(id));
    }


    @Override
    public CategoryFullDto update(CategoryUpdateDto updateCategory, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        Category category = categoryRepository.getReferenceById(id);
        try {
            category.setName(updateCategory.getName());
            category.setExpenses(updateCategory.getExpenses());
            category.setIncomes(updateCategory.getIncomes());
            categoryRepository.save(category);
            return categoryMappingUtils.categoryToCategoryFull(category);
        }
        catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID id) throws NotFoundByIdException {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }

<<<<<<< HEAD
    public List<Income> getIncomesByUserId(Long id) {
        return incomeRepository.findAllByuser_id(id);
    }

    public List<Expense> getExpensesByUserId(Long id) {
        return expenseRepository.findAllByuser_id(id);
=======
    public List<IncomeFullDto> getIncomes(UUID id) throws NotFoundByIdException {
        try {
            return incomeRepository.findAllByuser_id(id)
                    .stream().map(incomeMappingUtils::incomeToIncomeFull).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
    }

    public List<ExpenseFullDto> getExpenses(UUID id) throws NotFoundByIdException {
        try {
            return expenseRepository.findAllByuser_id(id)
                    .stream().map(expenseMappingUtils::expenseToExpenseFull).toList();
        }
        catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
>>>>>>> 7c874bf9201aece73d925cf334f9c183676c67c0
    }

    public List<Expense> getExpensesByCategoryId(Long id) {
        return expenseRepository.findAllBycategory_id(id);
    }

    public List<Income> getIncomesByCategoryId(Long id) {
        return incomeRepository.findAllBycategory_id(id);
    }

}


