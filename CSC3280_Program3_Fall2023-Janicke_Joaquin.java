// Name: Joaquin Janicke
//Date: 10/6/2023

import java.util.*;

public class Main {

    // calculate factorial of a given number we did this in class
    public static double factorialRecursive(int n) {
        if (n == 0) {//terminating condition for recursion
            return 1;
        } else {
            return n * factorialRecursive(n - 1);//recursive statement where n is multiplied by the factorial of n-1
        }
    }

    //calculate the math 
    public static double mocsMathRecursive(int n) {
        if (n == 0) {//if n is 0 return the factorial of 1
            return factorialRecursive(1);
        } else if (n == 1) {//if n is 1 return the factorial of 2
            return factorialRecursive(1) + factorialRecursive(1);
        } else {
            return factorialRecursive(n) + mocsMathRecursive(n - 1);//recursive statement where the factorial gets added
        }
    }

    // extracts input and prints result of mocsMathRecursive
    public static void mocsMath(String[] input) {
        int n = Integer.parseInt(input[1]);//save user input as n
        double result = mocsMathRecursive(n);//call the recursive method and save into a double
        System.out.printf("MocsMath: %.0f\n ", result);//formatted print for the result
    }

    // prints a line with a given number of stars
    public static void printStars(int stars) {
        for (int i = 0; i < stars; i++) {//iterate through num stars
            System.out.print("* ");//prints the star
        }
        System.out.println();//new line
    }

    // recursive method to print stars in a pattern
    public static void mocsShapeRecursive(int m, int n) {
        if (m > n) {//should never be the case but better safe than sorry
            return;
        }

        printStars(m);//print the amount of stars that m represents
        mocsShapeRecursive(m + 1, n); //recursive statment gets incremented 
        printStars(m);//print the amount of stars that m represents
    }

    // extracts input and prints shape pattern based on the inputs
    public static void mocsShape(String[] input) {
        System.out.println("MocsShape:");//add the print statement
        int m = Integer.parseInt(input[1]);//save n to a variable from user's input
        int n = Integer.parseInt(input[2]);//save m to a variable from user's input
        mocsShapeRecursive(m, n);//recursive call
    }

    // game logic to decide solvability
    public static void mocsGame(String[] input) {
        System.out.print("MocsGame:  ");//print statement
        int n = Integer.parseInt(input[1]);//save n to a variable from user's input
        boolean isSolvable = mocsGameRecursive(n);//create a boolean variable that changes depending on what is sent over
        if (isSolvable) {//if true
            System.out.println("Solvable");
        } else {//if false
            System.out.println("Not Solvable");
        }
    }

    // recursive logic for mocsGame
    public static boolean mocsGameRecursive(int n) {
        // base conditions
        if (n == 42) {
            return true;
        }
        if (n < 42) {
            return false;
        }

        //step one takes n and divides it by 2 as it sends the n/2 back to this method
        if (n % 2 == 0 && mocsGameRecursive(n / 2)) {
            return true; //return if condition is met
        }

        //step two takes n and checks to see if its divisible by 3 or 4
        if (n % 3 == 0 || n % 4 == 0) {
            int lastDigit = n % 10;//last digit
            int secondLastDigit = (n % 100) / 10;//second to last
            int amountReturned = lastDigit * secondLastDigit;//amount to be returned per the pdf

            if (amountReturned > 0 && mocsGameRecursive(n - amountReturned)) {//if the returning amount is more than 0 I have to move this into the if statement condition because I had it in 2 ifs
                return true;//return if condition is met
            }
        }

        // condition for divisible by 5
        if (n % 5 == 0) {
            return mocsGameRecursive(n - 42);//recurse again this time with the update num n
        }

        return false;//return if none of these cinditions are met
    }

    // recursive method to compress a grid
    public static void mocsCompressRecursive(int size, int threshold, int[][] grid, int topRow, int bottomRow, int leftColumn, int rightColumn) {
        // count cells and perform checks
        int totalCells = (bottomRow - topRow + 1) * (rightColumn - leftColumn + 1);//total amount of cells in a grid
        int blackCells = 0;//intialize black cells to black

        // count black cells
        for (int i = topRow; i <= bottomRow; i++) {//iterate from the top to the bottom
            for (int j = leftColumn; j <= rightColumn; j++) { //iterate from left to right
                if (grid[i][j] == 1) {//if it is a 1 and not a 0
                    blackCells++;//increment black cells
                }
            }
        }

        // perform compress actions based on the threshold
        if (blackCells * 100 / totalCells >= threshold) {//is the amount of cells more or equal or the threshold amount
            for (int i = topRow; i <= bottomRow; i++) {//iterate through the entire grid again
                for (int j = leftColumn; j <= rightColumn; j++) {
                    grid[i][j] = 1; //change value
                }
            }
            return;
        }

        // check white cells
        int whiteCells = totalCells - blackCells;//white cells is black - total
        if (whiteCells * 100 / totalCells >= threshold) {//compare the threshold
            for (int i = topRow; i <= bottomRow; i++) {//iterate through the entire grid
                for (int j = leftColumn; j <= rightColumn; j++) {
                    grid[i][j] = 0; //change value
                }
            }
            return;
        }

        // divide and recurse on quadrants
        //this part made me want to hit my head against the wall
        //check if the current grid can be further divided into quadrants
        if (bottomRow - topRow > 0 && rightColumn - leftColumn > 0) {
            // calculate the middle row
            int midRow = (topRow + bottomRow) / 2;
            // calculate the middle column
            int midCol = (leftColumn + rightColumn) / 2;
            // compress the top-left quadrant
            mocsCompressRecursive(size, threshold, grid, topRow, midRow, leftColumn, midCol);
            // compress the bottom-left quadrant
            mocsCompressRecursive(size, threshold, grid, midRow + 1, bottomRow, leftColumn, midCol);
            // compress the top-right quadrant
            mocsCompressRecursive(size, threshold, grid, topRow, midRow, midCol + 1, rightColumn);
            // compress the bottom-right quadrant
            mocsCompressRecursive(size, threshold, grid, midRow + 1, bottomRow, midCol + 1, rightColumn);
        }
    }

    // method to handle the compression process
    public static void mocsCompress(Scanner scanner, String[] input) {

        // parse input and create grid
        int size = Integer.parseInt(input[1]);
        int threshold = Integer.parseInt(input[2]);
       //decided to stray from your example of doing[size][] and then inserting the values for each row upon input from user
        int[][] grid = new int[size][size];

        // populate the grid from user input
        for (int i = 0; i < size; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < size; j++) {
                grid[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }

        // initiate compression and print results
        mocsCompressRecursive(size, threshold, grid, 0, size - 1, 0, size - 1);

        System.out.println("MocsCompress:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    // main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);//the MVP of every program
        int numCommands = scanner.nextInt();
        scanner.nextLine();

        // loop over num commands
        for (int i = 0; i < numCommands; i++) {
            String[] input = scanner.nextLine().split(" ");

            if ("MocsMath".equals(input[0])) {
                mocsMath(input);//call math
            } else if ("MocsShape".equals(input[0])) {
                mocsShape(input);//call shape
            } else if ("MocsGame".equals(input[0])) {
                mocsGame(input);//call game
            } else if ("MocsCompress".equals(input[0])) {
                mocsCompress(scanner, input);//call compress
            }
        }

        scanner.close();
    }
}
