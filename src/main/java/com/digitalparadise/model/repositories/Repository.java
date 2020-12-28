package com.digitalparadise.model.repositories;

import com.digitalparadise.controller.exceptions.repository.RepositoryException;

import java.util.List;

public interface Repository<T, M> {
    T getResourceByUUID(M element) throws RepositoryException;

    void add(T element) throws RepositoryException;

    void remove(T element) throws RepositoryException;

    void update(M id, T element) throws RepositoryException;

    List<T> getAll();

    String toString();
}
