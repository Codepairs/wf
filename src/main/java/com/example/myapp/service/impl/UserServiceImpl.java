package com.example.myapp.service.impl;

import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import com.example.myapp.dto.user.UserUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.EmptyUsersException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.User;
import com.example.myapp.repository.ExpenseRepository;
import com.example.myapp.repository.IncomeRepository;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.search.criteria.SearchCriteria;
import com.example.myapp.search.enums.OperationType;
import com.example.myapp.search.strategy.PredicateStrategy;
import com.example.myapp.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public UUID create(UserCreateDto user) throws SQLUniqueException {
        try {
            User newUser = mappingUtils.mapToUser(user);
            userRepository.save(newUser);
            return newUser.getId();
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public List<UserInfoDto> readAll(UserSearchDto user) throws EmptyUsersException {
        int size = user.getSize();
        int page = user.getPage();

        PageRequest request = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        List<UserInfoDto> users = userRepository.findAll(request).stream().map(mappingUtils::mapToUserInfoDto).toList();
        if (users.isEmpty()) {
            throw new EmptyUsersException("Expenses not found");
        }
        return users;
    }

    @Override
    public UserInfoDto read(UUID id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        return mappingUtils.mapToUserInfoDto(userRepository.getReferenceById(id));
    }


    @Override
    public UserInfoDto update(UserUpdateDto user, UUID id) throws SQLUniqueException, NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        User user1 = userRepository.getReferenceById(id);
        try {
            user1.setName(user.getName());
            user1.setEmail(user.getEmail());
            user1.setId(id);
            return mappingUtils.mapToUserInfoDto(userRepository.save(user1));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public UUID delete(UUID id) throws NotFoundByIdException {
        if (!userRepository.existsById(id)) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
        return id;
    }

    public List<IncomeInfoDto> getIncomes(UUID id) throws NotFoundByIdException {
        try {
            return incomeRepository.findAllByuser_id(id).stream().map(mappingUtils::mapToIncomeInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }

    }

    public List<ExpenseInfoDto> getExpenses(UUID id) throws NotFoundByIdException {
        try {
            return expenseRepository.findAllByuser_id(id).stream().map(mappingUtils::mapToExpenseInfoDto).toList();
        } catch (Exception e) {
            throw new NotFoundByIdException("User with id " + id + " not found");
        }
    }

    public List<UserInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException {
        int size = pageable.getPageSize();
        int page = pageable.getPageNumber();

        Specification<User> specification = (entity, query, cb) -> {
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
        Page<User> users = userRepository.findAll(specification, request);
        return users.getContent().stream().map(mappingUtils::mapToUserInfoDto).toList();
    }

    @Override
    public User read(String name) throws NotFoundByIdException {
        if (!userRepository.existsByName(name)) {
            throw new NotFoundByIdException("User with name " + name + " not found");
        }
        return userRepository.findByName(name).get();
    }
}

