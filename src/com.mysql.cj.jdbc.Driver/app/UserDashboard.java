package app;

import java.util.Scanner;

import Operations.Listing;
import Operations.User;

@SuppressWarnings("resource")
public class UserDashboard {

    public static boolean userDashboardCommandHandler(String cmd, User user) {
        Scanner input = new Scanner(System.in);
        int listingID;
        switch (cmd) {
            case "1":
                System.out.println("Enter the listing ID of the listing you wish to view:");
                while (true) {
                    try {
                        listingID = input.nextInt();
                        input.nextLine();
                        Listing listing = Listing.getListingByListingID(listingID);
                        ListingDashboard.listingDashboardView(user, listing);
                        break;
                    } catch (Exception e) {
                        System.out.println("Rating must be between 1 and 5 (included)! Choose again...");
                        input.nextLine();
                    }
                }
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

            // ADD LISTING CORRELATION!!!
            // add ability to review as a host
            // add ability to review a renter by host
            // etc...
            System.out.println("Welcome " + user.fname + "! Your ID is: " + user.userID + "\n\n");
            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Select a listing");
            System.out.println("exit: Log out and go to main menu");
            command = input.nextLine(); // Read user input
            if (!userDashboardCommandHandler(command, user)) {
                break;
            }
        }
    }

}
