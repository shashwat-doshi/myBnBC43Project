package app;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION = "jdbc:mysql://127.0.0.1/myBnBC43Project";

    public static boolean commandHandler(String cmd) {
        if (cmd.equals("exit")) {
            return false;
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

        try {
            // Establish connection
            Connection conn = DriverManager.getConnection(CONNECTION, USER, PASS);
            System.out.println("Successfully connected to MySQL!");
            Scanner myObj = new Scanner(System.in); // Create a Scanner object
            System.out.println("Welcome to MyBnB!");
            while (true) {
                System.out.println("Please enter input to continue...");
                String command = myObj.nextLine(); // Read user input
                System.out.println("command is: " + command); // Output user input
                if (!commandHandler(command)) {
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
            myObj.close();
            // rs.close();
            // stmt.close();
            conn.close();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.err.println("Connection error occured!");
        }
    }

}
