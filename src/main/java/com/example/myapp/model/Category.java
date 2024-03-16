package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    @JsonView(View.REST.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    @JsonView(View.REST.class)
    private String category_name;

    @OneToMany(mappedBy = "category",
              fetch = FetchType.LAZY,
              cascade = CascadeType.ALL)
    List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "category",
              fetch = FetchType.LAZY,
              cascade = CascadeType.ALL)
    List<Expense> expenses = new ArrayList<>();


    public Long getId(){
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String category_name) {
        this.category_name = category_name;
    }

}
