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

            //try fitting numbers one by one from numbers array
            int i = startingIndex;
            for (; i < 9; i++) {

                boolean isValid = true;
                //check row
                for (int j = 0; j < 9; j++) {
                    if (numbers[i] == board.get(row, j)) {
                        isValid = false;
                        break;
                    }
                }

                //check column
                for (int j = 0; j < 9; j++) {
                    if (numbers[i] == board.get(j, col)) {
                        isValid = false;
                        break;
                    }
                }

                //check box
                external:
                for (int j = row - row % 3; j < (row - row % 3) + 3; j++) {
                    for (int k = col - col % 3; k < (col - col % 3) + 3; k++) {
                        if (numbers[i] == board.get(j, k)) {
                            isValid = false;
                            break external;
                        }
                    }
                }

                //if the numbers fits set it on the board and move to the next square
                if (isValid) {

                    //add current cell to the changes stack
                    changesStack.push(row);
                    changesStack.push(col);
                    changesStack.push(i);
                    startingIndex = 0;
                    board.set(row, col, numbers[i]);
                    col++;
                    if (col == 9) {
                        row++;
                        col = 0;
                    }

                    break;
                }
            }
            //if you reached the end of the loop, then none of the tried numbers fit
            //move to the previously changed cell
            if (i == 9) {
                back = true;
                board.set(row, col, 0);
                //take previous cell information from changesStack
                startingIndex = changesStack.pop();
                col = changesStack.pop();
                row = changesStack.pop();

            }
        }
    }
}
