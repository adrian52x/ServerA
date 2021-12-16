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
    private String userEmail; // homeUserEmail

    private int foreignUserId; //foreignUser Id
    private String foreignUserEmail; //foreignUser email

    private String senderIp;
    private String receiverIp;

    private String status = "pending";



}
