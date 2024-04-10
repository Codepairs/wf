package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @JsonView(View.REST.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user",
              fetch = FetchType.LAZY,
              cascade = CascadeType.ALL)
    List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "user",
              fetch = FetchType.LAZY,
              cascade = CascadeType.ALL)
    List<Expense> expenses = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.user_name;
    }

    public void setName(String name) {
        this.user_name = name;
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


}

