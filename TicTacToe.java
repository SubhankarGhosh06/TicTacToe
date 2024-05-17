import java.util.*;

class TicTacToe {
    private static final int COMPUTER = 1;
    private static final int HUMAN = 2;
    private static final int SIDE = 3;
    private static final char COMPUTERMOVE = 'O';
    private static final char HUMANMOVE = 'X';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        solveTestCase(scanner);
        scanner.close();
    }

    private static void solveTestCase(Scanner scanner) {
        char[][] board = new char[SIDE][SIDE];
        initialise(board);
        showInstructions();
        playTicTacToe(HUMAN, board);
    }

    private static void showBoard(char[][] board) {
        System.out.println("\t\t\t " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t " + board[2][0] + " | " + board[2][1] + " | " + board[2][2] + "\n");
    }

    private static void showInstructions() {
        System.out.println("\nChoose a cell numbered from 1 to 9 as below and play\n");
        System.out.println("\t\t\t 1 | 2 | 3 ");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 4 | 5 | 6 ");
        System.out.println("\t\t\t-----------");
        System.out.println("\t\t\t 7 | 8 | 9 \n");
    }

    private static void initialise(char[][] board) {
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                board[i][j] = '*';
            }
        }
    }

    private static void declareWinner(int whoseTurn) {
        if (whoseTurn == COMPUTER)
            System.out.println("COMPUTER has won");
        else
            System.out.println("HUMAN has won");
    }

    private static boolean rowCrossed(char[][] board) {
        for (int i = 0; i < SIDE; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '*')
                return true;
        }
        return false;
    }

    private static boolean columnCrossed(char[][] board) {
        for (int i = 0; i < SIDE; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '*')
                return true;
        }
        return false;
    }

    private static boolean diagonalCrossed(char[][] board) {
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '*')
            return true;
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '*')
            return true;
        return false;
    }

    private static boolean gameOver(char[][] board) {
        return (rowCrossed(board) || columnCrossed(board) || diagonalCrossed(board));
    }

    private static int minimax(char[][] board, int depth, boolean isAI) {
        int score;
        if (gameOver(board)) {
            return isAI ? -10 : 10;
        } else {
            if (depth < 9) {
                if (isAI) {
                    int bestScore = -999;
                    for (int i = 0; i < SIDE; i++) {
                        for (int j = 0; j < SIDE; j++) {
                            if (board[i][j] == '*') {
                                board[i][j] = COMPUTERMOVE;
                                score = minimax(board, depth + 1, false);
                                board[i][j] = '*';
                                if (score > bestScore) {
                                    bestScore = score;
                                }
                            }
                        }
                    }
                    return bestScore;
                } else {
                    int bestScore = 999;
                    for (int i = 0; i < SIDE; i++) {
                        for (int j = 0; j < SIDE; j++) {
                            if (board[i][j] == '*') {
                                board[i][j] = HUMANMOVE;
                                score = minimax(board, depth + 1, true);
                                board[i][j] = '*';
                                if (score < bestScore) {
                                    bestScore = score;
                                }
                            }
                        }
                    }
                    return bestScore;
                }
            } else {
                return 0;
            }
        }
    }

    private static int bestMove(char[][] board, int moveIndex) {
        int x = -1, y = -1;
        int score;
        int bestScore = -999;
        for (int i = 0; i < SIDE; i++) {
            for (int j = 0; j < SIDE; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = COMPUTERMOVE;
                    score = minimax(board, moveIndex + 1, false);
                    board[i][j] = '*';
                    if (score > bestScore) {
                        bestScore = score;
                        x = i;
                        y = j;
                    }
                }
            }
        }
        return x * 3 + y;
    }

    private static void playTicTacToe(int whoseTurn, char[][] board) {
        int moveIndex = 0, x, y;
        Scanner scanner = new Scanner(System.in);
        while (!gameOver(board) && moveIndex != SIDE * SIDE) {
            int n;
            if (whoseTurn == COMPUTER) {
                n = bestMove(board, moveIndex);
                x = n / SIDE;
                y = n % SIDE;
                board[x][y] = COMPUTERMOVE;
                System.out.printf("COMPUTER has put a %c in cell %d\n\n", COMPUTERMOVE, n + 1);
                showBoard(board);
                moveIndex++;
                whoseTurn = HUMAN;
            } else if (whoseTurn == HUMAN) {
                System.out.print("You can insert in the following positions : ");
                for (int i = 0; i < SIDE; i++)
                    for (int j = 0; j < SIDE; j++)
                        if (board[i][j] == '*')
                            System.out.print((i * 3 + j) + 1 + " ");
                System.out.print("\nEnter the position = ");
                n = scanner.nextInt();
                n--;
                x = n / SIDE;
                y = n % SIDE;
                if (board[x][y] == '*' && n < 9 && n >= 0) {
                    board[x][y] = HUMANMOVE;
                    System.out.printf("\nHUMAN has put a %c in cell %d\n\n", HUMANMOVE, n + 1);
                    showBoard(board);
                    moveIndex++;
                    whoseTurn = COMPUTER;
                } else if (board[x][y] != '*' && n < 9 && n >= 0) {
                    System.out.println("\nPosition is occupied, select any one place from the available places\n\n");
                } else if (n < 0 || n > 8) {
                    System.out.println("Invalid position\n");
                }
            }
        }
        scanner.close();
        if (!gameOver(board) && moveIndex == SIDE * SIDE)
            System.out.println("It's a draw\n");
        else {
            if (whoseTurn == COMPUTER)
                whoseTurn = HUMAN;
            else if (whoseTurn == HUMAN)
                whoseTurn = COMPUTER;
            declareWinner(whoseTurn);
        }
    }
}
