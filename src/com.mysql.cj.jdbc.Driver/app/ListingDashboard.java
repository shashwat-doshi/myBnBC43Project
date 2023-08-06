package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import Operations.Listing;
import Operations.User;
import Operations.Review;
import Operations.Booking;
@SuppressWarnings("resource")
public class ListingDashboard {

    public static int getNumberOfBookingsPerListing(User user, Listing listing) {
        try {
            String sql = "SELECT COUNT(*) FROM Booking " +
                    "WHERE listingID = ? AND renterID = ?";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, listing.listingID);
            preparedStatement.setInt(2, user.userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return 0;
            } else {
                return rs.getInt("COUNT(*)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int checkIfRented(User user, Listing listing) {
        try {
            Scanner input = new Scanner(System.in); // Create a Scanner object
            int selectedBookingID = -1;

            int noOfBookingPerListing = getNumberOfBookingsPerListing(user, listing);
            if (noOfBookingPerListing == 0) {
                System.out.println("Renter has no bookings for this listing!");
                return -1;
            }

            int bookingIDList[] = new int[noOfBookingPerListing];
            int i = 0;

            String sql = "SELECT bookingID, listingID, renterID FROM Booking " +
                    "WHERE listingID = ? AND renterID = ?";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, listing.listingID);
            preparedStatement.setInt(2, user.userID);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                System.out.println("Renter has no bookings for this listing!");
                return -1;
            } else {
                do {
                    bookingIDList[i] = rs.getInt("bookingID");
                } while (rs.next());

                System.out.println("Which booking ID do you want to put a review for?\n");
                for (int x = 0; x < noOfBookingPerListing; x++) {
                    if (x == noOfBookingPerListing - 1) { // if its the last bookingID, no need to put a comma
                        System.out.print(bookingIDList[x] + "\n\n");
                    } else {
                        System.out.print(bookingIDList[x] + ", ");
                    }
                }

                while (true) {
                    try {
                        selectedBookingID = input.nextInt();
                        int flag = 0;
                        for (int j = 0; j < noOfBookingPerListing; j++) {
                            if (bookingIDList[j] == selectedBookingID) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            throw new Exception("Incorrect Booking ID! Please try again...\n");
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect Booking ID! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();

                return selectedBookingID;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static boolean isReviewExists(User user, Listing listing, int bookingID, String reviewType) {
        try {
            String sql = "SELECT * FROM Booking WHERE bookingID = ? AND " + reviewType + " IS NOT NULL";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                rs.close();
                preparedStatement.close();
                return true; // we can add a review if there exists no reviewForOwner
            } else {
                System.out.println(reviewType + " in the current booking already exists!");
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

    public static boolean checkIfHosted(User host, Listing listing) {
        try {
            String sql = "SELECT * FROM Listing WHERE listingID = ? AND posterID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, listing.listingID);
            preparedStatement.setInt(2, host.userID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("You are not a host for this listing! Cannot create review!");
                rs.close();
                preparedStatement.close();
            } else {
                rs.close();
                preparedStatement.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("Unable to retrieve booking from the given booking ID, please try again!");
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public static int checkRenterStay(User host, Listing listing, int renterID) {
        try {
            if (host.userID == renterID) {
                System.out.println("Invalid input! Host and renter cannot be the same!");
                return -1;
            }
            String sql = "SELECT Booking.renterID, Booking.listingID, Booking.bookingID, Listing.posterID " +
                    "FROM Booking " +
                    "INNER JOIN Listing " +
                    "ON Booking.listingID = Listing.listingID " +
                    "WHERE Booking.renterID = ? AND Booking.listingID = ? AND Listing.posterID = ?";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, renterID);
            preparedStatement.setInt(2, listing.listingID);
            preparedStatement.setInt(3, host.userID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("The renter has never stayed in this listing! Cannot create a review!");
                rs.close();
                preparedStatement.close();
            } else {
                // int bookingID = rs.getInt("bookingID");
                Scanner input = new Scanner(System.in); // Create a Scanner object
                int selectedBookingID = -1;

                int noOfBookingPerListing = getNumberOfBookingsPerListing(host, listing);
                if (noOfBookingPerListing == 0) {
                    System.out.println("Renter has no bookings for this listing!");
                    return -1;
                }

                int bookingIDList[] = new int[noOfBookingPerListing];
                int i = 0;

                do {
                    bookingIDList[i] = rs.getInt("bookingID");
                } while (rs.next());

                System.out.println("Which booking ID do you want to put a review for?\n");
                for (int x = 0; x < noOfBookingPerListing; x++) {
                    if (x == noOfBookingPerListing - 1) { // if its the last bookingID, no need to put a comma
                        System.out.print(bookingIDList[x]);
                    } else {
                        System.out.print(bookingIDList[x] + ", ");
                    }
                }

                while (true) {
                    try {
                        selectedBookingID = input.nextInt();
                        int flag = 0;
                        for (int j = 0; j < noOfBookingPerListing; j++) {
                            if (bookingIDList[j] == selectedBookingID) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            throw new Exception();
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect Booking ID! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine(); // read \n character after nextInt() is executed
                rs.close();
                preparedStatement.close();
                return selectedBookingID;
            }
        } catch (Exception e) {
            System.out.println("Unable to verify if renter has stayed in owner's listing, please try again!");
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    public static void viewListingInfo(Listing listing) {
        System.out.println("Listing information:");
        System.out.println("ID: " + listing.listingID);
        System.out.println("status: " + listing.listingStatus);
        System.out.println("Start Date: " + listing.startDate);
        System.out.println("End Date: " + listing.endDate);
        System.out.println("Price Per Night: " + listing.pricePerNight);
        System.out.print("Property ID: " + listing.propertyID);
        System.out.print(", Owner ID: " + listing.posterID);
        System.out.println(", Currency ID: " + listing.currencyID + "\n");
    }

    public static boolean listingDashboardCommandHandler(String cmd, User user, Listing listing) {
        switch (cmd) {
            case "1":
                // check if renter has rented the particular listing ever
                int bookingID = checkIfRented(user, listing);
                boolean isReviewExists = isReviewExists(user, listing, bookingID, "reviewForOwner");
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
                                System.out.println("Updated reviewForOwner foreign key in booking table");
                            }
                            preparedStatement.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out
                                    .println("Could not update booking table with reviewForProperty ID! Try again...");
                        }

                    }
                }
                break;
            case "2":
                // check if renter has rented the particular listing ever
                int bookingIDReviewProperty = checkIfRented(user, listing);
                boolean isReviewExistsProperty = isReviewExists(user, listing, bookingIDReviewProperty,
                        "reviewForProperty");
                if (bookingIDReviewProperty != -1 && isReviewExistsProperty) {
                    Review review = new Review(user);
                    if (review != null) {
                        try {
                            String sql = "UPDATE Booking " +
                                    "SET reviewForProperty = ? " +
                                    "WHERE bookingID = ?";
                            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql,
                                    Statement.RETURN_GENERATED_KEYS);

                            preparedStatement.setInt(1, review.reviewID);
                            preparedStatement.setInt(2, bookingIDReviewProperty);

                            int rowAffected = preparedStatement.executeUpdate();
                            if (rowAffected == 1) {
                                System.out.println("Updated reviewForProperty by renter foreign key in booking table");
                            }
                            preparedStatement.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Could not update booking table with reviewForOwner ID! Try again...");
                        }

                    }
                }
                break;
            case "3":
                // check if renter has stayed in owner's property ever
                boolean hasHosted = checkIfHosted(user, listing);
                if (hasHosted) {
                    System.out.println("Hello host! Enter the Renter ID you wish to put a review for in this listing:");
                    Scanner input = new Scanner(System.in); // Create a Scanner object
                    int renterID = input.nextInt();
                    input.nextLine(); // read the unnecessary \n character

                    int bookingIDReviewRenter = checkRenterStay(user, listing, renterID);
                    boolean isReviewExistsRenter = isReviewExists(user, listing, bookingIDReviewRenter,
                            "reviewForRenter");
                    if (bookingIDReviewRenter != -1 && isReviewExistsRenter) {
                        Review review = new Review(user);
                        if (review != null) {
                            try {
                                String sql = "UPDATE Booking " +
                                        "SET reviewForRenter = ? " +
                                        "WHERE bookingID = ?";
                                PreparedStatement preparedStatement = Main.conn.prepareStatement(sql,
                                        Statement.RETURN_GENERATED_KEYS);

                                preparedStatement.setInt(1, review.reviewID);
                                preparedStatement.setInt(2, bookingIDReviewRenter);

                                int rowAffected = preparedStatement.executeUpdate();
                                if (rowAffected == 1) {
                                    System.out.println("Updated reviewForRenter by owner foreign key in booking table");
                                }
                                preparedStatement.close();
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                System.out.println(
                                        "Could not update booking table with reviewForRenter ID! Try again...");
                            }
                        }
                    }
                }
                break;
            case "4":
                Booking booking = new Booking();
                booking.createNewBooking(user, listing);
                break;
            case "5":
                viewListingInfo(listing);
                break;
            case "6":
            while (true) {
                System.out.println("Enter the booking ID");
                Scanner input = new Scanner(System.in);
                try {
                    int bookingIDView = input.nextInt();
                    input.nextLine();
                    Booking bookingView = Booking.getBookingByBookingID(bookingIDView);
                    if (listing != null && (bookingView.renterID == user.userID || listing.posterID == user.userID)) {
                        BookingDashboard.BookingDashboardInterface(user, listing, bookingView);
                        break;
                    }
                    else{
                        System.out.println("You are not involved with this booking");
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect Booking ID! Please try again...");
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

    public static void listingDashboardView(User user, Listing listing) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;
        while (true) {

            // ADD LISTING CORRELATION!!!
            // add ability to review as a host
            // add ability to review a renter by host
            // etc...
            System.out.println(
                    "\nWelcome to the listing with listing ID " + listing.listingID + "!\n");

            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Add review for owner as a renter");
            System.out.println("2: Add review for property as a renter");
            System.out.println("3: Add review for renter as a owner");
            System.out.println("4: Book a listing");
            System.out.println("5: Show listing information");
            System.out.println("6: Select a Booking");
            System.out.println("exit: Go to User Dashboard");
            command = input.nextLine(); // Read user input
            if (!listingDashboardCommandHandler(command, user, listing)) {
                break;
            }
        }
    }

}
