package com.example.myapp.service;

import com.example.myapp.dto.expense.ExpenseInfoDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.user.UserCreateDto;
import com.example.myapp.dto.user.UserInfoDto;
import com.example.myapp.dto.user.UserSearchDto;
import com.example.myapp.dto.user.UserUpdateDto;
import com.example.myapp.handler.exceptions.EmptyCategoriesException;
import com.example.myapp.handler.exceptions.EmptyUsersException;
import com.example.myapp.handler.exceptions.NotFoundByIdException;
import com.example.myapp.handler.exceptions.SQLUniqueException;
import com.example.myapp.model.User;
import com.example.myapp.search.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    /**
     * Создает нового клиента
     *
     * @param user - клиент для создания
     */
    UUID create(UserCreateDto user) throws SQLUniqueException;

    /**
     * Возвращает список всех имеющихся клиентов
     *
     * @return список клиентов
     */
    List<UserInfoDto> readAll(UserSearchDto user) throws EmptyUsersException;

    /**
     * Возвращает клиента по его ID
     *
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    UserInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Возвращает клиента по его ID
     *
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    User read(String name) throws NotFoundByIdException;

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     *
     * @param user - клиент в соответсвии с которым нужно обновить данные
     * @param id   - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */




    UserInfoDto update(UserUpdateDto user, UUID id) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Удаляет клиента с заданным ID
     *
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;


    /**
     * Получает все доходы пользователя по его ID
     *
     * @param user_id - id пользователя, для которого нужно получить доходы
     * @return - список доходов
     */

    List<IncomeInfoDto> getIncomes(UUID user_id) throws NotFoundByIdException;

    /**
     * Получает все расходы пользователя по его ID
     *
     * @param user_id - id пользователя, для которого нужно получить расходы
     * @return - список расходов
     */
    List<ExpenseInfoDto> getExpenses(UUID user_id) throws NotFoundByIdException;

    List<UserInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException;

    List<ExpenseInfoDto> getExpensesByFilterAndId(List<SearchCriteria<?>> conditions, Pageable pageable,UUID user_id) throws NotFoundByIdException;

    List<IncomeInfoDto> getIncomesByFilterAndId(List<SearchCriteria<?>> conditions, Pageable pageable,UUID user_id) throws NotFoundByIdException;
}
