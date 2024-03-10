package com.example.myapp.repository;


import com.example.myapp.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByuser_id(Long user_id);
}