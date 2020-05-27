package com.mateusz.jakuszko.sudokugame.io;

import com.mateusz.jakuszko.sudokugame.SelectionMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputOutput {
    public static Scanner scanner = new Scanner(System.in);

    static public void printOption() {
        System.out.println("\nChoose on of the option below :\n");
        for (SelectionMenu option : SelectionMenu.values()) {
            System.out.println(option.getOption() + " - " + option.getOptionExplanation());
        }
    }

    static public int intInput(int start, int end) {
        boolean error = true;
        int choose = -1;
        do {
            try {
                choose = scanner.nextInt();
                if (choose > end || choose < start) {
                    System.out.println("You didn't give right int -> [ " + start + "-" + end + " ] :). Try again");
                } else {
                    error = false;
                }
            } catch (InputMismatchException exception) {
                scanner.nextLine();
                System.out.println("You didn't give right int -> [ " + start + "-" + end + " ] :). Try again");
            }
        } while (error);
        return choose;
    }

    static public int[] intChoiceInput() {
        String[] stringChoiceTable = stringChoiceInput();
        int[] intChoiceTable = new int[stringChoiceTable.length];
        for (int i = 0; i < stringChoiceTable.length; i++) {
            intChoiceTable[i] = Integer.parseInt(stringChoiceTable[i]);
        }
        return intChoiceTable;
    }

    static public String[] stringChoiceInput() {
        String choose = "";
        boolean error = false;
        System.out.println("Make your choice, remember, you need to give your choice in right pattern: \n" +
                "[  row,column,number,row,column,number,row,column,number...  ]");

        do {
            choose = scanner.next();
            String regex = "(\\d[ |,]\\d[ |,]\\d[ *|,]){1,81}";
            if (choose.matches(regex)) {
                error = true;
                return choose.substring(0, choose.length()-1).split(",");
            } else {
                choose += ",";
                if (choose.matches(regex)) {
                    error = true;
                    return choose.substring(0, choose.length()-1).split(",");
                }
            }
            System.out.println("Something wrong with your choice... Try make a choice according to a pattern above");
        } while (!error);
        return choose.split(",");
    }

    static public int[] setElementField() {
        System.out.println("Which row?");
        int row = intInput(1, 9);
        System.out.println("Which column?");
        int column = intInput(1, 9);
        System.out.println("Which number for element?");
        int elementField = intInput(1, 9);
        return new int[]{row, column, elementField};
    }


}
