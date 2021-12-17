package com.example.servera.services;


import com.example.servera.entities.User;
import com.example.servera.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    //Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /////////////


    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(int id){
        return userRepository.findUserById(id);
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User saveUser(User user){ return userRepository.save(user);}

}
