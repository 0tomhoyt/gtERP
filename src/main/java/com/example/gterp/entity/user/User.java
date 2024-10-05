package com.example.gterp.entity.user;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(length = 250)
    private String email;

    @Column(length = 250)
    private String phoneNumber;

    @Column(length = 250)
    private String nickName;

    @Column(length = 250)
    private String role;

    @Column
    private String permission;

    // Getter和Setter方法
}
