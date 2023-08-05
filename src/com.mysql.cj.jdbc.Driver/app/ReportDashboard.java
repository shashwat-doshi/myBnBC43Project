package app;

import java.util.Scanner;

import static Queries.ReportQueries.*;

@SuppressWarnings("resource")
public class ReportDashboard {

    public static boolean reportDashboardHandler(String cmd) {
        // Scanner input = new Scanner(System.in); // Create a Scanner object
        switch (cmd) {
            case "1":
                getNoOfBookingsByCity();
                break;
            case "2":
                getNoOfBookingsByPCode();
                break;

            case "exit":
                return false;
            default:
                System.out.println("Incorrect choice! Please try again...\n");
        }
        return true;
    }

    public static void reportDashboardInterface() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;

        while (true) {
            System.out.println("Please enter the appropriate choice you wish to run a report for...\n");
            System.out.println("1: Get total number of bookings in a specific date range by city\n" +
                    "2: Get total number of bookings in a specific date range in a city by postal code\n" +
                    "3: Show listings ordered by price\n" +
                    "4: Search for listings by postal code\n" +
                    "exit: Go back to main menu\n");
            command = input.nextLine(); // Read user input
            if (!reportDashboardHandler(command)) {
                break;
            }
        }
    }

}
