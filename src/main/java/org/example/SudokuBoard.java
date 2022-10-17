package org.example;

import java.util.Random;

public class SudokuBoard {
    private int[][] board = new int[9][9];

    public int getSquare(int row, int col) {
        return board[row][col];
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


    }

}
