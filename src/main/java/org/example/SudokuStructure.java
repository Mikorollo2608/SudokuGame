package org.example;


public class SudokuStructure {
    private SudokuField[] contents = new SudokuField[9];

    public void setContents(SudokuField field, int index) {
        contents[index] = new SudokuField();
        contents[index].setFieldValue(field.getFieldValue());
    }

    public SudokuField get(int index) {
        return contents[index];
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (contents[j].getFieldValue() == contents[i].getFieldValue()
                        && contents[j].getFieldValue() != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
