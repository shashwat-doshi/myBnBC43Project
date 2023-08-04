package app;

import java.util.Scanner;
import Operations.User;
import Operations.Booking;
import Operations.Listing;

@SuppressWarnings("resource")
public class BookingDashboard {

    public static boolean BookingDashboardCommandHandler(String cmd, User user, Listing listing, Booking booking) {
        switch (cmd) {
            case "1":
                booking.cancelBooking(user);
                break;
            case "exit":
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return true;
    }

    public static void BookingDashboardInterface(User user, Listing listing, Booking booking) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;
        while (true) {

            // ADD LISTING CORRELATION!!!
            // add ability to review as a host
            // add ability to review a renter by host
            // etc...
            System.out.println("\nWelcome " + user.fname + "! Your ID is: " + user.userID + "\n");
            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Cancel a Booking");
            System.out.println("exit: Log out and go to main menu");
            command = input.nextLine(); // Read user input
            if (!BookingDashboardCommandHandler(command, user, listing, booking)) {
                break;
            }
        }
    }

}
