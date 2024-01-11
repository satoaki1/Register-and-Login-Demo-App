package com.example.randlandmanageuser.controller;

import com.example.randlandmanageuser.entity.dto.UserDto;
import com.example.randlandmanageuser.exception.DuplicateUserException;
import com.example.randlandmanageuser.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        try {
            userService.saveUser(userDto);
        } catch (DuplicateUserException e) {
            if (e.getMessage().contains("email")) {
                result.rejectValue("email", "duplicate.email");
            } else if (e.getMessage().contains("username")) {
                result.rejectValue("username", "duplicate.username");
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
