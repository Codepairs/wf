package com.example.myapp.repository;


import com.example.myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
// Здесь можно добавить дополнительные методы для запросов к базе данных, если необходимо
}