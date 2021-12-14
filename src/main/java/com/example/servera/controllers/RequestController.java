package com.example.servera.controllers;

import com.example.servera.entities.Request;
import com.example.servera.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/requests")
public class RequestController {


    private RestTemplate restTemplate = new RestTemplate();

    final String foreignIp = "http://localhost:9091";
    final String homeIp = "http://localhost:8080";

    RequestService requestService;
    LogInController lc;

    public RequestController(RequestService requestService, LogInController lc) {

        this.requestService = requestService;
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
    public String sendGreeting(@RequestParam String f_email, Model model) {
        String receiverEmail = f_email;
        String receiverIP = foreignIp + "/friendsip";
        String userEmail = lc.currentUserEmail;
        Map<String, String> reqMap = new HashMap<>();
        String method = "request";
        String requestForFriendship = "{" + method + ":" + userEmail + " " + homeIp + " " + receiverEmail + " " + foreignIp + " " + "v1" + "}";
        reqMap.put("request", requestForFriendship);
        ResponseEntity response = restTemplate.postForEntity(receiverIP, reqMap, String.class);
        model.addAttribute("request", response.getBody());
        model.addAttribute("userEmail", userEmail);
        System.out.println(response.getBody());
        return "responseDisplay";

    }

}
