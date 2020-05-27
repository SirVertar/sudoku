package com.mateusz.jakuszko.sudokugame;

public class SudokuGame {

    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard();

        GameController gameController = new GameController(sudokuBoard);
        gameController.startGame();
    }
}
