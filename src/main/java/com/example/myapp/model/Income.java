package com.example.myapp.model;

import com.example.myapp.converter.DateConverter;
import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "incomes")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Income {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "value")
    private BigDecimal value;

    @Column
    @Convert(converter = DateConverter.class)
    private LocalDate getDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "last_update")
    @UpdateTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime lastUpdateTime;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    @Convert(converter = DateConverter.class)
    private LocalDateTime creationTime;

}
