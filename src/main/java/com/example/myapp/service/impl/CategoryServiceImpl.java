package com.example.myapp.service.impl;

import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.category.CategorySearchDto;
import com.example.myapp.dto.category.CategoryUpdateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.repository.CategoryRepository;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.search.criteria.SearchCriteria;
import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.example.myapp.service.CategoryService;
import com.example.myapp.utils.MappingUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.util.CastUtils.cast;

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

    @Override
    public List<IncomeInfoDto> getIncomes(UUID id) throws NotFoundByIdException {
        try {
            return incomeRepository.findAllByuser_id(id)
                    .stream().map(mappingUtils::mapToIncomeInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
    }

    @Override
    public List<ExpenseInfoDto> getExpenses(UUID id) throws NotFoundByIdException {
        try {
            return expenseRepository.findAllByuser_id(id)
                    .stream().map(mappingUtils::mapToExpenseInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("Category with id " + id + " not found");
        }
    }

    @Override
    public List<CategoryInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        Specification<Category> specification = (entity, query, cb) -> {
            Predicate predicate = cb.conjunction();
            for (SearchCriteria<?> criteria : conditions) {
                OperationType operationType = criteria.getOperation();
                PredicateStrategy<?> strategy = criteria.getStrategy();
                switch (operationType) {
                    case EQUALS ->
                            predicate = cb.and(predicate, strategy.getEqualsPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case RIGHT_LIMIT ->
                            predicate = cb.and(predicate, strategy.getRightLimitPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case LEFT_LIMIT ->
                            predicate = cb.and(predicate, strategy.getLeftLimitPattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                    case LIKE ->
                            predicate = cb.and(predicate, strategy.getLikePattern(entity.get(criteria.getField()), cast(criteria.getValue()), cb));
                }
            }
            return predicate;
        };

        PageRequest request = PageRequest.of(page, size, pageable.getSort());
        Page<Category> categories = categoryRepository.findAll(specification, request);
        return categories.getContent().stream().map(mappingUtils::mapToCategoryInfoDto).toList();
    }

}


