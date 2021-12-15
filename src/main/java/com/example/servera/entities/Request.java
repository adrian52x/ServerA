package com.example.servera.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user; // homeUser

    private String foreignUser; //foreignUser email

    private String status = "pending";


    public Request(User user, String foreignUser){
        this.user = user;
        this.foreignUser = foreignUser;
    }

}
