package com.perdijimen.bethabank.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private Date birth_day;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String DNI;

    @Column
    private String address;

    @Column
    private String location;

    @Column
    private String country;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;

   // @OneToMany(mappedBy = "users")
  //  private List<account> account = new ArrayList();




}
