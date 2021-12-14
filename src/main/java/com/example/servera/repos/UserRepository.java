package com.example.servera.repos;

import com.example.servera.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    //User findUserById(int id);
    User findUserByEmail(String email);

}