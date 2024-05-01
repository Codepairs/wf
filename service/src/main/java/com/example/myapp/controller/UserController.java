package com.example.myapp.controller;

import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import com.example.myapp.dto.user.UserUpdateDto;
import com.example.myapp.handler.exceptions.EmptyUsersException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<UUID> create(@Valid @RequestBody UserCreateDto user) throws SQLUniqueException {
        UUID id = userService.create(user);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<UserInfoDto>> read(UserSearchDto user) throws EmptyUsersException {
        List<UserInfoDto> users = userService.readAll(user);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/incomesById")
    public ResponseEntity<List<IncomeInfoDto>> readIncomesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<IncomeInfoDto> incomes = userService.getIncomes(id);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }


    @GetMapping(value = "/expensesById")
    public ResponseEntity<List<ExpenseInfoDto>> readExpensesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<ExpenseInfoDto> expenses = userService.getExpenses(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping(value = "/usersById")
    public ResponseEntity<UserInfoDto> readById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UserInfoDto user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping(value = "/usersById")
    public ResponseEntity<UserInfoDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id, @Valid @RequestBody UserUpdateDto user) throws NotFoundByIdException, SQLUniqueException {
        UserInfoDto userInfoDto = userService.update(user, id);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/usersById")
    public ResponseEntity<UUID> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UUID deletedId = userService.delete(id);
        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }


}

