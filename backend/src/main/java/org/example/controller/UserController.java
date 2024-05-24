package org.example.controller;


import org.example.dto.expense.ExpenseInfoDto;
import org.example.dto.income.IncomeInfoDto;
import org.example.dto.user.UserInfoDto;
import org.example.dto.user.UserUpdateDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/incomesById/{id}")
    public ResponseEntity<Flux<IncomeInfoDto>> getIncomesById(@PathVariable(name = "id") UUID id) {
        Flux<IncomeInfoDto> incomes = userService.getIncomesById(id);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/expensesById/{id}")
    public ResponseEntity<Flux<ExpenseInfoDto>> getExpensesById(@PathVariable(name = "id") UUID id) {
        Flux<ExpenseInfoDto> incomes = userService.getExpensesById(id);
        return incomes != null
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/usersById/{id}")
    public ResponseEntity<Mono<UserInfoDto>> getUserById(@PathVariable(name = "id") UUID id) {
        final Mono<UserInfoDto> user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/usersById/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody UserUpdateDto user) {
        final Mono<UserInfoDto> updated = userService.updateUserById(user, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/usersById/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final Mono<Void> deleted = userService.deleteUserById(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @GetMapping(value = "/pagination")
    public ResponseEntity<Flux<UserInfoDto>> getUsersPagination() {
        final Flux<UserInfoDto> users = userService.getUsersPagination();
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
