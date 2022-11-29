package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final String path;

    FileSudokuBoardDao(String path) {
        this.path = path;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (FileInputStream file = new FileInputStream(new File(path))) {
            ObjectInputStream inObj = new ObjectInputStream(file);
            sudokuBoard = (SudokuBoard) inObj.readObject();

        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (Exception e) {
            System.out.println("Other Exception!");
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream file = new FileOutputStream(new File(path))) {
            ObjectOutputStream outObj = new ObjectOutputStream(file);
            outObj.writeObject(obj);
        } catch (IOException e) {
            System.out.println("IOException!");
        }
    }

    @Override
    public void close() {
    }
}
