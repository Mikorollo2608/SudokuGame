package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals("SudokuField{value=0}", sudokuField.toString());

        SudokuField sudokuField1 = new SudokuField();
        sudokuField1.setFieldValue(8);

        assertEquals("SudokuField{value=8}",sudokuField1.toString());
    }

    @Test
    void equalsTest() {
        SudokuField sudokuField = new SudokuField();
        SudokuField sudokuField1 = new SudokuField();

        sudokuField.setFieldValue(1);
        sudokuField1.setFieldValue(1);


        assertTrue(sudokuField.equals(sudokuField));
        assertTrue(sudokuField.equals(sudokuField1));
        assertFalse(sudokuField.equals(null));

        sudokuField1.setFieldValue(2);
        assertFalse(sudokuField.equals(sudokuField1));
    }

    @Test
    void hashCodeTest() {
        SudokuField sudokuField = new SudokuField();
        SudokuField sudokuField1 = new SudokuField();

        sudokuField.setFieldValue(1);
        sudokuField1.setFieldValue(1);

        int hashCode = sudokuField.hashCode();

        assertEquals(sudokuField.hashCode(), hashCode);
        assertEquals(sudokuField.hashCode(), sudokuField1.hashCode());

        sudokuField.setFieldValue(2);

        assertNotEquals(sudokuField.hashCode(), hashCode);
    }
}