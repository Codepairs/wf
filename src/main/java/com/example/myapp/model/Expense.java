package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @Column(name = "id")
    @JsonView(View.REST.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    @JsonView(View.REST.class)
    private String comment;

    @Column(name = "value")
    @JsonView(View.REST.class)
    private Float value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonView(View.REST.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonView(View.REST.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Float getValue(){
        return this.value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Long getId(){
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUserId(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }
}
