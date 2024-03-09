package com.example.myapp.repository;


import com.example.myapp.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
// Здесь можно добавить дополнительные методы для запросов к базе данных, если необходимо
}