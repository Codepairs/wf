package com.example.myapp.service;

import com.example.myapp.dto.category.CategoryCreateDto;
import com.example.myapp.dto.category.CategoryInfoDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.category.CategorySearchDto;
import com.example.myapp.dto.category.CategoryUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    /**
     * Создает новую категорию
     *
     * @param category - категория для создания
     */
    UUID create(CategoryCreateDto category) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся категорий
     *
     * @return список категорий
     */
    List<CategoryInfoDto> readAll(CategorySearchDto categorySearchDto) throws EmptyCategoriesException;

    /**
     * Возвращает категорию по ее ID
     *
     * @param id - ID категории
     * @return - объект категории с заданным ID
     */
    CategoryInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет катеорию с заданным ID,
     * в соответствии с переданным категорией
     *
     * @param category - категория в соответствии с которой нужно обновить данные
     * @param id       - id категории которую нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    CategoryInfoDto update(CategoryUpdateDto category, UUID id) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Удаляет категорию с заданным ID
     *
     * @param id - id категории, которую нужно удалить
     * @return - true если категория была удалена, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;


    /**
     * Получает все доходы из категории по ее ID
     *
     * @param categoryId - id категории, для которой нужно получить доходы
     * @return - список доходов
     */

    List<IncomeInfoDto> getIncomes(UUID categoryId) throws NotFoundByIdException;

    /**
     * Получает все расходы из категории по ее ID
     *
     * @param categoryId - id категории, для которой нужно получить расходы
     * @return - список расходов
     */
    List<ExpenseInfoDto> getExpenses(UUID categoryId) throws NotFoundByIdException;
}
