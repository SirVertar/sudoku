package com.mateusz.jakuszko.sudokugame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuBlock {
    private List<SudokuElement> block = new ArrayList<>();

    public boolean isPossibleNumberInSomeOfElementsFieldInBlock(SudokuBoard sudokuBoard, SudokuElement sudokuElement, int possibleNumber) {
        SudokuBlock sudokuBlock = sudokuBoard.getSudokuBlocks()[sudokuElement.getBlockIndex()];
        for (SudokuElement sudokuElementInBlock : sudokuBlock.getBlock()) {
            if (sudokuElementInBlock.getFieldOfElement() == possibleNumber) {
                return true;
            }
        }
        return false;
    }

    public boolean isPossibleNumberInOtherElementsPossibleNumbersArrayInBlock(SudokuBoard sudokuBoard, SudokuElement sudokuElement, int possibleNumber) {
        int quantityOfPossibleNumbersInOtherElements = -1;
        SudokuBlock sudokuBlock = sudokuBoard.getSudokuBlocks()[sudokuElement.getBlockIndex()];
        for (SudokuElement sudokuElementInBlock : sudokuBlock.getBlock()) {
            if (sudokuElementInBlock.getPossibleNumbers().contains(possibleNumber)) {
                quantityOfPossibleNumbersInOtherElements++;
            }
            if (quantityOfPossibleNumbersInOtherElements == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isRepeatingFieldsOfElementsInBlock() {
        List<Integer> temporaryArray = new ArrayList<>();
        for (SudokuElement sudokuElement: block) {
            if (sudokuElement.getFieldOfElement() != -1) {
                if (temporaryArray.contains(sudokuElement.getFieldOfElement())) {
                    return true;
                }
                temporaryArray.add(sudokuElement.getFieldOfElement());
            }
        }
        return false;
    }

    public List<SudokuElement> getBlock() {
        return block;
    }
}
