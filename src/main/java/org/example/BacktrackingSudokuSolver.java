package org.example;

import java.util.Random;
import java.util.Stack;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public void solve(SudokuBoard board) {

        Random random = new Random();

        //shuffle the priorities of numbers
        int[] numbers = new int[9];
        for (int i = 0; i < 9; i++) {
            while (numbers[i] == 0) {
                int temp = random.nextInt(9) + 1;
                int j = 0;
                for (; j < i; j++) {
                    if (temp == numbers[j]) {
                        break;
                    }
                }
                if (j == i) {
                    numbers[i] = temp;
                }
            }
        }

        int row = 0;
        int col = 0;

        //holds row,column and index of previously tested cell
        //index being index of tested number from an array numbers
        Stack<Integer> changesStack = new Stack<>();
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
                board.set(row, col, numbers[i]);

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
                board.set(row, col, numbers[i - 1]);
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
}
