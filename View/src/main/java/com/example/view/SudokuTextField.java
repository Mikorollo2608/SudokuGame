package com.example.view;

import javafx.scene.control.TextField;

public class SudokuTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[1-9]]?")) {
            super.replaceText(start, end, text);
        }
    }
}