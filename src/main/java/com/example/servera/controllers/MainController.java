package com.example.servera.controllers;

import com.example.servera.entities.FriendList;
import com.example.servera.entities.Request;
import com.example.servera.services.FriendListService;
import com.example.servera.services.RequestService;
import com.example.servera.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    List<Request> requestList = new ArrayList<>();
    int  currentId = 0;

    UserService userService;
    FriendListService friendListService;
    RequestService requestService;

    public MainController(UserService userService, FriendListService friendListService, RequestService requestService) {
        this.userService = userService;
        this.friendListService = friendListService;
        this.requestService = requestService;
    }

    @GetMapping("/acceptRequest/{currentId1}/{requestId}")
    public String acceptRequest(@PathVariable int currentId1, @PathVariable int requestId){
        Request request = requestService.findRequestById(requestId).get();
        friendListService.saveInFriendList(new FriendList(request.getUser(),request.getUserEmail(),request.getForeignUserId(),request.getForeignUserEmail(),request.getSenderIp()));
        requestService.deleteRequest(request);
        requestList = requestService.findRequestsByUserId(currentId1);
        currentId = currentId1;

        return "redirect:/";
    }

    @GetMapping("/declineRequest/{requestId}")
    public String declineRequest( @PathVariable int requestId){
        Request request = requestService.findRequestById(requestId).get();
        requestService.deleteRequest(request);
        currentId = request.getUser().getId();
        requestList = requestService.findRequestsByUserId(currentId);

        return "redirect:/";
    }

    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("myRequestList",requestList);
        model.addAttribute("currentId1",currentId);

        return "index";
    }

    @GetMapping("/friendList/{currentId1}")
    public String getFriendList(@PathVariable int currentId1, Model model) {
        List<FriendList> friendList = friendListService.findFriendListByUserId(currentId1);
        model.addAttribute("friendList", friendList);
        return "friendList";

    }
}
