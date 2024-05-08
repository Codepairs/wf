package org.example.controller;

import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserUpdateDto;
import com.example.myapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class RegistrationController {

    /*
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("password", new String());
        return "registration";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserUpdateDto userDto = new UserUpdateDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam(required = true) String email,
                               @RequestParam(required = true) String password,
                               @RequestParam(required = true) String name,
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
    }

     */
}