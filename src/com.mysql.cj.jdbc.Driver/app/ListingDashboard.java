package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import Operations.Listing;
import Operations.User;
import Operations.Review;

@SuppressWarnings("resource")
public class ListingDashboard {

    public static int checkIfRented(User user, Listing listing) {
        try {
            String sql = "SELECT bookingID, listingID, renterID FROM Booking " +
                    "WHERE listingID = ? AND renterID = ?";

            // what if this returns 2 rows? (if user makes 2 bookings for the same listing)
            // 20-23 and 27-30

            // ask user which booking id do they want to put a review for? (display both
            // booking ids and tell them to choose)

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, listing.listingID);
            preparedStatement.setInt(2, user.userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                System.out.println("Renter has no bookings for this listing!");
                return -1;
            } else {
                return rs.getInt("bookingID");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static boolean isReviewExists(User user, Listing listing, int bookingID) {
        try {
            String sql = "SELECT * FROM Booking WHERE bookingID = ? AND reviewForOwner IS NOT NULL";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                rs.close();
                preparedStatement.close();
                return true; // we can add a review if there exists no reviewForOwner
            } else {
                System.out.println("Review for owner in the current booking already exists!");
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve booking from the given booking ID, please try again!");
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public static boolean listingDashboardCommandHandler(String cmd, User user, Listing listing) {
        switch (cmd) {
            case "1":
                // check if renter has rented the particular listing ever
                int bookingID = checkIfRented(user, listing);
                boolean isReviewExists = isReviewExists(user, listing, bookingID);
                if (bookingID != -1 && isReviewExists) {
                    Review review = new Review(user);
                    if (review != null) {
                        try {
                            String sql = "UPDATE Booking " +
                                    "SET reviewForOwner = ? " +
                                    "WHERE bookingID = ?";
                            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql,
                                    Statement.RETURN_GENERATED_KEYS);

                            preparedStatement.setInt(1, review.reviewID);
                            preparedStatement.setInt(2, bookingID);

                            int rowAffected = preparedStatement.executeUpdate();
                            if (rowAffected == 1) {
                                System.out.println("Updated reviewForOwner by renter foreign key in booking table");
                            }
                            preparedStatement.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not update booking table with reviewForOwner ID! Try again...");
                        }

                    }
                }
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
            System.out.println("\nWelcome to the listing! Here is the info:" + "\n");
            System.out.println("ID: " + listing.listingID);
            System.out.println("status: " + listing.listingStatus);
            System.out.println("Start Date: " + listing.startDate);
            System.out.println("End Date: " + listing.endDate);
            System.out.println("Price Per Night: " + listing.pricePerNight);
            System.out.print("Property ID: " + listing.propertyID);
            System.out.print(", Owner ID: " + listing.posterID);
            System.out.println(", Currency ID: " + listing.currencyID + "\n");

            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Add review for owner as a renter");
            System.out.println("2: Add review for property as a renter");
            System.out.println("3: Add review for renter as a owner");
            System.out.println("4: Book a listing");
            System.out.println("exit: Go to User Dashboard");
            command = input.nextLine(); // Read user input
            if (!listingDashboardCommandHandler(command, user, listing)) {
                break;
            }
        }
    }

}
