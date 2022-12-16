package org.example;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements Serializable, Cloneable {
    private List<SudokuField> board = Arrays.asList(new SudokuField[81]);

    private SudokuSolver sudokuSolver;

    private PropertyChangeSupport support;

    public SudokuBoard(SudokuSolver newSudokuSolver) {
        sudokuSolver = newSudokuSolver;

        //initialize the board with 0
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(0);
        }

        support = new PropertyChangeSupport(this);
    }

    /**
     * Getter to a single cell on the board.
     *
     * @param row Row of the board.
     * @param col Column of the board.
     * @return Asked for cell
     */
    public int get(int row, int col) {
        return board.get(row * 9 + col).getFieldValue();
    }

    public void set(int row, int col, int val) {
        int tmp = board.get(row * 9 + col).getFieldValue();
        board.get(row * 9 + col).setFieldValue(val);
        support.fireIndexedPropertyChange("sudokuBoard",row * 9 + col,tmp, val);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public SudokuRow getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.setContents(board.get(row * 9 + i), i);
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int column) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setContents(board.get(i * 9 + column), i);
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int row, int column) {
        SudokuBox sudokuBox = new SudokuBox();
        int index = 0;
        for (int i = row - row % 3; i < (row - row % 3) + 3; i++) {
            for (int j = column - column % 3; j < (column - column % 3) + 3; j++) {
                sudokuBox.setContents(board.get(i * 9 + j), index);
                index++;
            }
        }
        return sudokuBox;
    }

    boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(3 * i, 3 * j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String temp = "\n";
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                temp = temp.concat(Integer.toString(get(j, k)));
                temp = temp.concat(" ");
                if (k == 2 || k == 5) {
                    temp = temp.concat("| ");
                }
            }
            temp = temp.concat("\n");
            if (j == 2 || j == 5) {
                temp = temp.concat("---------------------\n");
            }
        }
        return MoreObjects.toStringHelper(this)
                .addValue(temp)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuBoard that)) {
            return false;
        }
        return Objects.equal(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board, sudokuSolver);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        SudokuBoard sudokuBoardClone = (SudokuBoard) super.clone();
        sudokuBoardClone.board = Arrays.asList(new SudokuField[81]);

        for (int i = 0; i < 81; i++) {
            sudokuBoardClone.board.set(i, new SudokuField());
            sudokuBoardClone.board.get(i).setFieldValue(0);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoardClone.set(i, j, this.get(i, j));
            }
        }
        return sudokuBoardClone;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
