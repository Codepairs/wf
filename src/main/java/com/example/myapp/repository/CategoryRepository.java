package com.example.myapp.repository;


import com.example.myapp.model.Category;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}