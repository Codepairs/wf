package org.example.controller;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import com.example.myapp.model.View;
import com.example.myapp.service.UserService;
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


    @JsonView(View.REST.class)
    @GetMapping(value = "/users/{id}/expenses")
    public ResponseEntity<List<ExpenseFullDto>> read_expenses(@PathVariable(name = "id") UUID id) {
        try {
            List<ExpenseFullDto> expenses = userService.getExpenses(id);
            return expenses != null &&  !expenses.isEmpty()
                    ? new ResponseEntity<>(expenses, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (NotFoundByIdException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonView(View.REST.class)
    @GetMapping(value = "/users/{id}/incomes")
    public ResponseEntity<List<IncomeFullDto>> read_incomes(@PathVariable(name = "id") UUID id) {
        try {
            List<IncomeFullDto> incomes = userService.getIncomes(id);
            return incomes != null &&  !incomes.isEmpty()
                    ? new ResponseEntity<>(incomes, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (NotFoundByIdException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserFullDto> read(@PathVariable(name = "id") UUID id) {
        try{
            final UserFullDto user = userService.read(id);
            return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (NotFoundByIdException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") UUID id, @RequestBody UserUpdateDto user) {
        try {
            final UserFullDto updated = userService.update(user, id);
            return updated != null
                    ? new ResponseEntity<>(updated, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        catch (SQLUniqueException | NotFoundByIdException e) {
            throw new RuntimeException(e);
        }
    }


}
