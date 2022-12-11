package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Difficulty {
    EASY(18), MEDIUM(36), HARD(54);

    private final int numberOfFieldsToRemove;

    Difficulty(int number) {
        numberOfFieldsToRemove = number;
    }

    public void removeFields(SudokuBoard sudokuBoard) {
        List<Integer> fieldsToRemove = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            fieldsToRemove.add(i);
        }
        Collections.shuffle(fieldsToRemove);
        fieldsToRemove = fieldsToRemove.subList(0,numberOfFieldsToRemove);
        for (Integer fieldToRemove : fieldsToRemove) {
            sudokuBoard.set(fieldToRemove / 9, fieldToRemove % 9, 0);
        }
    }
}
