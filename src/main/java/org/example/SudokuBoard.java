package org.example;

import java.util.Random;

public class SudokuBoard {
    private int[][] board = new int[9][9];

    /** Getter to a single cell on the board.
     *
     * @param row   Row of the board.
     * @param col   Column of the board.
     * @return      Asked for cell
     */
    public int get(int row, int col) {
        return board[row][col];
    }

    public void set(int row, int col, int val) {
        board[row][col] = val;
    }

    public void fillBoard() {
        Random random = new Random();

        //fill the board with 0
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }

        //generate first row
        for (int i = 0; i < 9; i++) {
            while (board[0][i] == 0) {
                int temp = random.nextInt(9) + 1;
                int j = 0;
                for (; j < i; j++) {
                    if (temp == board[0][j]) {
                        break;
                    }
                }
                if (j == i) {
                    board[0][i] = temp;
                }
            }
        }

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

        //starting from 2nd row
        int row = 1;
        int col = 0;

        //try to fit numbers until you reach out of the board
        while (row != 9) {

            //find first untried number in array of numbers
            int startingIndex = 0;
            for (int i = 0; i < 9; i++) {
                if (board[row][col] == numbers[i]) {
                    startingIndex = i + 1;
                    break;
                }
            }

            //try fitting numbers
            int i = startingIndex;
            for (; i < 9; i++) {

                boolean isValid = true;
                //check row
                for (int j = 0; j < 9; j++) {
                    if (numbers[i] == board[row][j]) {
                        isValid = false;
                        break;
                    }
                }

                //check column
                for (int j = 0; j < 9; j++) {
                    if (numbers[i] == board[j][col]) {
                        isValid = false;
                        break;
                    }
                }

                //check box
                external:
                for (int j = row - row % 3; j < (row - row % 3) + 3; j++) {
                    for (int k = col - col % 3; k < (col - col % 3) + 3; k++) {
                        if (numbers[i] == board[j][k]) {
                            isValid = false;
                            break external;
                        }
                    }
                }

                //if the numbers fits set it on the board and move to the next square
                if (isValid) {
                    board[row][col] = numbers[i];
                    col++;
                    if (col == 9) {
                        row++;
                        col = 0;
                    }
                    break;
                }
            }
            //if you reached the end of the loop, then none of the tried numbers fit
            //move one square back
            if (i == 9) {
                board[row][col] = 0;
                col--;
                if (col == -1) {
                    row--;
                    col = 8;
                }
            }
        }
    }
}
