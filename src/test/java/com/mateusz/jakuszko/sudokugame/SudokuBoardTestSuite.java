package com.mateusz.jakuszko.sudokugame;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SudokuBoardTestSuite {
    @Before
    public void setLastSudokuElement() {
        SudokuElement.setLastElement(1);
    }

    @Test
    public void createRowsTest() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();
        SudokuRow[] sudokuRows = sudokuBoard.getSudokuRows();
        //When
        int numberOfSudokuRows = sudokuRows.length;
        List<Integer> listOfFirstRowElementsIds = new ArrayList<>();
        List<Integer> expectedListOfFirstRowElementsIds = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            expectedListOfFirstRowElementsIds.add(i+1);
            listOfFirstRowElementsIds.add(sudokuRows[0].getRow().get(i).getId());
        }
        //Then
        Assert.assertEquals(9, numberOfSudokuRows);
        Assert.assertEquals(expectedListOfFirstRowElementsIds, listOfFirstRowElementsIds);
    }

    @Test
    public void createSudokuBlockTest() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();
        SudokuBlock[] sudokuBlocks = sudokuBoard.getSudokuBlocks();
        List<SudokuElement> sudokuBlock = sudokuBlocks[1].getBlock();
        //When
        int numberOfSudokuBlocks = sudokuBlocks.length;
        int sizeOfBlock = sudokuBlock.size();

        List<SudokuElement> expectedSetOfElements = new ArrayList<>();
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[0].getRow().get(3));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[0].getRow().get(4));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[0].getRow().get(5));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[1].getRow().get(3));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[1].getRow().get(4));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[1].getRow().get(5));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[2].getRow().get(3));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[2].getRow().get(4));
        expectedSetOfElements.add(sudokuBoard.getSudokuRows()[2].getRow().get(5));

        //Then
        Assert.assertEquals(9, numberOfSudokuBlocks);
        Assert.assertEquals(9, sizeOfBlock);
        Assert.assertTrue(sudokuBlock.containsAll(expectedSetOfElements));
    }

    @Test
    public void createSudokuColumnsTest() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();
        //When
        int numberOfColumns = sudokuBoard.getSudokuColumns().length;
        int firstIdOfElement = sudokuBoard.getSudokuColumns()[5].getColumn().get(6).getId();
        int secondIdOfElement = sudokuBoard.getSudokuColumns()[0].getColumn().get(4).getId();
        int thirdIdOfElement = sudokuBoard.getSudokuColumns()[8].getColumn().get(6).getId();
        int fourthIdOfElement = sudokuBoard.getSudokuColumns()[7].getColumn().get(1).getId();
        int fifthIdOfElement = sudokuBoard.getSudokuColumns()[1].getColumn().get(1).getId();
        //Then
        Assert.assertEquals(9, numberOfColumns);
        Assert.assertEquals(60, firstIdOfElement);
        Assert.assertEquals(37, secondIdOfElement);
        Assert.assertEquals(63, thirdIdOfElement);
        Assert.assertEquals(17, fourthIdOfElement);
        Assert.assertEquals(11, fifthIdOfElement);
    }

    @Test
    public void shallowCloneTest() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();
        //When
        sudokuBoard.getSudokuRows()[0].getRow().get(1).setFieldOfElement(5);
        sudokuBoard.getSudokuRows()[5].getRow().get(4).setFieldOfElement(3);
        sudokuBoard.getSudokuRows()[7].getRow().get(3).setFieldOfElement(1);

        SudokuBoard clonedSudokuBoard = null;
        try {
            clonedSudokuBoard = sudokuBoard.shallowCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert clonedSudokuBoard != null;
        clonedSudokuBoard.getSudokuRows()[0].getRow().get(1).setFieldOfElement(2);
        clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).setFieldOfElement(8);
        clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).setFieldOfElement(4);

        //Then
        Assert.assertNotEquals(5, sudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertNotEquals(3, sudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertNotEquals(1, sudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
        Assert.assertEquals(2, sudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertEquals(8, sudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertEquals(4, sudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
        Assert.assertEquals(2, clonedSudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertEquals(8, clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertEquals(4, clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
        Assert.assertNotEquals(5, clonedSudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertNotEquals(3, clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertNotEquals(1, clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
    }

    @Test
    public void deepCloneTest() {
        //Given
        SudokuBoard sudokuBoard = new SudokuBoard();
        //When
        sudokuBoard.getSudokuRows()[0].getRow().get(1).setFieldOfElement(5);
        sudokuBoard.getSudokuRows()[5].getRow().get(4).setFieldOfElement(3);
        sudokuBoard.getSudokuRows()[7].getRow().get(3).setFieldOfElement(1);

        SudokuBoard clonedSudokuBoard = null;
        try {
            clonedSudokuBoard = sudokuBoard.deepCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assert clonedSudokuBoard != null;
        clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).setFieldOfElement(8);
        clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).setFieldOfElement(4);

        int sizeOfClonedBoard = clonedSudokuBoard.getSudokuRows().length * clonedSudokuBoard.getSudokuRows()[1].getRow().size();
        System.out.println(sudokuBoard);
        System.out.println(clonedSudokuBoard);
        //Then
        Assert.assertEquals(81, sizeOfClonedBoard);
        Assert.assertNotSame(clonedSudokuBoard.getSudokuRows()[0].getRow(), sudokuBoard.getSudokuRows()[0].getRow());
        Assert.assertNotSame(clonedSudokuBoard.getSudokuColumns()[0].getColumn(), sudokuBoard.getSudokuColumns()[0].getColumn());
        Assert.assertNotSame(clonedSudokuBoard.getSudokuBlocks()[0].getBlock(), sudokuBoard.getSudokuBlocks()[0].getBlock());
        Assert.assertNotSame(clonedSudokuBoard.getSudokuRows()[0].getRow().get(1), sudokuBoard.getSudokuRows()[0].getRow().get(1));
        Assert.assertEquals(5, sudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertEquals(3, sudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertEquals(1, sudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
        Assert.assertEquals(5, clonedSudokuBoard.getSudokuRows()[0].getRow().get(1).getFieldOfElement());
        Assert.assertEquals(8, clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertEquals(4, clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
        Assert.assertNotEquals(3, clonedSudokuBoard.getSudokuRows()[5].getRow().get(4).getFieldOfElement());
        Assert.assertNotEquals(1, clonedSudokuBoard.getSudokuRows()[7].getRow().get(3).getFieldOfElement());
    }

}
