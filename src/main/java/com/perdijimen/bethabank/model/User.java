package com.perdijimen.bethabank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria tipo Long")
    private Long id;

    @Column
    @ApiModelProperty("Nombre completo del usuario")
    private String name;

    @Column
    @ApiModelProperty("Apellido del usuario")
    private String lastname;

    @Column
    @ApiModelProperty("Fecha de nacimiento del usuario")
    private LocalDate birth_day;

    @Column
    @ApiModelProperty("Teléfono del usuario")
    private String phone;

    @Column
    @ApiModelProperty("Dirección de email del usuario")
    private String email;

    @Column
    @ApiModelProperty("Contraseña del usuario")
    private String password;

    @Column
    @ApiModelProperty("DNI del usuario")
    private String DNI;

    @Column
    @ApiModelProperty("Dirección del usuario")
    private String address;

    @Column
    @ApiModelProperty("Localidad de la dirección del usuario")
    private String location;

    @Column
    @ApiModelProperty("País de la dirección del usuario")
    private String country;

    @Column
    @ApiModelProperty("Fecha de alta del usuario")
    private LocalDate created_at;

    @Column
    @ApiModelProperty("Fecha de última actualización del usuario")
    private LocalDate updated_at;

    @OneToMany(mappedBy = "user")
    private List<Account> accountList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cardList;


    public User() {
    }

    public User(String name, String lastname, LocalDate birth_day, String phone, String email, String password, String DNI, String address, String location, String country, LocalDate created_at, LocalDate updated_at) {
        this.name = name;
        this.lastname = lastname;
        this.birth_day = birth_day;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.DNI = DNI;
        this.address = address;
        this.location = location;
        this.country = country;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(LocalDate birth_day) {
        this.birth_day = birth_day;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }


    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }


    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birth_day=" + birth_day +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", DNI='" + DNI + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +

                '}';
    }
}
