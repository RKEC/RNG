package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws Exception {
        start();
    }

    private static void start() {
        menu();
    }


    public static void play() throws Exception {

        Random random = new Random();
        Scanner keyboard = new Scanner(System.in);

        Database database = new Database();
        database.connectDatabase();

        int x;
        int y;
        int answer;
        int ansCount = 0;
        int operator;
        int difficulty = chooseDifficulty();
        boolean loop = true;


        while (loop) {
            x = random.nextInt(difficulty);
            y = random.nextInt(difficulty);

            if (difficulty == 20) {
                answer = x + y;
                System.out.println("Solve for: " + x + " + " + y);
            } else if (difficulty == 50) {
                operator = random.nextInt(2);
                if (operator == 0) {
                    answer = x + y;
                    System.out.println("Solve for: " + x + " + " + y);
                } else {
                    answer = x - y;
                    System.out.println("Solve for: " + x + " - " + y);
                }
            } else {
                operator = random.nextInt(3);
                if (operator == 0) {
                    answer = x + y;
                    System.out.println("Solve for: " + x + " + " + y);
                } else if (operator == 1) {
                    answer = x - y;
                    System.out.println("Solve for: " + x + " - " + y);
                } else if (operator == 2) {
                    answer = x * y;
                    System.out.println("Solve for: " + x + " * " + y);
                } else {
                    answer = x / y;
                    System.out.println("Solve for: " + x + " / " + y);
                }

            }
            int input = keyboard.nextInt();

            if (input == answer) {
                System.out.println(Colours.GREEN + "CORRECT!!!" + Colours.RESET);
                ansCount++;
            } else {
                System.out.println(Colours.RED + "INCORRECT!!!" + Colours.RESET);
                System.out.println("Answer: " + answer);
                loop = false;
                if (ansCount > 0) {

                    System.out.println(Colours.BLUE + "Enter name: " + Colours.RESET);
                    String name = keyboard.next();
                    if (database.registerScore(database.getNewId(), name, ansCount, difficulty)) {
                        System.out.println(Colours.GREEN + "Your score is now on the leaderboards " + name + Colours.RESET);
                    }
                }
            }
        }
        System.out.println(Colours.PURPLE + "Thanks for playing, your score was " + Colours.GREEN + ansCount + Colours.RESET);
    }

    public static int chooseDifficulty() {
        Scanner keyboard = new Scanner(System.in);

        System.out.print(Colours.PURPLE + "Choose difficulty: " + Colours.RESET);

        Difficulty difficulty;
        boolean loop = true;
        int option;
        int diff = 0;

        while (loop) {
            printDifficultyOptions();
            try {
                String input = keyboard.nextLine();
                if (input.isEmpty() || input.length() > 1) {
                    throw new IllegalArgumentException();
                } else {
                    option = Integer.parseInt(input);
                }
                difficulty = Difficulty.values()[option];
                switch (difficulty) {
                    case BACK:
                        loop = false;
                        break;
                    case EASY:
                        diff = 20;
                        loop = false;
                        break;
                    case MEDIUM:
                        diff = 50;
                        loop = false;
                        break;
                    case HARD:
                        diff = 100;
                        loop = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println(Colours.RED + "Please enter a valid option");
                keyboard.nextLine();
            }
        }
        return diff;
    }

    public static void menu(){
        Scanner keyboard = new Scanner(System.in);
        Database database = new Database();

        Menu menu;
        boolean loop = true;
        int option1;

        while (loop) {
            printMenuOptions();
            try {
                String input = keyboard.nextLine();
                if (input.isEmpty() || input.length() > 1) {
                    throw new IllegalArgumentException();
                } else {
                    option1 = Integer.parseInt(input);
                }
                menu = Menu.values()[option1];
                switch (menu) {
                    case QUIT:
                        System.exit(0);
                        loop = false;
                        break;
                    case PLAY:
                        play();
                        loop = false;
                        break;
                    case LEADERBOARDS:
                        database.leaderboards();
                        break;
                }
            } catch (Exception e) {
                System.out.println(Colours.RED + "Please enter a valid option");
            }
        }
    }

    private static void printDifficultyOptions() {
        System.out.println("\n Options to select");
        for (int i = 0; i < Difficulty.values().length; i++) {
            System.out.println("\t" + Colours.BLUE + i + ". " + Difficulty.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Enter a number to select option (enter 0 to quit):>");
    }
    private static void printMenuOptions() {
        System.out.println("\n Options to select");
        for (int i = 0; i < Menu.values().length; i++) {
            System.out.println("\t" + Colours.BLUE + i + ". " + Menu.values()[i].toString() + Colours.RESET);
        }
        System.out.println("Enter a number to select option (enter 0 to quit):>");
    }
}


