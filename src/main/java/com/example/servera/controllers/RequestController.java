package com.example.servera.controllers;

import com.example.servera.entities.Request;
import com.example.servera.entities.User;
import com.example.servera.services.RequestService;
import com.example.servera.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequestController {


    private RestTemplate restTemplate = new RestTemplate();

    final String foreignIp = "http://localhost:9091";
    final String homeIp = "http://localhost:8080";

    RequestService requestService;
    UserService userService;
    LogInController lc;

    public RequestController(RequestService requestService, UserService userService, LogInController lc) {

        this.requestService = requestService;
        this.userService = userService;
        this.lc = lc;
    }

    @GetMapping("/all")
    public Iterable<Request> fetchAllRequests() {
        return requestService.findAllRequests();
    }

    @GetMapping("/{userId}")
    public List<Request> findRequestsByUserId(@PathVariable int userId) {
        return requestService.findRequestsByUserId(userId);
    }

    @PostMapping("/save")
    public Request addRequest(@RequestBody Request request) {
        return requestService.saveRequest(request);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteRequest(@PathVariable int id)
            throws ResourceNotFoundException {
        Request request = requestService.findRequestById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found for this id :: " + id));

        requestService.deleteRequest(request);
        Map<String, Boolean> response = new HashMap<>();
        response.put("request deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/sendFriendRequest")
    public String sendGreeting(@RequestParam String f_email,@RequestParam String currentemail,@RequestParam String userid, Model model) {
        System.out.println(f_email);
        String foreignEmail = f_email;
        String currentUserId = userid;
        String receiverIP = foreignIp + "/friendship";
        String userEmail = currentemail;
        Map<String, String> reqMap = new HashMap<>();
        String method = "request";
        String requestForFriendship = "{" + method + ": " + userEmail +" "+currentUserId+" "+ homeIp + " " + foreignEmail + " " + foreignIp + " " + "v1" + "}";
        reqMap.put("request", requestForFriendship);
        ResponseEntity response = restTemplate.postForEntity(receiverIP, reqMap, String.class);
        model.addAttribute("request", response.getBody());
        model.addAttribute("userEmail", userEmail);
        System.out.println(response.getBody());
       // String responseFromB = response.getBody().toString().substring(0, 28);
        String[] responseDetails = response.getBody().toString().split("\\s+");

        User user = userService.findUserByEmail(currentemail);
        Request rq = new Request(user, foreignEmail);

        if(responseDetails[0].equals("TRUE")){
            requestService.saveRequest(rq);
        }
        return "responseDisplay";

    }

}
