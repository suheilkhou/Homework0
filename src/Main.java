import java.io.File;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.util.Random;
import java.util.Scanner;
//hkbkjkhbhb
public class Main {
    public static Scanner scanner;
    public static Random rnd;

    int x = 8;
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



