package com.example.myapp.model;

import com.example.myapp.converter.DateConverter;
import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Expense {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "value")
    private Float value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserFullDto user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryFullDto category;

    @Column(name = "last_update")
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;
}
