package app;

import java.util.Scanner;
import Operations.Listing;
import Operations.User;

@SuppressWarnings("resource")
public class ListingDashboard {

    public static boolean listingDashboardCommandHandler(String cmd, User user, Listing listing) {
        switch (cmd) {
            case "1":
                // check if renter has rented the particular listing ever
                break;
            case "2":
                // check if renter has rented the particular listing ever
                break;
            case "3":
                // check if renter has stayed in owner's property ever
            case "exit":
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return true;
    }

    public static void listingDashboardView(User user, Listing listing) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;
        while (true) {

            // ADD LISTING CORRELATION!!!
            // add ability to review as a host
            // add ability to review a renter by host
            // etc...
            System.out.println("Welcome to the listing! Here is the info:" + "\n\n");
            System.out.println("ID: " + listing.listingID);
            // System.out.println("status: " + listing.status);
            // System.out.println("Start Date: " + listing.startDate);
            // System.out.println("End Date: " + listing.endDate);
            System.out.println("Price Per Night: " + listing.pricePerNight);
            System.out.print("Property ID: " + listing.propertyID);
            System.out.print(", Owner ID: " + listing.posterID);
            System.out.println(", Currency ID: " + listing.currencyID + "\n");

            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Add review for owner as a renter");
            System.out.println("2: Add review for property as a renter");
            System.out.println("3: Add review for renter as a owner");
            System.out.println("exit: Go to User Dashboard");
            command = input.nextLine(); // Read user input
            if (!listingDashboardCommandHandler(command, user, listing)) {
                break;
            }
        }
    }

}
