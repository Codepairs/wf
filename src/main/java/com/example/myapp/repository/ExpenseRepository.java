package com.example.myapp.repository;


import com.example.myapp.model.Expense;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;



@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Здесь можно добавить дополнительные методы для запросов к базе данных, если необходимо
}