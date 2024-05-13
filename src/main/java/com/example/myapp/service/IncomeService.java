package com.example.myapp.service;

import com.example.myapp.dto.income.IncomeCreateDto;
import com.example.myapp.dto.income.IncomeInfoDto;
import com.example.myapp.dto.income.IncomeSearchDto;
import com.example.myapp.dto.income.IncomeUpdateDto;
import com.example.myapp.handler.exceptions.*;
import com.example.myapp.search.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IncomeService {
    /**
     * Создает новый доход
     *
     * @param income - расход для создания
     */
    UUID create(IncomeCreateDto income) throws SQLUniqueException, NotFoundByIdException;

    /**
     * Возвращает список всех имеющихся расходов
     *
     * @return список расходов
     */
    List<IncomeInfoDto> readAll(IncomeSearchDto incomeSearchDto) throws EmptyExpenseException, EmptyIncomesException;

    /**
     * Возвращает расхода по его ID
     *
     * @param id - ID расхода
     * @return - объект расхода с заданным ID
     */
    IncomeInfoDto read(UUID id) throws NotFoundByIdException;

    /**
     * Обновляет расход с заданным ID,
     * в соответствии с переданным ДРУГИМ расходом
     *
     * @param income - расход в соответсвии с которым нужно обновить данные
     * @param id     - id расхода которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    IncomeInfoDto update(IncomeUpdateDto income, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Удаляет расход с заданным ID
     *
     * @param id - id расхода, которого нужно удалить
     * @return - true если расход был удален, иначе false
     */
    UUID delete(UUID id) throws NotFoundByIdException;

    List<IncomeInfoDto> getByFilter(List<SearchCriteria<?>> conditions, Pageable pageable) throws EmptyCategoriesException;
}
