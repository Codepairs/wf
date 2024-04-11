package com.example.myapp.service;

import com.example.myapp.dto.full.ExpenseFullDto;
import com.example.myapp.dto.full.IncomeFullDto;
import com.example.myapp.dto.full.UserFullDto;
import com.example.myapp.dto.update.UserUpdateDto;
import com.example.myapp.handler.exceptions.EmptyUsersException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    /**
     * Создает нового клиента
     * @param user - клиент для создания
     */
    UserFullDto create(UserUpdateDto user) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся клиентов
     * @return список клиентов
     */
    List<UserFullDto> readAll() throws EmptyUsersException;

    /**
     * Возвращает клиента по его ID
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    UserFullDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     * @param user - клиент в соответсвии с которым нужно обновить данные
     * @param id - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    UserFullDto update(UserUpdateDto user, UUID id) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Удаляет клиента с заданным ID
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    void delete(UUID id) throws NotFoundByIdException;


    /**
     * Получает все доходы пользователя по его ID
     * @param user_id - id пользователя, для которого нужно получить доходы
     * @return - список доходов
     */

    List<IncomeFullDto> getIncomes(UUID user_id) throws NotFoundByIdException;

    /**
     * Получает все расходы пользователя по его ID
     * @param user_id - id пользователя, для которого нужно получить расходы
     * @return - список расходов
     */
    List<ExpenseFullDto> getExpenses(UUID user_id) throws NotFoundByIdException;
}
