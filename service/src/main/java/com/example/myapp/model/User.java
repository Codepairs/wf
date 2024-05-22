package com.example.myapp.model;

import com.example.myapp.converter.DateConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_name", unique = true)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Income> incomes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    List<Expense> expenses;

    @Column(name = "last_update")
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;
}

