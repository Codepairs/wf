package com.example.myapp.service;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.exceptions.EmptyCategoriesException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Category;
import com.example.myapp.model.Expense;
import com.example.myapp.model.Income;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    /**
     * Создает новую категорию
     * @param category - категория для создания
     */
    CategoryFullDto create(CategoryUpdateDto category) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся категорий
     * @return список категорий
     */
    List<CategoryFullDto> readAll() throws EmptyCategoriesException;

    /**
     * Возвращает категорию по ее ID
     * @param id - ID категории
     * @return - объект категории с заданным ID
     */
    CategoryFullDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет катеорию с заданным ID,
     * в соответствии с переданным категорией
     * @param category - категория в соответствии с которой нужно обновить данные
     * @param id - id категории которую нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    CategoryFullDto update(CategoryUpdateDto category, UUID id) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Удаляет категорию с заданным ID
     * @param id - id категории, которую нужно удалить
     * @return - true если категория была удалена, иначе false
     */
    void delete(UUID id) throws NotFoundByIdException;


    /**
     * Получает все доходы из категории по ее ID
     * @param category_id - id категории, для которой нужно получить доходы
     * @return - список доходов
     */

    List<Income> getIncomesByUserId(Long category_id);
    List<IncomeFullDto> getIncomes(UUID categoryId) throws NotFoundByIdException;

    /**
     * Получает все расходы из категории по ее ID
     * @param category_id - id категории, для которой нужно получить расходы
     * @return - список расходов
     */
    List<Expense> getExpensesByUserId(Long category_id);
    List<ExpenseFullDto> getExpenses(UUID categoryId) throws NotFoundByIdException;
}
