package org.example.controller;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.model.Expense;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {

    @PostMapping("/expenses")
    public Expense expense(@RequestBody ExpenseFullDto dto) {
        // do something with the dto..create the booking..convert it to a transaction
        Expense expense = null;
        return expense;
    }
}