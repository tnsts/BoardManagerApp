package com.example.boardmanager.repositories;

import com.example.boardmanager.domain.Column;
import org.springframework.data.repository.CrudRepository;

public interface ColumnRepository extends CrudRepository<Column, Integer> {
    Column findById(Long id);
}
