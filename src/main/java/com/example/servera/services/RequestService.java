package com.example.servera.services;

import com.example.servera.entities.Request;
import com.example.servera.repos.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    RequestRepository requestRepository;

    //Constructor
    public RequestService(RequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }
    ////////////



    public Iterable<Request> findAllRequests(){
        return requestRepository.findAll();
    }

    public Optional<Request> findRequestById(int id){
        return requestRepository.findRequestById(id);
    }

    public List<Request> findRequestsByUserId(int userId){
        return requestRepository.findRequestByUser_Id(userId);
    }

    public Request saveRequest(Request request){
        return requestRepository.save(request);
    }

    public void deleteRequest(Request request){
        requestRepository.delete(request);
    }
}
