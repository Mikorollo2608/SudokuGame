package org.example;

import com.google.common.base.MoreObjects;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {

        //shuffle the priorities of numbers
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);

        int row = 0;
        int col = 0;

        //holds row,column and index of previously tested cell
        //index being index of tested number from an array numbers
        Deque<Integer> changesStack = new ArrayDeque<>();
        //flag indicating that the solver is taking a step back
        boolean back = false;
        //index from which start trying numbers
        int startingIndex = 0;

        //try to fit numbers until you reach out of the board
        while (row != 9) {

            //if the cell is not empty and you're not taking a step back go to the next cell
            if (board.get(row, col) != 0 && !back) {
                col++;
                if (col == 9) {
                    row++;
                    col = 0;
                }
                continue;
            }

            //reset back flag
            back = false;

            boolean isValid = false;

            //try fitting numbers one by one from numbers array
            //as long as you won't find one that fits
            int i = startingIndex;
            for (; i < 9 && !isValid; i++) {

                //assume that number fits
                isValid = true;
                //place number in the board and check if it fits
                board.set(row, col, numbers.get(i));

                //check row
                //if number doesn't fit reset the cell and try next number
                if (!board.getRow(row).verify()) {
                    board.set(row, col, 0);
                    isValid = false;
                    continue;
                }

                if (!board.getColumn(col).verify()) {
                    board.set(row, col, 0);
                    isValid = false;
                    continue;
                }

                if (!board.getBox(row, col).verify()) {
                    board.set(row, col, 0);
                    isValid = false;
                }
            }

            //if number fits move to the next cell
            if (isValid) {

                //add current cell to the changes stack
                changesStack.push(row);
                changesStack.push(col);
                changesStack.push(i - 1);
                startingIndex = 0;
                board.set(row, col, numbers.get(i - 1));
                col++;
                if (col == 9) {
                    row++;
                    col = 0;
                }
            } else {
                //if you reached the end of the loop, then none of the tried numbers fit
                //move to the previously changed cell
                back = true;
                board.set(row, col, 0);
                //take previous cell information from changesStack
                startingIndex = changesStack.pop() + 1;
                col = changesStack.pop();
                row = changesStack.pop();

            }
        }
    }

    @Override
    public String toString() {
        String name = "BacktrackingSudokuSolver";
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}
