package com.example.myapp.service;

import com.example.myapp.dto.expense.ExpenseCreateDto;
import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.expense.ExpenseSearchDto;
import com.example.myapp.dto.expense.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.search.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    /**
     * Создает новый расход
     *
     * @param expense - расход для создания
     */
    UUID create(ExpenseCreateDto expense) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Возвращает список всех имеющихся расходов
     *
     * @return список расходов
     */
    List<ExpenseInfoDto> readAll(ExpenseSearchDto expenseSearchDto) throws EmptyExpenseException;

    /**
     * Возвращает расхода по его ID
     *
     * @param id - ID расхода
     * @return - объект расхода с заданным ID
     */
    ExpenseInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет расход с заданным ID,
     * в соответствии с переданным ДРУГИМ расходом
     *
     * @param expense - расход в соответсвии с которым нужно обновить данные
     * @param id      - id расхода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    ExpenseInfoDto update(ExpenseUpdateDto expense, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Удаляет расход с заданным ID
     *
     * @param id - id расхода, которого нужно удалить
     * @return - true если расход был удален, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;

    List<ExpenseInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException;
}
