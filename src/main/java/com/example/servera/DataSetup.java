package com.example.servera;

import com.example.servera.entities.FriendList;
import com.example.servera.entities.Request;
import com.example.servera.entities.User;
import com.example.servera.repos.FriendListRepository;
import com.example.servera.repos.RequestRepository;
import com.example.servera.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSetup implements CommandLineRunner {

    UserRepository userRepository;
    RequestRepository requestRepository;
    FriendListRepository friendListRepository;

    public DataSetup(UserRepository userRepository, RequestRepository requestRepository, FriendListRepository friendListRepository){
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.friendListRepository = friendListRepository;
    }



    @Override
    public void run(String... args) throws Exception {

        User u1 = userRepository.save(new User("Adam","Ench","a@gmail.com"));
        User u2 = userRepository.save(new User("Bert","Hede","b@gmail.com"));
        User u3 = userRepository.save(new User("Creed","Luk","c@gmail.com"));
        User u4 = userRepository.save(new User("David","Virk","d@gmail.com"));
        User u5 = userRepository.save(new User("Dominic","Smith","ds@gmail.com"));
        User u6 = userRepository.save(new User("Mikkel","Meinertsen","mm@gmail.com"));
        User u7 = userRepository.save(new User("Michelle","Theisen","mt@gmail.com"));
        User u8 = userRepository.save(new User("Nikolaj","Struntze","ns@gmail.com"));




    }
}
