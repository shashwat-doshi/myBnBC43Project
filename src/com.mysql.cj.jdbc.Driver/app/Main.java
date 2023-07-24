package app;

import java.sql.*;
import java.util.Scanner;
import Operations.User;
import java.sql.SQLException;

public class Main {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://127.0.0.1/myBnBC43Project";

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
        final String PASS = "alpapiyush";
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
                        "exit: To exit the application\n\n" +
                        "Please enter input to continue...");
                command = mainInput.nextLine(); // Read user input
                if (!commandHandler(command, conn)) {
                    break;
                }
            }

            // // Execute a query
            // System.out.println("Preparing a statement...");
            // Statement stmt = conn.createStatement();
            // String sql = "SELECT * FROM Sailors;";
            // ResultSet rs = stmt.executeQuery(sql);

            // // STEP 5: Extract data from result set
            // while (rs.next()) {
            // // Retrieve by column name
            // int sid = rs.getInt("sid");
            // String sname = rs.getString("sname");
            // int rating = rs.getInt("rating");
            // int age = rs.getInt("age");

            // // Display values
            // System.out.print("ID: " + sid);
            // System.out.print(", Name: " + sname);
            // System.out.print(", Rating: " + rating);
            // System.out.println(", Age: " + age);
            // }

            System.out.println("Closing connection...");
            mainInput.close();
            // rs.close();
            // stmt.close();
            conn.close();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.err.println("Connection error occured!");
        }
    }

}
