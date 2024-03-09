package com.example.myapp.service;

import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;

import java.util.List;

public interface ExpenseService {
    /**
     * Создает новый расход
     * @param expense - расход для создания
     */
    void create(Expense expense);

    /**
     * Возвращает список всех имеющихся расходов
     * @return список расходов
     */
    List<Expense> readAll();

    /**
     * Возвращает расхода по его ID
     * @param id - ID расхода
     * @return - объект расхода с заданным ID
     */
    Expense read(Long id);

    /**
     * Обновляет расход с заданным ID,
     * в соответствии с переданным ДРУГИМ расходом
     * @param expense - расход в соответсвии с которым нужно обновить данные
     * @param id - id расхода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Expense expense, Long id);

    /**
     * Удаляет расход с заданным ID
     * @param id - id расхода, которого нужно удалить
     * @return - true если расход был удален, иначе false
     */
    boolean delete(Long id);
}
