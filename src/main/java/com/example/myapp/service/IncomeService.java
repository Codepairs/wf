package com.example.myapp.service;

import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.update.IncomeUpdateDto;
import com.example.myapp.exceptions.EmptyIncomesException;
import com.example.myapp.exceptions.NotFoundByIdException;
import com.example.myapp.exceptions.SQLUniqueException;
import com.example.myapp.model.Income;
import com.example.myapp.model.User;

import java.util.List;
import java.util.UUID;

public interface IncomeService {
    /**
     * Создает новый доход
     * @param income - доход для создания
     */
    IncomeFullDto create(IncomeUpdateDto income) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся доходов
     * @return список доходов
     */
    List<IncomeFullDto> readAll() throws EmptyIncomesException;

    /**
     * Возвращает доход по его ID
     * @param id - ID дохода
     * @return - объект дохода с заданным ID
     */
    IncomeFullDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет доход с заданным ID,
     * в соответствии с переданным ДРУГИМ доходом
     * @param income - доход в соответсвии с которым нужно обновить данные
     * @param id - id дохода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    IncomeFullDto update(IncomeUpdateDto income, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Удаляет доход с заданным ID
     * @param id - id дохода, которого нужно удалить
     */
    void delete(UUID id) throws NotFoundByIdException;
}
