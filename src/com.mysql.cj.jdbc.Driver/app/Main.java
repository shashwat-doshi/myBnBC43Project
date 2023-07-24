package app;

import java.sql.*;
import java.util.Scanner;
import Operations.User;

public class Main {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://127.0.0.1/myBnBC43Project";

    public static boolean commandHandler(String cmd, Connection conn) {
        switch (cmd) {
            case "1":
                User newUser = new User(conn);
                System.out.println("User info: " + newUser.userID);
                break;
            case "2":
                break;

            case "exit":
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
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
            System.out.println("Welcome to MyBnB!");
            while (true) {
                // mainInput.nextLine();
                System.out.println("Please select one of the following options:\n\n" +
                        "1: Sign up as a user\n" +
                        "2: Delete user\n" +
                        "3: Sign in as a user\n" +
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
