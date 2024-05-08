package com.example.myapp.repository;


import com.example.myapp.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    List<Income> findAllByuser_id(Long user_id);

    List<Income> findAllBycategory_id(Long category_id);
    List<Income> findAllByuser_id(UUID user_id);
}