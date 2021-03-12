package com.example.boardmanager.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Column> columns;

    @Transient
    private String bgImgPath;

    public Board() {
    }

    public Board(String name) {
        this.name = name;
    }

    public Board(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void setBgImgPath(String bgImgPath) {
        this.bgImgPath = bgImgPath;
    }

    public String getBgImgPath() {
        return bgImgPath;
    }
}
