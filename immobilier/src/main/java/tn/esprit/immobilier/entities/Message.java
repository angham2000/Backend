package tn.esprit.immobilier.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

//Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
   // private Integer id;

    private String user;

    private String message;


}
