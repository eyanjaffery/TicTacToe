package org.example;
import java.util.Random;
import java.util.Scanner;

/**
 * This class implements a simple Tic-Tac-Toe game with support for player vs. computer mode.
 */
public class TicTacToe {
    private static char[][] board; // The Tic-Tac-Toe board
    private static int gridRows; // Number of rows in the grid
    private static int gridCols; // Number of columns in the grid
    private static char currentPlayer = 'X'; // Current player ('X' or 'O')
    private static Random random = new Random(); // Random number generator for computer moves

    /**
     * The main method that starts the Tic-Tac-Toe game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        // Enter grid size
        System.out.print("Enter the grid Rows: ");
        gridRows = scanner.nextInt();
        System.out.print("Enter the grid Columns: ");
        gridCols = scanner.nextInt();

        board = new char[gridRows][gridCols];

        initializeBoard(gridRows, gridCols);
        char winner = '\0';

        // Set whether the player is playing against the computer
        boolean vsComputer = true;

        while (winner == '\0') {
            displayBoard(gridRows, gridCols);
            if (currentPlayer == 'X' || !vsComputer) {
                getPlayerMove(scanner);
            } else {
                // Computer's turn
                System.out.println("Computer's turn (O):");
                makeComputerMove();
            }
            winner = checkWinner();
            switchPlayer();
        }

        displayBoard(gridRows, gridCols);
        if (winner == 'D') {
            System.out.println("It's a draw!");
        } else if (vsComputer && winner == 'O') {
            System.out.println("Computer wins!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }

    /**
     * Initializes the Tic-Tac-Toe board with empty spaces.
     *
     * @param x Number of rows.
     * @param y Number of columns.
     */
    private static void initializeBoard(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Displays the current state of the Tic-Tac-Toe board.
     *
     * @param x Number of rows.
     * @param y Number of columns.
     */
    private static void displayBoard(int x, int y) {
        // Display column numbers
        System.out.print("  ");
        for (int i = 0; i < y; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();

        for (int i = 0; i < x; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < y; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
            if (i < x - 1) {
                System.out.print("  ");
                for (int k = 0; k < y; k++) {
                    System.out.print("----");
                }
                System.out.println("-");
            }
        }
        System.out.println();
    }

    /**
     * Gets the player's move from the user.
     *
     * @param scanner Scanner object for input.
     */
    private static void getPlayerMove(Scanner scanner) {
        int row, col;
        do {
            System.out.print("Player " + currentPlayer + ", enter row (0, 1, or 2): ");
            row = scanner.nextInt();
            System.out.print("Player " + currentPlayer + ", enter column (0, 1, or 2): ");
            col = scanner.nextInt();
        } while (!isValidMove(row, col));

        board[row][col] = currentPlayer;
    }

    /**
     * Makes a move for the computer player, prioritizing winning moves if available.
     */
    private static void makeComputerMove() {
        // Try to find a winning move first
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (isValidMove(i, j)) {
                    // Simulate making the move
                    board[i][j] = currentPlayer;
                    if (checkWinner() == 'O') {
                        // Found a winning move, make this move
                        return;
                    } else {
                        // Undo the move if it doesn't lead to a win
                        board[i][j] = ' ';
                    }
                }
            }
        }

        // If no winning move, make a random valid move
        int row, col;
        do {
            row = random.nextInt(gridRows);
            col = random.nextInt(gridCols);
        } while (!isValidMove(row, col));

        board[row][col] = currentPlayer;
    }


    /**
     * Checks if a move is valid.
     *
     * @param row Row index.
     * @param col Column index.
     * @return true if the move is valid, false otherwise.
     */
    private static boolean isValidMove(int row, int col) {
        if (row < 0 || row >= gridRows || col < 0 || col >= gridCols || board[row][col] != ' ') {
            return false;
        }
        return true;
    }

    /**
     * Checks if there is a winner or a draw.
     *
     * @return 'X' if player X wins, 'O' if player O wins, 'D' for a draw, or '\0' if no winner yet.
     */
    private static char checkWinner() {
        int size = board.length; // Board is a square, so rows = cols

        // Check rows
        for (int i = 0; i < size; i++) {
            boolean rowWin = true;
            for (int j = 0; j < size; j++) {
                if (board[i][j] != currentPlayer) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                return currentPlayer;
            }
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            boolean colWin = true;
            for (int i = 0; i < size; i++) {
                if (board[i][j] != currentPlayer) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                return currentPlayer;
            }
        }

        // Check diagonals
        boolean diag1Win = true; // Top-left to bottom-right diagonal
        boolean diag2Win = true; // Top-right to bottom-left diagonal
        for (int i = 0; i < size; i++) {
            if (board[i][i] != currentPlayer) {
                diag1Win = false;
            }
            if (board[i][size - 1 - i] != currentPlayer) {
                diag2Win = false;
            }
        }
        if (diag1Win || diag2Win) {
            return currentPlayer;
        }

        // Check for a draw
        boolean isBoardFull = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    isBoardFull = false;
                    break;
                }
            }
        }

        return isBoardFull ? 'D' : '\0'; // 'D' for Draw, '\0' for no winner yet
    }

    /**
     * Switches the current player.
     */
    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}
