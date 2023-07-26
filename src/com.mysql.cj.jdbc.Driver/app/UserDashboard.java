package app;

import java.util.Scanner;

import Operations.User;
import Operations.Review;

public class UserDashboard {

    public static boolean userDashboardCommandHandler(String cmd, User user) {
        switch (cmd) {
            case "1":
                Review review = new Review(user);
                System.out.println("finished review");
                break;
            case "exit":
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return true;
    }

    public static void userDashboardInterface(User user) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;
        while (true) {
            System.out.println("Welcome " + user.fname + "! Your ID is: " + user.userID + "\n\n");
            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Add a review");
            System.out.println("exit: Log out and go to main menu");
            command = input.nextLine(); // Read user input
            if (!userDashboardCommandHandler(command, user)) {
                break;
            }
        }
        input.close();
    }

}
