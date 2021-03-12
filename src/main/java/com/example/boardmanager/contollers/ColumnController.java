package com.example.boardmanager.contollers;

import com.example.boardmanager.domain.Board;
import com.example.boardmanager.domain.Column;
import com.example.boardmanager.repositories.ColumnRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ColumnController {
    private final ColumnRepository columnRepository;

    public ColumnController(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    @PostMapping("board/{id}/column")
    public Column createColumn(@PathVariable long id, @RequestParam String title) {
        Column column = new Column(title, new Board());
        column.getBoard().setId(id);
        return columnRepository.save(column);
    }

    @GetMapping("column/{id}")
    public Column getColumn(@PathVariable long id) {
        return columnRepository.findById(id);
    }

    @PutMapping("column/{id}")
    public Column updateColumn(
            @PathVariable long id,
            @RequestParam(required = false) String title
    ) {
        Column column = columnRepository.findById(id);
        if (title != null) column.setTitle(title);
        return columnRepository.save(column);
    }

    @DeleteMapping("column/{id}")
    public Column deleteColumn(@PathVariable long id) {
        Column column = columnRepository.findById(id);
        columnRepository.delete(column);
        return column;
    }

}
