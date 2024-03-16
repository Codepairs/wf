package com.example.myapp.service;

import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;

import java.util.List;

public interface CategoryService {
    /**
     * Создает новую категорию
     * @param category - категория для создания
     */
    void create(Category category);

    /**
     * Возвращает список всех имеющихся категорий
     * @return список категорий
     */
    List<Category> readAll();

    /**
     * Возвращает категорию по ее ID
     * @param id - ID категории
     * @return - объект категории с заданным ID
     */
    Category read(Long id);

    /**
     * Обновляет катеорию с заданным ID,
     * в соответствии с переданным категорией
     * @param category - категория в соответствии с которой нужно обновить данные
     * @param id - id категории которую нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(Category category, Long id);

    /**
     * Удаляет категорию с заданным ID
     * @param id - id категории, которую нужно удалить
     * @return - true если категория была удалена, иначе false
     */
    boolean delete(Long id);


    /**
     * Получает все доходы из категории по ее ID
     * @param category_id - id категории, для которой нужно получить доходы
     * @return - список доходов
     */

    List<Income> getIncomesByUserId(Long category_id);

    /**
     * Получает все расходы из категории по ее ID
     * @param category_id - id категории, для которой нужно получить расходы
     * @return - список расходов
     */
    List<Expense> getExpensesByUserId(Long category_id);
}
