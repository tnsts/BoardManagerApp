package com.example.boardmanager.repositories;

import com.example.boardmanager.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Integer> {
    Board findById(Long id);

    Page<Board> findAll(Pageable pageable);
}
