package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SudokuFieldTest {

    @Test
    void setFieldValue() {
        SudokuField sudokuField = new SudokuField();

        assertEquals(sudokuField.getFieldValue(),0);

        sudokuField.setFieldValue(5);

        assertEquals(sudokuField.getFieldValue(),5);

        sudokuField.setFieldValue(9);

        assertEquals(sudokuField.getFieldValue(),9);

        //Value out of range integers (0;9>
        sudokuField.setFieldValue(32);

        assertEquals(sudokuField.getFieldValue(),0);

    }

    @Test
    void toStringTest() {
        SudokuField sudokuField = new SudokuField();

        assertEquals(sudokuField.toString(),"Value: 0");

        SudokuField sudokuField1 = new SudokuField();
        sudokuField1.setFieldValue(8);

        assertEquals(sudokuField1.toString(),"Value: 8");
    }
}