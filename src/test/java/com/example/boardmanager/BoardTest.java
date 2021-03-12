package com.example.boardmanager;

import com.example.boardmanager.contollers.BoardController;
import com.example.boardmanager.domain.Board;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardController boardController;

    @Autowired
    private DbDataGenerator dbDataGenerator;

    private ArrayList<Board> boards;

    @BeforeAll
    public void init() {
        int n = 2;
        boards = dbDataGenerator.generateBoards(n);
        dbDataGenerator.generateTasks(n, dbDataGenerator.generateColumns(n, boards));
    }

    @Test
    public void contextLoadsTest() throws Exception {
        assertThat(boardController).isNotNull();
    }

    @Test
    public void createBoardTest() throws Exception {
        String name = "Create Board Test";
        this.mockMvc.perform(post("/api/v1/board").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void getBoardTest() throws Exception {
        Board board = boards.get(
                new Random().nextInt(boards.size())
        );

        this.mockMvc.perform(get("/api/v1/board/" + board.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(board.getName()))
                .andExpect(jsonPath("$.id").value(board.getId()))
                .andExpect(jsonPath("$.columns").isEmpty());
    }

    @Test
    public void getFullBoardTest() throws Exception {
        Board board = boards.get(
                new Random().nextInt(boards.size())
        );

        this.mockMvc.perform(get("/api/v1/board/" + board.getId() + "/full-info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(board.getName()))
                .andExpect(jsonPath("$.id").value(board.getId()));
    }

    @Test
    public void getAllBoardsTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/board/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateBoardTest() throws Exception {
        Board board = boards.get(new Random().nextInt(boards.size()));
        String name = "Update Board Test";
        board.setName(name);

        MediaType APPLICATION_JSON_UTF8 = new MediaType(
                MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8
        );


        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(board);

        mockMvc.perform(put("/api/v1/board").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void deleteBoardTest() throws Exception {
        Board board = boards.get(new Random().nextInt(boards.size()));

        mockMvc.perform(delete("/api/v1/board/" + board.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(board.getId()));
    }
}
