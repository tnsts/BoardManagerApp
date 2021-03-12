package com.example.boardmanager;

import com.example.boardmanager.domain.Board;
import com.example.boardmanager.domain.Column;
import com.example.boardmanager.domain.Task;
import com.example.boardmanager.repositories.BoardRepository;
import com.example.boardmanager.repositories.ColumnRepository;
import com.example.boardmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class DbDataGenerator {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private TaskRepository taskRepository;

    private final String[] randomCreatures = {
            "Alligator", "Anteater", "Beaver", "Cheetah", "Capybara",
            "Buffalo", "Chinchilla", "Duck", "Fox", "Ferret", "Frog",
            "Leopard", "Koala", "Kangaroo", "Kraken", "Otter", "Raccoon",
            "Wombat", "Turtle", "Skunk", "Squirrel", "Rhino"
    };

    private final String[] randomCharacteristics = {
            "Honest", "Strict", "Forceful", "Thoughtful", "Intelligent",
            "Clownish", "Eccentric", "Friendly", "Interesting", "Anxious", "Gentle",
            "Responsible", "Crafty", "Imaginative", "Pleasant", "Original", "Polite",
            "Fearful", "Absent-Minded", "Sarcastic", "Patient", "Spirited"
    };

    public ArrayList<Board> generateBoards(int amount) {
        ArrayList<Board> boards = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            boards.add(boardRepository.save(new Board(
                    randomCharacteristics[random.nextInt(randomCharacteristics.length)] + " " +
                            randomCreatures[random.nextInt(randomCreatures.length)]

            )));
        }
        return boards;
    }

    public ArrayList<Column> generateColumns(int amount, ArrayList<Board> boards) {
        ArrayList<Column> columns = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            columns.add(columnRepository.save(
                    new Column(randomCharacteristics[random.nextInt(randomCharacteristics.length)] + " " +
                            randomCreatures[random.nextInt(randomCreatures.length)],
                            boards.get(random.nextInt(boards.size())))
            ));
        }
        return columns;
    }

    public ArrayList<Task> generateTasks(int amount, ArrayList<Column> columns) {
        ArrayList<Task> tasks = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            tasks.add(taskRepository.save(
                    new Task(randomCharacteristics[random.nextInt(randomCharacteristics.length)] + " " +
                            randomCreatures[random.nextInt(randomCreatures.length)],
                            randomCharacteristics[random.nextInt(randomCharacteristics.length)] + " " +
                                    randomCreatures[random.nextInt(randomCreatures.length)],
                            columns.get(random.nextInt(columns.size())))
            ));
        }
        return tasks;
    }

}
