package com.example.boardmanager.repositories;

import com.example.boardmanager.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findById(Long id);
}
