package com.digitalparadise.controller.managers;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;

import java.util.List;

public interface IManager<T, M> {
    void add(T element) throws RepositoryException;

    void remove(T element) throws RepositoryException;

    void update(M id, T element) throws RepositoryException;

    List<T> getAll();

}
