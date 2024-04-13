package org.example.controller;

import com.example.myapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(required = false) Integer age, @RequestParam String name, Model model) {
        User user = new User(name, age);

        // TODO save user to database
        // userDao.save(user);

        // TODO send out registration email
        // mailService.sendRegistrationEmail(user);

        model.addAttribute("user", user);
        return "registration-success";
    }
}