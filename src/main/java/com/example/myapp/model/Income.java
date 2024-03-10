package com.example.myapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "incomes")
public class Income {
    @Id
    @Column(name = "id")
    @JsonView(View.REST.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.REST.class)
    @Column(name = "comment")
    private String comment;

    @JsonView(View.REST.class)
    @Column(name = "value")
    private Float value;

    @JsonView(View.REST.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
