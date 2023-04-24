import java.io.File;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static Scanner scanner;
    public static Random rnd;
//    public static int[][] findSize(String size){
//        // findSize is a function that takes one argument(String size), and return
//        // a 2d array where each cell defines a specific type of ship where the first
//        // value defines the size of the ship and the second value defines the quantity
//        // of the ship... newArr=[[1,5][3,7][8,9]] size1: 5 ships,, size3: 7 ships,, size8: 9 ships.
//        String arr[] = size.split(" ");
//        int[][] newArr = new int[arr.length][2];
//        for(int i = 0; i < arr.length; i++){
//            for (int j = 0; j < 2; j++){
//                newArr[i][j] = Integer.valueOf(arr[i].split("X")[j]);
//            }
//        }
//        return newArr;
//    }
//
//    public static void printCurrGame(String[][] game, int height, int width){
//        // printCurrGame is a function that takes three arguments(String[][] game, int height, int width)
//        // game.length=height and game[0].length=width. The function doesn't return anything, it prints
//        // the current game by going through the array.
//        for (int i = 0; i < height+1; i++){
//            for (int j = 0; j < width+1; j++){
//                System.out.print(game[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
//
//    public static int numberOfShips(int[][] ships){
//        int counter = 0;
//        for(int i = 0; i<ships.length;i++){
//            counter += ships[i][1];
//        }
//        return counter;
//    }
//
//    public static void putOnBoard(String[][] game){
//
//    }
//

    public static int[][] splitInput(String information){
        // This function takes a string as an argument and return a 2d array.
        // The returned array is the given string split by " " and "X".
        String[] info = information.split(" ");
        int[][] sizes = new int[info.length][2];
        for (int i = 0; i < info.length; i++){
            String[] temp = info[i].split("X");
            sizes[i][0] = Integer.valueOf(temp[0]);
            sizes[i][1] = Integer.valueOf(temp[1]);
        }
        return sizes;
    }

    public static void printBoard(String[][] board){
        // This function takes a 2d String array and prints it.
        // The printed output is the current game-board.
        System.out.println("Your current game board:");
        for(int i = 0; i<board.length;i++){
            for(int j = 0; j<board[0].length;j++){
               System.out.print(board[i][j]);
               if(j != board[0].length-1){
                   System.out.print(" ");
               }
            }
            System.out.println();
        }
    }

    public static int[] placement(String placement){
        // This function takes a String as an argument and return an int array.
        // The returned value is the string split using ", ".
        // Check
        // Check
        // Check
        // Check
        // Check
        // Check
        String[] temp = placement.split(", ");
        int[] temp1 = new int[3];
        for (int i = 0; i < temp.length; i++){
            temp1[i] = Integer.valueOf(temp[i]);
        }
        return temp1;
    }

    public static boolean checkLegal(String[][] board, int[] placement, int size){
        // This function takes 3 arguments, cuurent gameboard, placement of the ship, the size of the ship, it
        // returns true if it is legal to place the ship else it returns false and prints an output that matches the
        // illegal move, it checks if the move is legal or not according to a given arrangement.
        String[][] tempBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                tempBoard[i][j] = board[i][j];
            }
        }
        if(placement[2] != 0 && placement[2] != 1){
            System.out.println("Illegal orientation, try again!");
            return false;
        }
        if (placement[0] < 0 | placement[0] > board.length-1 | placement[1] < 0 | placement[1] > board[0].length-1){
            System.out.println("Illegal tile, try again!");
            return false;
        }
        if(placement[2] == 0){
            if (placement[0]+size-1 > board.length){
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return false;
            }
        }
        if (placement[2] == 1){
            if (placement[1]+size-1<0){
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
                return false;
            }
        }
        if (placement[2] == 0){
            for (int i = 0; i < size; i++){
                if(board[placement[0]+i][placement[1]] == "#"){
                    System.out.println("Battleship overlaps another battleship, try again!");
                    return false;
                }
            }
        }
        if (placement[2] == 1){
            for (int i = 0; i < size; i++){
                if (board[placement[0]][placement[1]+i] == "#"){
                    System.out.println("Battleship overlaps another battleship, try again!");
                    return false;
                }
            }
        }
        if (placement[2] == 0){
            for (int i = 0; i < size; i++){
                if (checkAround(board, placement[0]+i, placement[1]) == false){
                    System.out.println("Adjacent battleship detected, try again!");
                    return false;
                }
            }

        }
        if (placement[2] == 1){
            for (int i = 0; i < size; i++){
                if (checkAround(board, placement[0], placement[1]+i) == false){
                    System.out.println("Adjacent battleship detected, try again!");
                    return false;
                }
            }

        }
        placeShip(board,placement,size);
        return true;

    }

    public static boolean checkAround(String[][] board,int x, int y){
        // This function takes 3 arguments, current board game, and coordinates of a ship, it checks the
        // adjacent of the ship, returns true only if its legal.
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (board[x+i][y+j]=="#"){
                    System.out.println("Adjacent battleship detected, try again!");
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeShip(String[][] board, int[] placement, int size){
        // This function takes 3 arguments and places the ship according to the given placement and size.
        if (placement[2] == 0){
            for (int i = 0;i < size; i++){
                board[placement[0]][placement[1]+i] = "#";
            }
        }
        if (placement[2] == 1){
            for (int i = 0;i < size; i++){
                board[placement[0]+i][placement[1]] = "#";
            }
        }
    }

    public static void battleshipGame() {
//        System.out.println("Enter the board size");
//        int[][] dimensions = findSize(scanner.nextLine()); // Holds the value of the size of the board.
//        int height = dimensions[0][0];
//        int width = dimensions[0][1];
//        System.out.println("Enter the battleships sizes");
//        int[][] ships = findSize(scanner.nextLine()); // Ships is an array that holds the value of the info about the ships.
//        System.out.println("Your current game board:");
//        String[][] game = new String[height+1][width+1]; // game is a 2d array that holds the status of the game and both
//        // the first column and row are used for numbers.
//        // This for loop is used to give initial values to the game(everything is "-").
//        for (int i = 0; i < height+1; i++){
//            for (int j = 0; j < width+1; j++){
//                game[i][j] = "-";
//                if (i==0){
//                    game[i][j] = String.valueOf(j-1);
//                }
//                if (j==0){
//                    game[i][j] = String.valueOf(i-1);
//                }
//            }
//        }
//        game[0][0] = " ";
//        printCurrGame(game,height,width);
//
//        int numberOfShips = numberOfShips(ships);
//
//        String locOri = scanner.nextLine();
//        int a = Integer.valueOf(locOri.split(",")[0]);
//        int b = Integer.valueOf(locOri.split(",")[1]);
//        int Orientation = Integer.valueOf(locOri.split(",")[2]);
//

        System.out.println("Enter the board size");
        int[][] boardSize = splitInput(scanner.next());
        System.out.println("Enter the battleships sizes");
        int[][] battleShipSizes = splitInput(scanner.nextLine());
        String[][] board = new String[boardSize[0][0]+1][boardSize[0][1]+1];
        for (int i = 0; i < boardSize[0][0]+1; i++){
            for (int j = 0; j < boardSize[0][0]+1; j++){
                if (i == 0){
                    board[i][j] = String.valueOf(j-1);
                }
                if (j == 0){
                    board[i][j] = String.valueOf(i-1);
                }
                if(i!=0 && j!=0){
                    board[i][j] = "–";
                }
            }
        }
        board[0][0] = " ";

    }


    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }
}



