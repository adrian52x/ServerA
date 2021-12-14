package com.example.servera.repos;

import com.example.servera.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    List<Request> findRequestByUser_Id(int userId);
    //Optional<Request> findRequestById(int id);
    Request deleteRequestById(int id);
}
