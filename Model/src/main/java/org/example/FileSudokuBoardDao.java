package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private FileInputStream fileIn;
    private FileOutputStream fileOut;

    FileSudokuBoardDao(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileIn = new FileInputStream(file);
            fileOut = new FileOutputStream(file);
        } catch (IOException e) {
            System.out.println("Constructing IOException!");
        }
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (ObjectInputStream inObj = new ObjectInputStream(fileIn)) {
            sudokuBoard = (SudokuBoard) inObj.readObject();

        } catch (IOException e) {
            System.out.println("Reading IOException!");
        } catch (Exception e) {
            System.out.println("Reading other Exception!");
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        try (ObjectOutputStream outObj = new ObjectOutputStream(fileOut)) {
            outObj.writeObject(obj);
        } catch (Exception e) {
            System.out.println("Write IOException!");
        }
    }

    @Override
    public void close() {
        try {
            fileIn.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Closing IOException!");
        }
    }
}
