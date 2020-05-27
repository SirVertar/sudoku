package com.mateusz.jakuszko.sudokugame;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn {
    private List<SudokuElement> column = new ArrayList<>();;

    public SudokuColumn() {
    }

    public boolean isPossibleNumberInSomeOfElementsIFieldnColumn(SudokuBoard sudokuBoard, SudokuElement sudokuElement, int possibleNumber) {
        SudokuColumn sudokuColumn = sudokuBoard.getSudokuColumns()[sudokuElement.getColumnIndex()];
        for (SudokuElement sudokuElementInColumn : sudokuColumn.getColumn()) {
            if (sudokuElementInColumn.getFieldOfElement() == possibleNumber) {
                return true;
            }
        }
        return false;
    }

    public boolean isPossibleNumberInOtherElementsPossibleNumbersArrayInColumn(SudokuBoard sudokuBoard, SudokuElement sudokuElement, int possibleNumber) {
        int quantityOfPossibleNumbersInOtherElements = -1;
        SudokuColumn sudokuColumn = sudokuBoard.getSudokuColumns()[sudokuElement.getColumnIndex()];
        for (SudokuElement sudokuElementInColumn : sudokuColumn.getColumn()) {
            if (sudokuElementInColumn.getPossibleNumbers().contains(possibleNumber)) {
                quantityOfPossibleNumbersInOtherElements++;
            }
            if (quantityOfPossibleNumbersInOtherElements == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isRepeatingFieldsOfElementsInColumn() {
        List<Integer> temporaryArray = new ArrayList<>();
        for (SudokuElement sudokuElement: column) {
            if (sudokuElement.getFieldOfElement() != -1) {
                if (temporaryArray.contains(sudokuElement.getFieldOfElement())) {
                    return true;
                }
                temporaryArray.add(sudokuElement.getFieldOfElement());
            }
        }
        return false;
    }

    public List<SudokuElement> getColumn() {
        return column;
    }
}
