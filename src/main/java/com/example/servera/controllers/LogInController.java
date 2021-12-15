package com.example.servera.controllers;

import com.example.servera.entities.User;
import com.example.servera.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogInController {
    int currentUserId = 0;
    String currentUserEmail = "";
    UserService userService;

    public LogInController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }


    @PostMapping("/")
    public ModelAndView login(@RequestParam String email, Model model) {
        System.out.println(email);
        ModelAndView mv = new ModelAndView("index");
        ModelAndView loginMv = new ModelAndView("login");
        User currentUser = userService.findUserByEmail(email);
        if (currentUser != null) {
            currentUserId = currentUser.getId();
            System.out.println(currentUserId);
            model.addAttribute("currentEmail",email);
            model.addAttribute("currentId",currentUserId);
            return mv;

        }
        return loginMv;
    }
}
