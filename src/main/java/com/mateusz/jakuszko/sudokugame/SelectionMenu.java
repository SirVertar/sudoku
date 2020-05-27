package com.mateusz.jakuszko.sudokugame;

public enum SelectionMenu {
    RESOLVE_SUDOKU(0, "Resolve sudoku"),
    MOVE(1, "Make move"),
    EXIT(2, "Exit sudoku without resolving");

    private int option;
    private String optionExplanation;

    SelectionMenu(int option, String optionExplanation) {
        this.option = option;
        this.optionExplanation = optionExplanation;
    }

    public int getOption() {
        return option;
    }

    public String getOptionExplanation() {
        return optionExplanation;
    }


}

