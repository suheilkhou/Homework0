import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public static int[][] splitInput(String information) {
        // This function takes a string as an argument and return a 2d array.
        // The returned array is the given string split by " " and "X".
        String[] info = information.split(" ");
        int[][] sizes = new int[info.length][2];
        for (int i = 0; i < info.length; i++) {
            String[] temp = info[i].split("X");
            sizes[i][0] = Integer.valueOf(temp[0]);
            sizes[i][1] = Integer.valueOf(temp[1]);
        }
        return sizes;
    }

    public static void printBoard(String[][] board, int a) {
        // This function takes a 2d String array and prints it.
        // The printed output is the current game-board.
        if (a == 0) {
            System.out.println("Your current game board:");
        }
        if (a == 1) {
            System.out.println("Your current guessing board:");
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
                if (j != board[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[] placement(String placement) {
        // This function takes a String as an argument and return an int array.
        // The returned value is the string split using ", ".
        String[] temp = placement.split(", ");
        int[] temp1 = new int[3];
        for (int i = 0; i < temp.length; i++) {
            temp1[i] = Integer.valueOf(temp[i]);
        }
        temp1[0] += 1;
        temp1[1] += 1;
        return temp1;
    }

    public static boolean checkLegal(String[][] board, int[] placement, int size, String user) {
        // This function takes 3 arguments, cuurent gameboard, placement of the ship, the size of the ship, it
        // returns true if it is legal to place the ship else it returns false and prints an output that matches the
        // illegal move, it checks if the move is legal or not according to a given arrangement.
        String[][] tempBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                tempBoard[i][j] = board[i][j];
            }
        }
        if (placement[2] != 0 && placement[2] != 1) {
            if (user == "user") {
                System.out.println("Illegal orientation, try again!");
            }
            return false;
        }
        if (placement[0] < 0 | placement[0] > board.length - 1 | placement[1] < 0 | placement[1] > board[0].length - 1) {
            if (user == "user") {
                System.out.println("Illegal tile, try again!");
            }
            return false;
        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (placement[1] + i >= board[0].length) {
                    if (user == "user") {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (placement[0] + i >= board.length) {
                    if (user == "user") {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (board[placement[0] + i][placement[1]] == "#") {
                    if (user == "user") {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (board[placement[0]][placement[1] + i] == "#") {
                    if (user == "user") {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (checkAround(board, placement[0] + i, placement[1], user) == false) {
                    return false;
                }
            }

        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (checkAround(board, placement[0], placement[1] + i, user) == false) {
                    return false;
                }
            }

        }
        placeShip(board, placement, size);
        return true;

    }

    public static boolean checkAround(String[][] board, int x, int y, String user) {
        // This function takes 3 arguments, current board game, and coordinates of a ship, it checks the
        // adjacent of the ship, returns true only if its legal.
        if (x == board.length - 1 && y != board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j] == "#") {
                        if (user == "user") {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else if (x != board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j] == "#") {
                        if (user == "user") {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else if (x == board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j] == "#") {
                        if (user == "user") {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j] == "#") {
                        if (user == "user") {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        }

        return true;
    }


    public static void placeShip(String[][] board, int[] placement, int size) {
        // This function takes 3 arguments and places the ship according to the given placement and size.
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                board[placement[0]][placement[1] + i] = "#";
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                board[placement[0] + i][placement[1]] = "#";
            }
        }
    }

    public static void setUpBoard(String[][] board, int[][] battleShipSizes) {
        for (int i = 0; i < battleShipSizes.length; i++) {
            for (int j = 0; j < battleShipSizes[i][0]; j++) {
                System.out.println("Enter location and orientation for battleship of size " + battleShipSizes[i][1]);
                boolean flag = false;
                while (flag == false) {
                    String placementInput = scanner.nextLine();

                    if (checkLegal(board, placement(placementInput), battleShipSizes[i][1], "user") == true) {
                        flag = true;
                        printBoard(board, 0);
                    }
                }

            }
        }
    }

    public static void setUpPc(String[][] board, int[][] battleShipSizes) {
        for (int i = 0; i < battleShipSizes.length; i++) {
            for (int j = 0; j < battleShipSizes[i][0]; j++) {
                boolean flag = false;
                while (flag == false) {
                    int x = rnd.nextInt(board.length-1);
                    int y = rnd.nextInt(board[0].length-1);
                    int orientationPc = rnd.nextInt(1);
                    String placementInputPc = x + ", " + y + ", " + orientationPc;

                    if (checkLegal(board, placement(placementInputPc), battleShipSizes[i][1], "pc") == true) {
                        flag = true;
                    }
                }

            }
        }
    }

    public static int numeberOfShips(int[][] battleShipSizes) {
        int counter = 0;
        for (int i = 0; i < battleShipSizes.length; i++) {
            counter += battleShipSizes[i][0];
        }
        return counter;
    }

    public static String shoot(int x, int y, String[][] userBoard, String[][] userGuessingBoard, String[][] pcBoard, String[][] pcGuessingBoard, String user, int userNumberOfShips,int pcNumberOfShips) {
        if (user == "user") {
            if (x < 0 | x > userBoard.length - 1 | y < 0 | y > userBoard[0].length - 1) {
                System.out.println("Illegal tile, try again!");
                return "false";
            }
            if (userGuessingBoard[x][y] == "V" || userGuessingBoard[x][y] == "X") {
                System.out.println("Tile already attacked, try again!");
                return "false";
            }
            if (pcBoard[x][y] == "#") {
                System.out.println("That is a hit!");
                userGuessingBoard[x][y] = "V";
                pcBoard[x][y] = "X";
                if (checkIfDrowned(pcBoard, x, y)==true){
                    pcNumberOfShips -= 1;
                    System.out.println("The computer's battleships has been drowned, "+pcNumberOfShips+ " more battleships to go!");
                }
                if (pcNumberOfShips == 0) {
                    System.out.println("You won the game!");
                    return "done";
                }
                return "true";
            }
            if (pcBoard[x][y] == "–"){
                userGuessingBoard[x][y] = "X";
                System.out.println("That is a miss!");
                return "true";
            }
        }


        if (user == "pc") {
            if (x < 0 | x > pcBoard.length - 1 | y < 0 | y > pcBoard[0].length - 1) {
                return "false";
            }
            if (pcGuessingBoard[x][y] == "V" || pcGuessingBoard[x][y] == "X") {
                return "false";
            }
            if (userBoard[x][y] == "#") {
                System.out.println("The computer attacked ("+(x-1)+", "+(y-1)+")");
                System.out.println("That is a hit!");
                pcGuessingBoard[x][y] = "V";
                userBoard[x][y] = "X";
                if (checkIfDrowned(userBoard, x, y)==true){
                    userNumberOfShips -= 1;
                    System.out.println("Your battleship has been drowned, you have left " +userNumberOfShips+ " more battleships!");
                }
                if (userNumberOfShips == 0) {
                    System.out.println("You lost ):!");
                    return "done";
                }
                return "true";
            }
            if (userBoard[x][y] == "–"){
                pcGuessingBoard[x][y] = "X";
                System.out.println("The computer attacked ("+(x-1)+", "+(y-1)+")");
                System.out.println("That is a miss!");
                return "true";
            }
        }
        return "true";

    }

    public static boolean checkDrowned(String[][] board, int x, int y) {
        if (x == board.length - 1 && y != board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j] == "#") {
                        return false;
                    }
                }
            }
        } else if (x != board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j] == "#") {
                        return false;
                    }
                }
            }
        } else if (x == board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j] == "#") {
                        return false;
                    }
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j] == "#") {
                        return false;
                    }
                }
            }
        }
        return true;
    }


//    public static boolean checkDro(String[][] board, int x, int y){
//        if (x == board.length - 1 && y != board[0].length - 1) {
//            // bottom
//            for (int i = -1; i < 1; i++) {
//                for (int j = -1; j < 2; j++) {
//                    if (board[x + i][y + j] == "#") {
//                        return false;
//                    }
//                }
//            }
//        } else if (x != board.length - 1 && y == board[0].length - 1) {
//            // right column
//            for (int i = -1; i < 2; i++) {
//                for (int j = -1; j < 1; j++) {
//                    if (board[x + i][y + j] == "#") {
//                        return false;
//                    }
//                }
//            }
//        } else if (x == board.length - 1 && y == board[0].length - 1) {
//            // [n][m]
//            for (int i = -1; i < 1; i++) {
//                for (int j = -1; j < 1; j++) {
//                    if (board[x + i][y + j] == "#") {
//                        return false;
//                    }
//                }
//            }
//        } else {
//            // No boards
//            for (int i = -1; i < 2; i++) {
//                for (int j = -1; j < 2; j++) {
//                    if (board[x + i][y + j] == "#") {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//


    public static boolean checkIfDrowned(String[][] board, int x, int y){
        if (x == board.length - 1 && y == board[0].length - 1) {
            // zawye
            int i = 0;

            while(x-i > 0 && board[x-i][y] != "–"){
                //btl3 lfo2
                if (board[x-i][y] == "#"){
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y-i > 0 && board[x][y - i] != "–"){
                //bro7 shmal
                if (board[x][y-i] == "#"){
                    return false;
                }
                i += 1;
            }
        }else if (x != board.length - 1 && y == board[0].length - 1) {
            // right column
            int i = 0;

            while (x - i > 0 && board[x - i][y] != "–") {
                //btl3 lfo2
                if (board[x - i][y] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (x + i < board.length && board[x + i][y] != "–") {
                // Bnzl lt7t
                if (board[x + i][y] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && board[x][y - i] != "–") {
                //bro7 shnal
                if (board[x][y - i] == "#") {
                    return false;
                }
                i += 1;
            }
        }
        else if (x == board.length - 1 && y != board[0].length - 1) {
            // bottom
            int i = 0;

            while (x - i > 0 && board[x - i][y] != "–") {
                // Btl3 lfo2
                if (board[x - i][y] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && board[x][y - i] != "–") {
                // Bro7 shmal
                if (board[x][y - i] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y + i > board[0].length && board[x][y + i] != "–") {
                // Bro7 ymen
                if (board[x][y + i] == "#") {
                    return false;
                }
                i += 1;
            }
        }else {
            // No boards
            int i = 0;

            while (x - i > 0 && board[x - i][y] != "–") {
                // Btl3 lfo2
                if (board[x - i][y] == "#") {
                    return false;
                }
                i += 1;
            }
            while (x + i < board.length && board[x + i][y] != "–") {
                // Bnzl lt7t
                if (board[x + i][y] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && board[x][y - i] != "–") {
                // Bro7 shmal
                if (board[x][y - i] == "#") {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y + i > board[0].length && board[x][y + i] != "–") {
                // Bro7 ymen
                if (board[x][y + i] == "#") {
                    return false;
                }
                i += 1;
            }
        }
        return true;
    }







    public static void battleshipGame() {
        System.out.println("Enter the board size");
        int[][] boardSize = splitInput(scanner.nextLine());
        System.out.println("Enter the battleships sizes");
        int[][] battleShipSizes = splitInput(scanner.nextLine());
        String[][] board = new String[boardSize[0][0]+1][boardSize[0][1]+1];
        String[][] guessingBoard = new String[boardSize[0][0]+1][boardSize[0][1]+1];
        String[][] pcBoard = new String[boardSize[0][0]+1][boardSize[0][1]+1];
        String[][] pcGuessingBoard = new String[boardSize[0][0]+1][boardSize[0][1]+1];
        for (int i = 0; i < boardSize[0][0]+1; i++){
            for (int j = 0; j < boardSize[0][1]+1; j++){
                if (i == 0){
                    board[i][j] = String.valueOf(j-1);
                    guessingBoard[i][j] = String.valueOf(j-1);
                    pcBoard[i][j] = String.valueOf(j-1);
                    pcGuessingBoard[i][j] = String.valueOf(j-1);
                }
                if (j == 0){
                    board[i][j] = String.valueOf(i-1);
                    guessingBoard[i][j] = String.valueOf(i-1);
                    pcBoard[i][j] = String.valueOf(i-1);
                    pcGuessingBoard[i][j] = String.valueOf(i-1);
                }
                if(i!=0 && j!=0){
                    board[i][j] = "–";
                    guessingBoard[i][j] = "–";
                    pcBoard[i][j] = "–";
                    pcGuessingBoard[i][j] = "–";
                }
            }
        }
        board[0][0] = " ";
        guessingBoard[0][0]= " ";
        pcBoard[0][0] = " ";
        pcGuessingBoard[0][0] = " ";

        int userNumberOfShips = numeberOfShips(battleShipSizes);
        int pcNumberOfShips = numeberOfShips(battleShipSizes);

        printBoard(board,0);
        setUpBoard(board,battleShipSizes);
        printBoard(guessingBoard,1);
        setUpPc(pcBoard,battleShipSizes);


        while(userNumberOfShips > 0 && pcNumberOfShips > 0){
            System.out.println("Enter a tile to attack");
            int[] userCordinates = placement(scanner.nextLine());
            String status = shoot(userCordinates[0],userCordinates[1],board,guessingBoard,pcBoard,pcGuessingBoard,"user",userNumberOfShips,pcNumberOfShips);
            while (status == "false"){
                userCordinates = placement(scanner.nextLine());
                status = shoot(userCordinates[0],userCordinates[1],board,guessingBoard,pcBoard,pcGuessingBoard,"user",userNumberOfShips,pcNumberOfShips);
            }
            if(status == "done"){
                break;
            }


            int x = rnd.nextInt(board.length-1);
            int y = rnd.nextInt(board[0].length-1);

            x += 1;
            y += 1;
            status = shoot(x,y,board,guessingBoard,pcBoard,pcGuessingBoard,"pc",userNumberOfShips,pcNumberOfShips);
            while (status=="false"){
                x = rnd.nextInt(board.length-1);
                y = rnd.nextInt(board[0].length-1);
                x += 1;
                y += 1;
                status = shoot(x,y,board,guessingBoard,pcBoard,pcGuessingBoard,"pc",userNumberOfShips,pcNumberOfShips);
            }
            printBoard(board,0);
            printBoard(guessingBoard,1);
            if (status == "done"){
                break;
            }




        }
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



