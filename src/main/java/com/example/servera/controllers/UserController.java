package com.example.servera.controllers;

import com.example.servera.entities.User;
import com.example.servera.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable int id){
        return userService.findUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.findUserByEmail(email);
    }

    @PostMapping("/adduser")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

}
