package com.example.boardmanager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "columns")
public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @JsonBackReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @JsonManagedReference
    @OneToMany(mappedBy = "column", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Task> tasks;

    public Column() {
    }

    public Column(String title) {
        this.title = title;
    }

    public Column(String title, Board board) {
        this.title = title;
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Board getBoard() {
        return board;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
