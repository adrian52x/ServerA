package com.example.servera.repos;

import com.example.servera.entities.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendListRepository extends CrudRepository<FriendList,Integer> {
    List<FriendList> findFriendListByUser_Id(int userId);
}

