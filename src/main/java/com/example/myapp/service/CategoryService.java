package com.example.myapp.service;

import com.example.myapp.dto.full.CategoryFullDto;
import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.info.CategoryInfoDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.info.IncomeInfoDto;
import com.example.myapp.dto.search.CategorySearchDto;
import com.example.myapp.dto.service.CategoryDto;
import com.example.myapp.dto.update.CategoryUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    /**
     * Создает новую категорию
     * @param category - категория для создания
     */
    UUID create(CategoryDto category) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся категорий
     * @return список категорий
     */
    List<CategoryInfoDto> readAll(CategorySearchDto categorySearchDto) throws EmptyCategoriesException;

    /**
     * Возвращает категорию по ее ID
     * @param id - ID категории
     * @return - объект категории с заданным ID
     */
    CategoryInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет катеорию с заданным ID,
     * в соответствии с переданным категорией
     * @param category - категория в соответствии с которой нужно обновить данные
     * @param id - id категории которую нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    CategoryInfoDto update(CategoryUpdateDto category, UUID id) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Удаляет категорию с заданным ID
     * @param id - id категории, которую нужно удалить
     * @return - true если категория была удалена, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;


    /**
     * Получает все доходы из категории по ее ID
     * @param categoryId - id категории, для которой нужно получить доходы
     * @return - список доходов
     */

    List<IncomeInfoDto> getIncomes(UUID categoryId) throws NotFoundByIdException;

    /**
     * Получает все расходы из категории по ее ID
     * @param categoryId - id категории, для которой нужно получить расходы
     * @return - список расходов
     */
    List<ExpenseInfoDto> getExpenses(UUID categoryId) throws NotFoundByIdException;
}
