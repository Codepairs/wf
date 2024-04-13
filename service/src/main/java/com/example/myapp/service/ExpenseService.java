package com.example.myapp.service;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.exceptions.EmptyExpenseException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    /**
     * Создает новый расход
     * @param expense - расход для создания
     */
    ExpenseFullDto create(ExpenseUpdateDto expense) throws SQLUniqueException;


    /**
     * Возвращает список всех имеющихся расходов
     * @return список расходов
     */
    List<ExpenseFullDto> readAll() throws EmptyExpenseException;

    /**
     * Возвращает расход по его ID
     * @param id - ID расхода
     * @return - объект расхода с заданным ID
     */
    ExpenseFullDto read(UUID id) throws NotFoundByIdException;


    /**
     * Возвращает список всех расходов по ID определенной категории
     * @param id - ID категории
     * @return список расходов по этой категории
     */
    List<Expense> readAllByCategoryId(Long id);

    /**
     * Обновляет расход с заданным ID,
     * в соответствии с переданным ДРУГИМ расходом
     * @param expense - расход в соответсвии с которым нужно обновить данные
     * @param id - id расхода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    ExpenseFullDto update(ExpenseUpdateDto expense, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Удаляет расход с заданным ID
     * @param id - id расхода, которого нужно удалить
     * @return - true если расход был удален, иначе false
     */
    void delete(UUID id) throws NotFoundByIdException;
}
