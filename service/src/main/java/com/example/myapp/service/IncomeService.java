package com.example.myapp.service;

import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;

import java.util.List;

public interface IncomeService {
    /**
     * Создает новый доход
     * @param income - доход для создания
     */
    void create(Income income);

    /**
     * Возвращает список всех имеющихся доходов
     * @return список доходов
     */
    List<Income> readAll();

    /**
     * Возвращает доход по его ID
     * @param id - ID дохода
     * @return - объект дохода с заданным ID
     */
    Income read(Long id);


    /**
     * Возвращает список всех доходов по ID определенной категории
     * @param id - ID категории
     * @return список доходов по этой категории
     */
    List<Income> readAllByCategoryId(Long id);


    /**
     * Обновляет доход с заданным ID,
     * в соответствии с переданным ДРУГИМ доходом
     * @param income - доход в соответсвии с которым нужно обновить данные
     * @param id - id дохода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Income income, Long id);

    /**
     * Удаляет доход с заданным ID
     * @param id - id дохода, которого нужно удалить
     * @return - true если доход был удален, иначе false
     */
    boolean delete(Long id);
}
