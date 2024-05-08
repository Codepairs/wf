package org.example.controller;

import com.example.myapp.dto.user.UserCreateDto;
import org.example.service.UserService;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserUpdateDto;


import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserUpdateDto userDto = new UserUpdateDto();
        model.addAttribute("user", userDto);
        return "registration";
    }



    @PostMapping("/register")
    public String registerUser(@RequestParam(required = true) String email,
                               @RequestParam(required = true) String name,
                               @RequestParam(required = true) String password,
                               Model model) {
        UserUpdateDto user = new UserUpdateDto();
        user.builder().email(email).password(password).name(name).build();

        // TODO validate user
        // TODO save user to database
        // userDao.save(user);


        // TODO send out registration email
        // mailService.sendRegistrationEmail(user);

        model.addAttribute("user", user);
        return "registration-success";
        //return new ModelAndView("successRegister", "user", userDto);
    }


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }



    @GetMapping(value = "/users/{id}/expenses")
    public ResponseEntity<List<ExpenseInfoDto>> read_expenses(@PathVariable(name = "id") UUID id) {
        List<ExpenseInfoDto> expenses = userService.getExpenses(id);
        return expenses != null &&  !expenses.isEmpty()
                ? new ResponseEntity<>(expenses, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/users/{id}/incomes")
    public ResponseEntity<List<IncomeInfoDto>> read_incomes(@PathVariable(name = "id") UUID id) {
        List<IncomeInfoDto> incomes = userService.getIncomes(id);
        return incomes != null &&  !incomes.isEmpty()
                ? new ResponseEntity<>(incomes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserInfoDto> read(@PathVariable(name = "id") UUID id) {
        final UserInfoDto user = userService.read(id);
        return user != null
            ? new ResponseEntity<>(user, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody UserUpdateDto user) {
        final UserInfoDto updated = userService.update(user, id);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
        final UserInfoDto deleted = userService.delete(id);
        return deleted != null
                ? new ResponseEntity<>(deleted, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateDto user) {
        final UserInfoDto created = userService.create(user);
        return created != null
                ? new ResponseEntity<>(created, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserInfoDto>> readAll() {
        final List<UserInfoDto> users = userService.readAll();
        return users != null
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
