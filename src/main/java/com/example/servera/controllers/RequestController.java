package com.example.servera.controllers;

import com.example.servera.entities.FriendList;
import com.example.servera.entities.Request;
import com.example.servera.entities.User;
import com.example.servera.services.FriendListService;
import com.example.servera.services.RequestService;
import com.example.servera.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
public class RequestController {

    private RestTemplate restTemplate = new RestTemplate();

<<<<<<< HEAD
    //final String homeIp = "34.235.165.180";
    //final String foreignIp = "54.210.22.88";

    final String homeIp = "http://localhost:8080/";
   final String foreignIp = "http://localhost:9091/";



=======
    final String homeIp = "18.209.167.95";
    final String foreignIp = "54.160.4.136";
>>>>>>> 4e7dd0c1fee5c7d358d85150e9b0ddd48bc0b4c9

    RequestService requestService;
    UserService userService;
    FriendListService fs;
    Map<String,String> newRequests = new HashMap<>();

    public RequestController(RequestService requestService,UserService userService, FriendListService fs){
        this.requestService = requestService;
        this.userService = userService;
        this.fs = fs;
    }


    @PostMapping("/friendship")
    public ResponseEntity<String> postGreeting(@RequestBody Map<String,String> req) {
        String friendshipRequest = req.get("request");
        String[] requestDetails = friendshipRequest.split("\\s+");

        System.out.println(friendshipRequest);
        User user = userService.findUserByEmail(requestDetails[6]);
        String foreignEmail = requestDetails[2];
        int foreignId = Integer.parseInt(requestDetails[4]);
        String senderIp = requestDetails[5] ;
        String receiverIp = requestDetails[7];


        // Checking if user exists by email AND if notAlreadyFriends

        if(user!= null && fs.notFriend(user.getEmail(),foreignEmail)){
            // create request in DB
            Request rq = new Request(user,user.getEmail(),foreignId,foreignEmail,senderIp,receiverIp);

            if(requestService.findRequestByUserAndForeignEmail(user,foreignEmail)==null){
                requestService.saveRequest(rq);
                return ResponseEntity.ok("TRUE Request created"+req.get("request")+" On "+new Date());
            }


        }
            return ResponseEntity.ok("FALSE Request was not created [Maybe already friends OR active request already exists]");

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

        return "Your request has been sent.";

    }


    @PostMapping("/response")
    public ResponseEntity<String> getConfirmation(@RequestBody Map<String,String> req) {

        String responseFromRequest = req.get("confirmation");
        String[] responseDetails = responseFromRequest.split("\\s+");
        //getting all the values from response in variables
        int userId = Integer.parseInt(responseDetails[2]);
        String userEmail = responseDetails[3];
        int foreignUserId = Integer.parseInt(responseDetails[4]);
        String foreignUserEmail = responseDetails[5];
        String foreignUserHost = responseDetails[6];

        User user = userService.findUserById(userId);
        fs.saveInFriendList(new FriendList(user,userEmail,foreignUserId,foreignUserEmail,foreignUserHost));

        System.out.println(responseFromRequest);


        return ResponseEntity.ok("Confirmed." +req.get("confirmation")+new Date());

    }









    //Endpoints

    @GetMapping("/all")
    public Iterable<Request> fetchAllRequests(){
        return requestService.findAllRequests();
    }

    @GetMapping("/{userId}")
    public List<Request> findRequestsByUserId(@PathVariable int userId){
        return requestService.findRequestsByUserId(userId);
    }

    @PostMapping("/save")
    public Request addRequest(@RequestBody Request request){
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

}
