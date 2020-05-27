package com.mateusz.jakuszko.sudokugame;

public class BacktrackData {
    private SudokuBoard sudokuBoard;
    private int rowNumberOfGuessedElement;
    private int columnNumberOfGuessedElement;
    private int guessedValueOfElement;

    public BacktrackData(SudokuBoard sudokuBoard, int rowNumberOfGuessedElement, int columnNumberOfGuessedElement, int guessedValueOfElement) {
        this.sudokuBoard = sudokuBoard;
        this.rowNumberOfGuessedElement = rowNumberOfGuessedElement;
        this.columnNumberOfGuessedElement = columnNumberOfGuessedElement;
        this.guessedValueOfElement = guessedValueOfElement;
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public int getRowNumberOfGuessedElement() {
        return rowNumberOfGuessedElement;
    }

    public int getColumnNumberOfGuessedElement() {
        return columnNumberOfGuessedElement;
    }

    public int getGuessedValueOfElement() {
        return guessedValueOfElement;
    }
}
