package com.example.myapp.service;

import com.example.myapp.model.User;

import java.util.List;

public interface UserService {
    /**
     * Создает нового клиента
     * @param user - клиент для создания
     */
    void create(User user);

    /**
     * Возвращает список всех имеющихся клиентов
     * @return список клиентов
     */
    List<User> readAll();

    /**
     * Возвращает клиента по его ID
     * @param id - ID клиента
     * @return - объект клиента с заданным ID
     */
    User read(Long id);

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     * @param user - клиент в соответсвии с которым нужно обновить данные
     * @param id - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(User user, Long id);

    /**
     * Удаляет клиента с заданным ID
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    boolean delete(Long id);
}
