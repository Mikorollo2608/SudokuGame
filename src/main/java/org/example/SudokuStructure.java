package org.example;

import java.util.Arrays;
import java.util.List;

public class SudokuStructure {
    private List<SudokuField> contents = Arrays.asList(new SudokuField[9]);

    public void setContents(SudokuField field, int index) {
        contents.set(index, new SudokuField());
        contents.get(index).setFieldValue(field.getFieldValue());
    }

    public SudokuField get(int index) {
        return contents.get(index);
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (contents.get(j).getFieldValue() == contents.get(i).getFieldValue()
                        && contents.get(j).getFieldValue() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String temp = "";
        int counter = 0;
        for (SudokuField sudokuField : contents) {
            temp += "[";
            temp += Integer.toString(counter);
            temp += "] ";
            if (sudokuField == null) {
                temp += "null ";
            } else {
                temp += sudokuField.toString();
                temp += " ";
            }
            counter++;
        }
        return temp;
    }
}
