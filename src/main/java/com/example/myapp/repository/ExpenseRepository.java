package com.example.myapp.repository;


import com.example.myapp.model.Expense;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByuser_id(Long user_id);

    List<Expense> findAllBycategory_id(Long category_id);


}