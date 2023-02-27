package com.likelion.Demojwt.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_db")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;
    private String password;
}
