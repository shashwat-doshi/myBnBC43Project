package app;

import java.util.Scanner;

@SuppressWarnings("resource")
public class QueryDashboard {

    public static void findListingsByLocation(double locationLatitude, double locationLongitude, float searchDistance) {

    }

    public static void queryDashboardHandler(String cmd) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        double locationLatitude, locationLongitude;
        float searchDistance = 5;

        switch (cmd) {
            case "1":
                System.out.println("Please enter the latitude of the location you want to search...");
                while (true) {
                    try {
                        locationLatitude = input.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect latitude input! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();
                System.out.println("Please enter the longitude of the location you want to search...");
                while (true) {
                    try {
                        locationLongitude = input.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect longitude input! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();

                System.out.println("Default search distance: 5km. Do you want to modify it? (Y/N)");
                String choice;
                while (true) {
                    try {
                        choice = input.nextLine();
                        if (choice == "Y") {
                            System.out.println("Please enter the new search distance:");
                            searchDistance = input.nextFloat();
                            break;
                        } else if (choice == "N") {
                            System.out.println("Default search distance = 5km selected.");
                            break;
                        } else {
                            throw new Exception("Incorrect choice! Please try again!");
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect choice! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();

                findListingsByLocation(locationLatitude, locationLongitude, searchDistance);

                break;
        }
    }

    public static void queryDashboardInterface() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;

        while (true) {
            System.out.println("Please enter the appropriate query you wish to perform...\n");
            System.out.println("1: Search for listings by location\n" +
                    "2: Sign in as a user\n" +
                    "3: Delete user\n" +
                    "4: Perform queries on the database\n" +
                    "exit: Go back to main menu\n\n");
            command = input.nextLine(); // Read user input
            queryDashboardHandler(command);
        }
    }

}
