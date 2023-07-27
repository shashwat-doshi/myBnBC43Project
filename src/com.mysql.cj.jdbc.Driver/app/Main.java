package app;

import java.sql.*;
import java.util.Scanner;
import Operations.User;
import Operations.Property;
import Operations.Payment;
import java.sql.SQLException;
import Operations.Listing;
public class Main {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/myBnBC43Project";

    public static boolean commandHandler(String cmd, Connection conn) {
        Scanner input = new Scanner(System.in);
        switch (cmd) {
            case "1":
                User newUser = new User(conn);
                newUser.getUserInfo(conn, newUser.userID);
                break;
            case "2":
                int userIDLogIn;
                System.out.println("Please enter your credentials (USER ID):");
                try {
                    userIDLogIn = input.nextInt();
                    input.nextLine();
                    try {
                        User currentUser = new User(conn, userIDLogIn);
                        System.out.println("Signed in as User " + userIDLogIn);
                        System.out.println("User " + userIDLogIn + "'s Profile:\n");
                        currentUser.getUserInfo(conn, userIDLogIn);
                    } catch (Exception e) {
                        System.out.println(
                                "User " + userIDLogIn + " does not exist in the database. Cannot retrieve user!");
                        System.out.println(e);
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input!");
                    System.out.println(e);
                }
                break;
            case "3":
                int userIDDelete;
                while (true) {
                    System.out.println("Please enter the userID of the user you wish to delete:");
                    try {
                        userIDDelete = input.nextInt();
                        input.nextLine();
                        try {
                            User deleteUser = new User(conn, userIDDelete);
                            deleteUser.deleteUserRecord(conn, userIDDelete);
                            deleteUser = null;
                        } catch (Exception e) {
                            System.out.println("Cannot delete user!");
                            System.out.println(e);
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid input!");
                        System.out.println(e);
                    }
                }
            case "4":
                Property.createNewProperty(conn);
                break;
            case "5":
                Listing.createNewListing(conn);
                break;
            case "6":
                Listing.deleteListing(conn);
                break;
            case "7":
                Payment.createNewPayment(conn);
                break;
            case "exit":
                input.close();
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
        input.close();
        return true;

    }

    public static void main(String[] args) throws ClassNotFoundException {
        // Register JDBC driver
        Class.forName(dbClassName);
        // Database credentials
        final String USER = "root";
        final String PASS = "password";
        System.out.println("Connecting to database...");
        String command;

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            System.out.println("Successfully connected to MySQL!");
            Scanner mainInput = new Scanner(System.in); // Create a Scanner object
            System.out.println("\nWelcome to MyBnB!");
            while (true) {
                // mainInput.nextLine();
                System.out.println("\nPlease select one of the following options:\n\n" +
                        "1: Sign up as a user\n" +
                        "2: Sign in as a user\n" +
                        "3: Delete user\n" +
                        "4: Create a property\n"+
                        "5: Create a listing\n"+
                        "6: Delete a listing\n"+
                        "7: Create a payment\n"+
                        "exit: To exit the application\n\n" +
                        "Please enter input to continue...");
                command = mainInput.nextLine(); // Read user input
                if (!commandHandler(command, conn)) {
                    break;
                }
            }

            System.out.println("Closing connection...");
            mainInput.close();
            conn.close();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.err.println("Connection error occured!");
        }
    }

}
