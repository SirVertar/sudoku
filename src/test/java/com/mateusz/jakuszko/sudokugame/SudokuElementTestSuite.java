package com.mateusz.jakuszko.sudokugame;

import org.junit.Assert;
import org.junit.Test;

public class SudokuElementTestSuite {

    @Test
    public void shallowCopyTest() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //When
        SudokuElement clonedSudokuElement = null;
        try {
            clonedSudokuElement = sudokuElement.shallowCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        sudokuElement.setEmpty(0);
        //Then
        Assert.assertNotSame(sudokuElement, clonedSudokuElement);
        Assert.assertNotEquals(sudokuElement, clonedSudokuElement);
    }

    @Test
    public void deepCopyTest() {
        //Given
        SudokuElement sudokuElement = new SudokuElement();
        //When
        SudokuElement clonedSudokuElement = null;
        try {
            clonedSudokuElement = sudokuElement.deepCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        sudokuElement.removeNumberFromPossibleNumbers(9);
        sudokuElement.removeNumberFromPossibleNumbers(8);
        //Then
        Assert.assertEquals(7, sudokuElement.getPossibleNumbers().size());
        assert clonedSudokuElement != null;
        Assert.assertEquals(9, clonedSudokuElement.getPossibleNumbers().size());
    }
}
