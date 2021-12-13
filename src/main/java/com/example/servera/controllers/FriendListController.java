package com.example.servera.controllers;


import com.example.servera.entities.FriendList;
import com.example.servera.entities.Request;
import com.example.servera.services.FriendListService;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/friendlist")
public class FriendListController {

    FriendListService friendListService;

    public FriendListController(FriendListService friendListService){
        this.friendListService = friendListService;
    }


    @GetMapping("/{userId}")
    public List<FriendList> FriendListOfUser(@PathVariable int userId){
        return friendListService.findFriendListByUserId(userId);
    }

    @PostMapping("/save")
    public FriendList addRequest(@RequestBody FriendList friendList){
        return friendListService.saveInFriendList(friendList);
    }



}
