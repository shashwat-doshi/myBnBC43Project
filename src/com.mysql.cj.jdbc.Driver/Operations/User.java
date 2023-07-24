package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class User {
    public int createUser(Connection conn) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        Integer adminChoice;
        LocalDate dob;
        int candidateID = 0;
        ResultSet rs = null;

        System.out.println("Before you get started, let us create your profile...\n");

        System.out.println("Enter your first name");
        String fname = input.nextLine();
        System.out.println("Enter your last name");
        String lname = input.nextLine();
        System.out.println("Are you an admin? 1 - yes, 0 - no");
        while (true) {
            try {
                adminChoice = input.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        boolean isAdmin = adminChoice == 1 ? true : false;
        input.nextLine(); // need to read \n after nextInt() is called
        System.out.println("Enter your SIN Number");
        String SIN = input.nextLine();
        System.out.println("Enter your Address");
        String addr = input.nextLine();
        System.out.println("Enter your date of birth (in yyyy-mm-dd)");
        while (true) {
            try {
                String userDob = input.nextLine();
                dob = LocalDate.parse(userDob);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }
        System.out.println("Enter your occupation");
        String occupation = input.nextLine();

        try {
            // Statement myStmt = conn.createStatement();
            String sql = "INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, isAdmin, occupation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, SIN);
            preparedStatement.setString(2, addr);
            preparedStatement.setObject(3, dob); // check
            preparedStatement.setString(4, fname);
            preparedStatement.setString(5, lname);
            preparedStatement.setBoolean(6, isAdmin);
            preparedStatement.setString(7, occupation);

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected == 1) {
                System.out.println("One row affected!! damn!");

                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    candidateID = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        input.close();

        return candidateID;

    }
}
