package com.example.servera.repos;

import com.example.servera.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserById(int id);
    User findUserByEmail(String email);

}