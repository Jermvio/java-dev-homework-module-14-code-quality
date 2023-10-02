package org.example;

import java.util.Scanner;

public class App {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        startGame(new UserPlayer(), new BotPlayer());
    }

    private static void startGame(Player firstPlayer, Player secondPlayer) {
        // init

        boolean boxAvailable = false;
        byte winner = 0;
        char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        System.out.println("Enter box number to select. Enjoy!\n");

        boolean boxEmpty = false;


        while (true) {
            printTableInConsole(box);

            boxEmpty = emptyBox(box, boxEmpty);

            if (gameFinished(winner)) {
                break;
            }

            firstPlayer.makeTurn(box);

            if (playerWin('X', box)) {
                winner = 1;
                continue;
            }

            boxAvailable = isBoxAvailable(box);

            if (checkDraw(boxAvailable)) {
                winner = 3;
                continue;
            }

            secondPlayer.makeTurn(box);

            if (playerWin('O', box)) {
                winner = 2;
                continue;
            }
        }
    }

    private static void printTableInConsole(char[] box) {
        System.out.println("\n\n " + box[0] + " | " + box[1] + " | " + box[2] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[6] + " | " + box[7] + " | " + box[8] + " \n");
    }

    private static boolean emptyBox(char[] box, boolean boxEmpty) {
        byte i;
        if (!boxEmpty) {
            for (i = 0; i < 9; i++)
                box[i] = ' ';
            boxEmpty = true;
        }
        return boxEmpty;
    }

    private static boolean gameFinished(byte winner) {
        boolean gameFinished = false;
        if (winner == 1) {
            System.out.println("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
            gameFinished = true;
        } else if (winner == 2) {
            System.out.println("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
            gameFinished = true;
        } else if (winner == 3) {
            System.out.println("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
            gameFinished = true;
        }
        return gameFinished;
    }

    private static boolean playerWin(char playerSign, char[] box) {
        if (    (box[0] == playerSign && box[1] == playerSign && box[2] == playerSign) ||
                (box[3] == playerSign && box[4] == playerSign && box[5] == playerSign) ||
                (box[6] == playerSign && box[7] == playerSign && box[8] == playerSign) ||
                (box[0] == playerSign && box[3] == playerSign && box[6] == playerSign) ||
                (box[1] == playerSign && box[4] == playerSign && box[7] == playerSign) ||
                (box[2] == playerSign && box[5] == playerSign && box[8] == playerSign) ||
                (box[0] == playerSign && box[4] == playerSign && box[8] == playerSign) ||
                (box[2] == playerSign && box[4] == playerSign && box[6] == playerSign)
        ) {
            return true;
        }
        return false;
    }

    private static boolean isBoxAvailable(char[] box) {
        byte i;
        boolean boxAvailable;
        boxAvailable = false;
        for (i = 0; i < 9; i++) {
            if (box[i] != 'X' && box[i] != 'O') {
                boxAvailable = true;
                break;
            }
        }
        return boxAvailable;
    }

    private static boolean checkDraw(boolean boxAvailable) {
        if (boxAvailable == false) {
            return true;
        }
        return false;
    }

    private interface Player {
        public void makeTurn(char[] box);
    }

    private static class UserPlayer implements Player {

        @Override
        public void makeTurn(char[] box) {
            byte input;
            while (true) {
                input = scan.nextByte();
                //check for inbounds
                if (input > 0 && input < 10) {
                    //check place availability
                    if (box[input - 1] == 'X' || box[input - 1] == 'O')
                        System.out.println("That one is already in use. Enter another.");
                    else { // puts sign
                        box[input - 1] = 'X';
                        break;
                    }
                } else
                    System.out.println("Invalid input. Enter again.");
            }
        }
    }

    private static class BotPlayer implements Player {

        @Override
        public void makeTurn(char[] box) {
            byte rand;
            while (true) {
                rand = (byte) (Math.random() * (9 - 1 + 1) + 1);
                if (box[rand - 1] != 'X' && box[rand - 1] != 'O') {
                    box[rand - 1] = 'O';
                    break;
                }
            }
        }
    }

}