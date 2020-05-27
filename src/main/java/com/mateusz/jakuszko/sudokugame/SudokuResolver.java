package com.mateusz.jakuszko.sudokugame;

import java.util.*;

public class SudokuResolver {
    private SudokuBoard sudokuBoard;
    private boolean isBoardFull = false;
    private boolean isThereAnyOperation;
    private boolean isError = false;
    private Random random;

    public SudokuResolver(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        random = new Random();
    }

    public void resolveSudoku() {
        if (sudokuBoard.isThereErrorInBoard()) {
            System.out.println("There is a problem with sudoku. " +
                    "Probably there are repeating elements in rows, columns or blocks.");
            isBoardFull = true;
        }
        while (!isBoardFull) {
            isThereAnyOperation = false;
            for (int i = 0; i < sudokuBoard.getSudokuRows().length; i++) {
                SudokuRow sudokuRow = sudokuBoard.getSudokuRows()[i];
                for (SudokuElement sudokuElement : sudokuRow.getRow()) {
                    if (sudokuElement.getEmpty() == -1) {
                        Set<Integer> temporaryPossibleNumbers = new HashSet<>();
                        for (Integer possibleNumber : sudokuElement.getPossibleNumbers()) {
                            if (sudokuBoard.getSudokuRows()[sudokuElement.getRowIndex()]
                                    .isPossibleNumberInSomeOfElementsFieldInRow(sudokuRow, possibleNumber) ||
                                    sudokuBoard.getSudokuBlocks()[sudokuElement.getBlockIndex()]
                                            .isPossibleNumberInSomeOfElementsFieldInBlock(sudokuBoard, sudokuElement, possibleNumber) ||
                                    sudokuBoard.getSudokuColumns()[sudokuElement.getColumnIndex()]
                                            .isPossibleNumberInSomeOfElementsIFieldnColumn(sudokuBoard, sudokuElement, possibleNumber)) {
                                temporaryPossibleNumbers.add(possibleNumber);
                                isThereAnyOperation = true;
                            }
                            if (!(sudokuBoard.getSudokuRows()[sudokuElement.getRowIndex()]
                                    .isPossibleNumberInOtherElementsPossibleNumbersArrayInRow(sudokuRow, possibleNumber) &&
                                    sudokuBoard.getSudokuColumns()[sudokuElement.getColumnIndex()]
                                            .isPossibleNumberInOtherElementsPossibleNumbersArrayInColumn(sudokuBoard, sudokuElement, possibleNumber) &&
                                    sudokuBoard.getSudokuBlocks()[sudokuElement.getBlockIndex()]
                                            .isPossibleNumberInOtherElementsPossibleNumbersArrayInBlock(sudokuBoard, sudokuElement, possibleNumber)) &&
                                    !sudokuBoard.isThereErrorInBoard()) {
                                sudokuElement.setFieldOfElement(possibleNumber);
                                isThereAnyOperation = true;
                            } else if (sudokuElement.getPossibleNumbers().size() == 1) {
                                int sizeOfBacktrack = sudokuBoard.getBacktrack().size();
                                if (sizeOfBacktrack > 0) {
                                    loadBacktrack(sizeOfBacktrack);
                                    isThereAnyOperation = true;
                                } else {
                                    isError = true;
                                }
                            }
                        }
                        sudokuElement.getPossibleNumbers().removeAll(temporaryPossibleNumbers);
                        if (sudokuElement.getPossibleNumbers().size() == 1) {
                            for (Integer lastPossibleNumber : sudokuElement.getPossibleNumbers()) {
                                sudokuElement.setFieldOfElement(lastPossibleNumber);
                                isThereAnyOperation = true;
                            }
                        }
                    }
                }
                isBoardFull = sudokuBoard.isBoardFull();
            }
            if (isError && !isBoardFull) {
                System.out.println("There is no possibility to resolve this sudoku... Probably I need to guess again");
                System.out.println(sudokuBoard);
                isError = false;
            }

            if (isBoardFull) {
                System.out.println("Final SUDOKU: ");
                System.out.println(sudokuBoard);
            }

            if (!isThereAnyOperation && !isBoardFull) {
                List<SudokuElement> listWithEmptySudokuElements = createListWithEmptySudokuElements();
                boolean isCorrect = false;

                int randomNumberFromPossibleNumbers = -1;
                SudokuElement randomSudokuElement = null;
                int count = 0;
                do {
                    int randomEmptyElementIndex = random.nextInt(listWithEmptySudokuElements.size());
                    randomSudokuElement = listWithEmptySudokuElements.get(randomEmptyElementIndex);
                    int randomIndexOfPossibleNumberOfEmptyElement = random.nextInt(randomSudokuElement.getPossibleNumbers().size());
                    randomNumberFromPossibleNumbers = randomSudokuElement.getPossibleNumbers().get(randomIndexOfPossibleNumberOfEmptyElement);
                    randomSudokuElement.setFieldOfElement(randomNumberFromPossibleNumbers);
                    count++;
                    if (count > 10) {
                        loadBacktrack(1);
                        break;
                    }
                    if (!sudokuBoard.isThereErrorInBoard()) {
                        randomSudokuElement.setFieldOfElement(-1);
                        randomSudokuElement.setEmpty(-1);
                        isCorrect = true;
                    } else {
                        randomSudokuElement.setFieldOfElement(-1);
                        randomSudokuElement.setEmpty(-1);
                    }
                } while (!isCorrect);

                SudokuBoard clonedSudokuBoard = null;
                try {
                    clonedSudokuBoard = sudokuBoard.deepCopy();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

                BacktrackData backtrackData = new BacktrackData(clonedSudokuBoard, randomSudokuElement.getRowIndex(),
                        randomSudokuElement.getColumnIndex(), randomNumberFromPossibleNumbers);
                sudokuBoard.getBacktrack().add(backtrackData);
                randomSudokuElement.setFieldOfElement(randomNumberFromPossibleNumbers);
            }
        }
    }

    private List<SudokuElement> createListWithEmptySudokuElements() {
        List<SudokuElement> listWithEmptySudokuElements = new ArrayList<>();
        for (int i = 0; i < sudokuBoard.getSudokuBlocks().length; i++) {
            for (SudokuElement sudokuElement : sudokuBoard.getSudokuBlocks()[i].getBlock()) {
                if (sudokuElement.getPossibleNumbers().size() == 0 && sudokuElement.getEmpty() == -1) {
                    sudokuElement.setPossibleNumbers(sudokuElement.createPossibleNumbersSet());
                }
                if (sudokuElement.getEmpty() == -1) {
                    listWithEmptySudokuElements.add(sudokuElement);
                }
            }
        }
        return listWithEmptySudokuElements;
    }

    public void setSudokuBoard(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    private void loadBacktrack(int sizeOfBacktrack) {
        int rowIndexOfGuessedElement = sudokuBoard.getBacktrack().get(sizeOfBacktrack - 1)
                .getRowNumberOfGuessedElement();
        int columnIndexOfGuessedElement = sudokuBoard.getBacktrack().get(sizeOfBacktrack - 1)
                .getColumnNumberOfGuessedElement();
        int guessedNumber = sudokuBoard.getBacktrack().get(sizeOfBacktrack - 1)
                .getGuessedValueOfElement();
        SudokuElement guessedElement = sudokuBoard.getBacktrack().get(sizeOfBacktrack - 1)
                .getSudokuBoard().getSudokuRows()[rowIndexOfGuessedElement]
                .getRow().get(columnIndexOfGuessedElement);
        guessedElement.removeNumberFromPossibleNumbers(guessedNumber);
        guessedElement.setFieldOfElement(-1);
        guessedElement.setEmpty(-1);
        setSudokuBoard(sudokuBoard.getBacktrack().get(sizeOfBacktrack - 1).getSudokuBoard());
    }

    public void clearBoard() {
        isBoardFull = false;
        isError = false;
        isThereAnyOperation = false;
        sudokuBoard.setBacktrack(new ArrayList<>());
    }


}
