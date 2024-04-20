package com.example.myapp.service;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.info.ExpenseInfoDto;
import com.example.myapp.dto.search.ExpenseSearchDto;
import com.example.myapp.dto.service.ExpenseDto;
import com.example.myapp.dto.update.ExpenseUpdateDto;
import com.example.myapp.handler.exceptions.EmptyExpenseException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    /**
     * Создает новый расход
     * @param expense - расход для создания
     */
    UUID create(ExpenseDto expense) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся расходов
     * @return список расходов
     */
    List<ExpenseInfoDto> readAll(ExpenseSearchDto expenseSearchDto) throws EmptyExpenseException;

    /**
     * Возвращает расхода по его ID
     * @param id - ID расхода
     * @return - объект расхода с заданным ID
     */
    ExpenseInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет расход с заданным ID,
     * в соответствии с переданным ДРУГИМ расходом
     * @param expense - расход в соответсвии с которым нужно обновить данные
     * @param id - id расхода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    ExpenseInfoDto update(ExpenseUpdateDto expense, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Удаляет расход с заданным ID
     * @param id - id расхода, которого нужно удалить
     * @return - true если расход был удален, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;
}
