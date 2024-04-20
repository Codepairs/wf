package com.example.myapp.controller;
/*
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
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
    public ResponseEntity<?> create(@Valid @RequestBody UserUpdateDto user) throws SQLUniqueException {
        UserFullDto userFullDto = userService.create(user);
        return new ResponseEntity<>(userFullDto, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<List<UserFullDto>> read() throws EmptyUsersException {
        final List<UserFullDto> users = userService.readAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "incomesById")
    public ResponseEntity<List<IncomeFullDto>> readIncomesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<IncomeFullDto> incomes = userService.getIncomes(id);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }


    @GetMapping(value = "expensesById")
    public ResponseEntity<List<ExpenseFullDto>> readExpensesById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        List<ExpenseFullDto> expenses= userService.getExpenses(id);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping(value = "usersById")
    public ResponseEntity<UserFullDto> readById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        UserFullDto user = userService.read(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @PutMapping(value = "/usersById")
    public ResponseEntity<UserFullDto> update(@RequestParam(value = "id") @Valid @PathVariable UUID id, @Valid @RequestBody UserUpdateDto user) throws NotFoundByIdException, SQLUniqueException {
        final UserFullDto userFullDto = userService.update(user, id);
        return new ResponseEntity<>(userFullDto, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

*/
