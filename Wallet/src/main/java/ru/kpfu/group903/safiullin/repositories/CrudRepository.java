package ru.kpfu.group903.safiullin.repositories;

import ru.kpfu.group903.safiullin.exceptions.DatabaseException;

import java.text.ParseException;
import java.util.List;

public interface CrudRepository<T> {
    void save(T entity) throws ParseException;

    void update(T entity) throws ParseException;

    void delete(T entity);

    T findById(Long id);

    List<T> findAll();
}
