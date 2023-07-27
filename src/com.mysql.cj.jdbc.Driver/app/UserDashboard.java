package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                        if (listing != null) {
                            ListingDashboard.listingDashboardView(user, listing);
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect Listing ID! Please try again...");
                        input.nextLine();
                    }
                }
                break;
            case "2":
                try {
                    String sql = "SELECT * FROM User WHERE userID = ?";
                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    preparedStatement.setInt(1, user.userID);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        System.out.println("\nUser " + user.userID + " does not exist!");
                    }

                    System.out.println("User " + user.userID + "'s Profile:");
                    do {
                        // Display values
                        System.out.print("ID: " + rs.getInt("userID"));
                        System.out.print(", fname: " + rs.getString("firstName"));
                        System.out.print(", lname: " + rs.getString("lastName"));
                        System.out.print(", SIN: " + rs.getString("SIN"));
                        System.out.print(", userAddress: " + rs.getString("userAddress"));
                        System.out.print(", occupation: " + rs.getString("occupation"));
                        System.out.print(", DOB: " + rs.getDate("DOB").toLocalDate());
                        System.out.print(", age: " + rs.getInt("age"));
                        System.out.println(", isAdmin: " + rs.getBoolean("isAdmin"));
                    } while (rs.next());
                    rs.close();
                    preparedStatement.close();
                } catch (Exception e) {
                    System.out.println("Unable to retrieve user from the given user ID, please try again!");
                    System.out.println(e.getMessage());
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
            System.out.println("\nWelcome " + user.fname + "! Your ID is: " + user.userID + "\n");
            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Select a listing");
            System.out.println("2: Show profile");
            System.out.println("exit: Log out and go to main menu");
            command = input.nextLine(); // Read user input
            if (!userDashboardCommandHandler(command, user)) {
                break;
            }
        }
    }

}
