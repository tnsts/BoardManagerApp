package com.example.boardmanager.contollers;

import com.example.boardmanager.domain.Board;
import com.example.boardmanager.repositories.BoardRepository;
import com.example.boardmanager.services.FileSystemService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/board")
public class BoardController {
    private final BoardRepository boardRepository;

    private final FileSystemService fileSystemService;

    public BoardController(BoardRepository boardRepository, FileSystemService fileSystemService) {
        this.boardRepository = boardRepository;
        this.fileSystemService = fileSystemService;
    }

    @PostMapping()
    public Board createBoard(@RequestParam String name) {
        Board board = new Board(name);
        return boardRepository.save(board);
    }

    @GetMapping("/{id}/full-info")
    public Board getFullBoardById(@PathVariable long id) {
        return boardRepository.findById(id);
    }

    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable long id) {
        Board board = boardRepository.findById(id);
        return new Board(board.getId(), board.getName());
    }

    @GetMapping("/all")
    public List<Board> readAllBoards(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return boardRepository.findAll(pageable).getContent();
    }

    @PutMapping()
    public Board updateBoard(@RequestBody Board board) {
        Board prevBoard = boardRepository.findById(board.getId());
        prevBoard.setName(board.getName());
        return boardRepository.save(prevBoard);
    }

    @DeleteMapping("/{id}")
    public Board deleteColumn(@PathVariable long id) {
        Board board = boardRepository.findById(id);
        boardRepository.delete(board);
        return board;
    }

    @PostMapping("/{id}/upload")
    Board uploadImage(
            @PathVariable long id,
            @RequestParam("bg-image") MultipartFile multipartFile
    ) throws Exception {
        Board board = boardRepository.findById(id);
        board.setBgImgPath(fileSystemService.save(
                multipartFile.getBytes(), id + "." + multipartFile.getOriginalFilename().split("\\.")[1]
        ));
        return board;
    }

    @GetMapping(value = "{id}/bg-image", produces = MediaType.IMAGE_JPEG_VALUE)
    FileSystemResource downloadImage(@RequestBody Board board) throws Exception {
        return fileSystemService.find(board.getBgImgPath());
    }
}
