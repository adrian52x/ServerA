package com.example.servera.services;

import com.example.servera.entities.FriendList;
import com.example.servera.repos.FriendListRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class FriendListService {

    FriendListRepository friendListRepository;

    public FriendListService(FriendListRepository friendListRepository){
        this.friendListRepository = friendListRepository;
    }


    public List<FriendList> findFriendListByUserId(int userId){
        return friendListRepository.findFriendListByUser_Id(userId);
    }

    public FriendList saveInFriendList(FriendList friendList){
        return friendListRepository.save(friendList);
    }

}
