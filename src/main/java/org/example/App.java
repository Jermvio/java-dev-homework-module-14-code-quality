package org.example;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        startGame(new UserPlayer(), new BotPlayer());
    }

    private static void startGame(Player firstPlayer, Player secondPlayer) {
        byte winner = 0;
        char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        System.out.println("Enter box number to select. Enjoy!\n");

        printTableInConsole(box);
        emptyBox(box);

        do {
            firstPlayer.makeTurn(box);

            if (checkPlayerWin('X', box)) {
                winner = 1;
            }

            if (checkDraw(box, winner)) {
                winner = 3;
            }

            secondPlayer.makeTurn(box);

            if (checkPlayerWin('O', box)) {
                winner = 2;
            }

            printTableInConsole(box);
        } while (winner == 0);
        gameFinished(winner);
    }

    private static void printTableInConsole(char[] box) {
        System.out.println("\n\n " + box[0] + " | " + box[1] + " | " + box[2] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[6] + " | " + box[7] + " | " + box[8] + " \n");
    }

    private static void emptyBox(char[] box) {
        for (int i = 0; i < 9; i++) {
            box[i] = ' ';
        }
    }

    private static void gameFinished(byte winner) {
        if (winner == 1) {
            System.out.println("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else if (winner == 2) {
            System.out.println("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else if (winner == 3) {
            System.out.println("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
        }
    }

    public static boolean checkPlayerWin(char playerSign, char[] box) {
        return (box[0] == playerSign && box[1] == playerSign && box[2] == playerSign) ||
                (box[3] == playerSign && box[4] == playerSign && box[5] == playerSign) ||
                (box[6] == playerSign && box[7] == playerSign && box[8] == playerSign) ||
                (box[0] == playerSign && box[3] == playerSign && box[6] == playerSign) ||
                (box[1] == playerSign && box[4] == playerSign && box[7] == playerSign) ||
                (box[2] == playerSign && box[5] == playerSign && box[8] == playerSign) ||
                (box[0] == playerSign && box[4] == playerSign && box[8] == playerSign) ||
                (box[2] == playerSign && box[4] == playerSign && box[6] == playerSign);
    }

    private static boolean noBoxAvailable(char[] box) {
        for (int i = 0; i < 9; i++) {
            if (box[i] == ' ') {
                return false;
            }
        }
        return true;
    }

    private static boolean checkDraw(char[] box, byte winner) {
        return noBoxAvailable(box) && winner == 0;
    }

    public interface Player {
        public void makeTurn(char[] box);
    }

    private static class UserPlayer implements Player {

        Scanner scan = new Scanner(System.in);

        @Override
        public void makeTurn(char[] box) {
            if (noBoxAvailable(box)) {
                return;
            }

            byte input;
            while (true) {
                input = scan.nextByte();

                if (input <= 0 || input >= 10) {
                    System.out.println("Invalid input. Enter again.");
                    continue;
                }

                if (box[input - 1] != ' ') {
                    System.out.println("That one is already in use. Enter another.");
                    continue;
                }

                box[input - 1] = 'X';
                break;
            }
        }
    }

    private static class BotPlayer implements Player {

        @Override
        public void makeTurn(char[] box) {
            if (noBoxAvailable(box)) {
                return;
            }

            byte rand;
            while (true) {
                rand = (byte) (Math.random() * (9 - 1 + 1) + 1);
                if (box[rand - 1] == ' ') {
                    box[rand - 1] = 'O';
                    break;
                }
            }
        }
    }

}