import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static Scanner scanner;
    public static Random rnd;

    public static int[][] splitInput(String information) {
        /*
        This function takes a String as an argument and returns a 2d String array, the String is split into twice:
        The first time it gets split by the char space " ", and the second time is gets split by the char
        "X". This function is used to split the input of the type aXb cXd eXf...
        The returned value is: [[a,b],[c,d],[e,f]...]
         */
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
        /*
        This function takes a 2d String array and an int a, its prints the 2d array by looping
        through it using two for loops, the integer a holds one of two values (0 or 1),
        where 0 is used to declare that the function is used to print the game board of the user
        and the 1 is used to declare that the function is used to print the guessing board of the user.
        This function doesn't return a value since it is used only to print.
         */
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
        /*
        This function takes a String value as an argument and returns an integer array,
        it splits the given String by the string ", ", it then converts the value to integer
        and saves the value in the returned array.
        Example: a, b, c --> [a,b,c]

         */
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
        /*
        This function takes 4 arguments, 2d String array board, int array placement, int size, String user.
        It returns a boolean value: True = it is legal to place the ship, False = it is illegal to place the ship.
        Board is the board that is checked if a certain ship can be placed or not,
        placement is an array that holds the coordinates of the ship (the coordinates of the start of the ship and the
        orientation of the ship). Size holds the size of the ship (length), user holds either the value "user" or "pc"
        it is used to determine if an output should be pushed or not.
         */
        if (placement[2] != 0 && placement[2] != 1) {
            if (user.equals("user")) {
                System.out.println("Illegal orientation, try again!");
            }
            return false;
        }
        if (placement[0] < 0 | placement[0] > board.length - 1 | placement[1] < 0 | placement[1] > board[0].length - 1) {
            if (user.equals("user")) {
                System.out.println("Illegal tile, try again!");
            }
            return false;
        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (placement[1] + i >= board[0].length) {
                    if (user.equals("user")) {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (placement[0] + i >= board.length) {
                    if (user.equals("user")) {
                        System.out.println("Battleship exceeds the boundaries of the board, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (board[placement[0] + i][placement[1]].equals("#")) {
                    if (user.equals("user")) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (board[placement[0]][placement[1] + i].equals("#")) {
                    if (user.equals("user")) {
                        System.out.println("Battleship overlaps another battleship, try again!");
                    }
                    return false;
                }
            }
        }
        if (placement[2] == 1) {
            for (int i = 0; i < size; i++) {
                if (!checkAround(board, placement[0] + i, placement[1], user)) {
                    return false;
                }
            }

        }
        if (placement[2] == 0) {
            for (int i = 0; i < size; i++) {
                if (!checkAround(board, placement[0], placement[1] + i, user)) {
                    return false;
                }
            }

        }
        placeShip(board, placement, size);
        return true;

    }

    public static boolean checkAround(String[][] board, int x, int y, String user) {
        /*
        This function takes 4 arguments, a 2d String array board, int x, int y, String user.
        It returns a boolean value, True = there is no adjacent ship, False = there is an adjacent ship.
        Board is the board that its gets checked on, int x and y are the coordinates of the ship that is getting
        checked, user is a String used to determine whether there should be an output or not.
         */
        if (x == board.length - 1 && y != board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j].equals("#")) {
                        if (user.equals("user")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else if (x != board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j].equals("#")) {
                        if (user.equals("user")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else if (x == board.length - 1 && y == board[0].length - 1) {
            for (int i = -1; i < 1; i++) {
                for (int j = -1; j < 1; j++) {
                    if (board[x + i][y + j].equals("#")) {
                        if (user.equals("user")) {
                            System.out.println("Adjacent battleship detected, try again!");
                        }
                        return false;
                    }
                }
            }
        } else {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (board[x + i][y + j].equals("#")) {
                        if (user.equals("user")) {
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
        /*
        This function takes 3 arguments, a 2d String array board, an int array placement, an int size.
        It doesn't return anything (void), because it is used to place a ship on the board by changing the values on it.
        Board is the board that is getting changed, placement is an array that hold the starting coordinates of the ship and
        it's direction, size holds the size of the ship (length).
         */
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
        /*
        This function takes 2 arguments, a 2d String array board and a 2d int array battleShipSizes.
        It doesn't return any value (void) since it is used to take input from the user and place ships by
        calling another functions.
        Board is the board that is being changed and battleShipSizes is an array that hold the quantity and type of the ships.
         */
        for (int i = 0; i < battleShipSizes.length; i++) {
            for (int j = 0; j < battleShipSizes[i][0]; j++) {
                System.out.println("Enter location and orientation for battleship of size " + battleShipSizes[i][1]);
                boolean flag = false;
                while (!flag) {
                    String placementInput = scanner.nextLine();

                    if (checkLegal(board, placement(placementInput), battleShipSizes[i][1], "user")) {
                        flag = true;
                        printBoard(board, 0);
                    }
                }

            }
        }
    }

    public static void setUpPc(String[][] board, int[][] battleShipSizes) {
        /*
        setUpPc is a function just like setUpBoard the only difference is that it is used to place
        the pc's ship, that's way it doesn't print any output nor takes any input.
         */
        for (int i = 0; i < battleShipSizes.length; i++) {
            for (int j = 0; j < battleShipSizes[i][0]; j++) {
                boolean flag = false;
                while (!flag) {
                    int x = rnd.nextInt(board.length-1);
                    int y = rnd.nextInt(board[0].length-1);
                    int orientationPc = rnd.nextInt(2);
                    String placementInputPc = x + ", " + y + ", " + orientationPc;

                    if (checkLegal(board, placement(placementInputPc), battleShipSizes[i][1], "pc")) {
                        flag = true;
                    }
                }

            }
        }
    }

    public static int numberOfShips(int[][] battleShipSizes) {
        /*
        This function takes a 2d int array battleShipSizes as an argument and returns an integer.
        It loops through the array and returns the total number of ships int the array.
         */
        int counter = 0;
        for (int i = 0; i < battleShipSizes.length; i++) {
            counter += battleShipSizes[i][0];
        }
        return counter;
    }

    public static String shoot(int x, int y, String[][] userBoard, String[][] userGuessingBoard, String[][] pcBoard,
                               String[][] pcGuessingBoard, String user, int[] userNumberOfShips, int[] pcNumberOfShips) {
        if (user.equals("user")) {
            if (x <= 0 | x > userBoard.length - 1 | y <= 0 | y > userBoard[0].length - 1) {
                System.out.println("Illegal tile, try again!");
                return "false";
            }
            if (userGuessingBoard[x][y].equals("V") || userGuessingBoard[x][y].equals("X")) {
                System.out.println("Tile already attacked, try again!");
                return "false";
            }
            if (pcBoard[x][y].equals("#")) {
                System.out.println("That is a hit!");
                userGuessingBoard[x][y] = "V";
                pcBoard[x][y] = "X";
                if (checkIfDrowned(pcBoard, x, y)){
                    pcNumberOfShips[0] -= 1;
                    System.out.println("The computer's battleship has been drowned, "+pcNumberOfShips[0]+ " more battleships to go!");
                }
                if (pcNumberOfShips[0] == 0) {
                    System.out.println("You won the game!");
                    return "done";
                }
                return "true";
            }
            if (pcBoard[x][y].equals("–")){
                userGuessingBoard[x][y] = "X";
                System.out.println("That is a miss!");
                return "true";
            }
        }


        if (user.equals("pc")) {
            if (x < 0 | x > pcBoard.length - 1 | y < 0 | y > pcBoard[0].length - 1) {
                return "false";
            }
            if (pcGuessingBoard[x][y].equals("V") || pcGuessingBoard[x][y].equals("X")) {
                return "false";
            }
            if (userBoard[x][y].equals("#")) {
                System.out.println("The computer attacked ("+(x-1)+", "+(y-1)+")");
                System.out.println("That is a hit!");
                pcGuessingBoard[x][y] = "V";
                userBoard[x][y] = "X";
                if (checkIfDrowned(userBoard, x, y)){
                    userNumberOfShips[0] -= 1;
                    System.out.println("Your battleship has been drowned, you have left " +userNumberOfShips[0]+ " more battleships!");
                }
                if (userNumberOfShips[0] == 0) {
                    return "done";
                }
                return "true";
            }
            if (userBoard[x][y].equals("–")){
                pcGuessingBoard[x][y] = "X";
                System.out.println("The computer attacked ("+(x-1)+", "+(y-1)+")");
                System.out.println("That is a miss!");
                return "true";
            }
        }
        return "true";

    }


    public static boolean checkIfDrowned(String[][] board, int x, int y){
        if (x == board.length - 1 && y == board[0].length - 1) {
            // zawye
            int i = 0;

            while(x-i > 0 && !board[x - i][y].equals("–")){
                //btl3 lfo2
                if (board[x - i][y].equals("#")){
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y-i > 0 && !board[x][y - i].equals("–")){
                //bro7 shmal
                if (board[x][y - i].equals("#")){
                    return false;
                }
                i += 1;
            }
        }else if (x != board.length - 1 && y == board[0].length - 1) {
            // right column
            int i = 0;

            while (x - i > 0 && !board[x - i][y].equals("–")) {
                //btl3 lfo2
                if (board[x - i][y].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (x + i < board.length && !board[x + i][y].equals("–")) {
                // Bnzl lt7t
                if (board[x + i][y].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && !board[x][y - i].equals("–")) {
                //bro7 shnal
                if (board[x][y - i].equals("#")) {
                    return false;
                }
                i += 1;
            }
        }
        else if (x == board.length - 1 && y != board[0].length - 1) {
            // bottom
            int i = 0;

            while (x - i > 0 && !board[x - i][y].equals("–")) {
                // Btl3 lfo2
                if (board[x - i][y].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && !board[x][y - i].equals("–")) {
                // Bro7 shmal
                if (board[x][y - i].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y + i < board[0].length && !board[x][y + i].equals("–")) {
                // Bro7 ymen
                if (board[x][y + i].equals("#")) {
                    return false;
                }
                i += 1;
            }
        }else {
            // No boards
            int i = 0;

            while (x - i > 0 && !board[x - i][y].equals("–")) {
                // Btl3 lfo2
                if (board[x - i][y].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (x + i < board.length && !board[x + i][y].equals("–")) {
                // Bnzl lt7t
                if (board[x + i][y].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y - i > 0 && !board[x][y - i].equals("–")) {
                // Bro7 shmal
                if (board[x][y - i].equals("#")) {
                    return false;
                }
                i += 1;
            }
            i = 0;
            while (y + i < board[0].length && !board[x][y + i].equals("–")) {
                // Bro7 ymen
                if (board[x][y + i].equals("#")) {
                    return false;
                }
                i += 1;
            }
        }
        return true;
    }

    public static int length(int a){
        if (a < 10){
            return 0;
        }
        return 1 + length(a/10);
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
        int maxLength = length(boardSize[0][0]+1);
        for (int x = 0; x < boardSize[0][0]+1; x++){
            for (int length =(maxLength - length(x-1)); length > 0; length--){
                board[x][0] =" " + board[x][0];
                guessingBoard[x][0] = " "+guessingBoard[x][0];
                pcBoard[x][0] =" " + pcBoard[x][0];
                pcGuessingBoard[x][0] = " " + pcGuessingBoard[x][0];
            }
        }

        int[] userNumberOfShips = new int[1];
        userNumberOfShips[0] = numberOfShips(battleShipSizes);
        int[] pcNumberOfShips = new int[1];
        pcNumberOfShips[0] = numberOfShips(battleShipSizes);


        printBoard(board,0);
        setUpBoard(board,battleShipSizes);
        printBoard(guessingBoard,1);
        setUpPc(pcBoard,battleShipSizes);

        while(userNumberOfShips[0] > 0 && pcNumberOfShips[0] > 0){
            System.out.println("Enter a tile to attack");
            int[] userCoordinates = placement(scanner.nextLine());
            String status = shoot(userCoordinates[0],userCoordinates[1],board,guessingBoard,pcBoard,pcGuessingBoard,"user",userNumberOfShips,pcNumberOfShips);
            while (status.equals("false")){
                userCoordinates = placement(scanner.nextLine());
                status = shoot(userCoordinates[0],userCoordinates[1],board,guessingBoard,pcBoard,pcGuessingBoard,"user",userNumberOfShips,pcNumberOfShips);
            }
            if(status.equals("done")){
                break;
            }


            int x = rnd.nextInt(board.length-1);
            int y = rnd.nextInt(board[0].length-1);

            x += 1;
            y += 1;
            status = shoot(x,y,board,guessingBoard,pcBoard,pcGuessingBoard,"pc",userNumberOfShips,pcNumberOfShips);
            while (status.equals("false")){
                x = rnd.nextInt(board.length-1);
                y = rnd.nextInt(board[0].length-1);
                x += 1;
                y += 1;
                status = shoot(x,y,board,guessingBoard,pcBoard,pcGuessingBoard,"pc",userNumberOfShips,pcNumberOfShips);
            }
            printBoard(board,0);
            if (status.equals("done")){
                System.out.println("You lost ):");
                break;
            }
            printBoard(guessingBoard,1);




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



