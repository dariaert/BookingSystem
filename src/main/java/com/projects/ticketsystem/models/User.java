package com.projects.ticketsystem.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "user_account")
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // разбить на отдельные поля
    @Column(name = "user_name")
    private String FIO;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password", length = 1000)
    private String password;

    @Column(name = "user_role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservation;

}
