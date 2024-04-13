package com.example.myapp.repository;


import com.example.myapp.model.Expense;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

<<<<<<< HEAD
    List<Expense> findAllByuser_id(Long user_id);

    List<Expense> findAllBycategory_id(Long category_id);


=======
    List<Expense> findAllByuser_id(UUID user_id);
>>>>>>> 7c874bf9201aece73d925cf334f9c183676c67c0
}