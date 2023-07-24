package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class User {

    public String fname, lname, SIN, userAddress, occupation;
    public boolean isAdmin;
    public LocalDate dob;
    public int userID;

    public User(Connection conn) {
        int userID = createUser(conn);
        this.userID = userID;
    }

    public User(Connection conn, int UserID) {

    }

    public void setUserInfo() {
        Integer adminChoice;

        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Before you get started, let us create your profile...\n");

        System.out.println("Enter your first name");
        this.fname = input.nextLine();
        System.out.println("Enter your last name");
        this.lname = input.nextLine();
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
        this.isAdmin = adminChoice == 1 ? true : false;
        input.nextLine(); // need to read \n after nextInt() is called
        System.out.println("Enter your SIN Number");
        this.SIN = input.nextLine();
        System.out.println("Enter your Address");
        this.userAddress = input.nextLine();
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
        this.occupation = input.nextLine();

        input.close();
    }

    public int createUser(Connection conn) {

        setUserInfo();

        int candidateID = 0;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, isAdmin, occupation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.SIN);
            preparedStatement.setString(2, this.userAddress);
            preparedStatement.setObject(3, this.dob); // check
            preparedStatement.setString(4, this.fname);
            preparedStatement.setString(5, this.lname);
            preparedStatement.setBoolean(6, this.isAdmin);
            preparedStatement.setString(7, this.occupation);

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
            System.out.println("Could not create user! Try again...");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Could not create user! Try again...");
            }
        }

        return candidateID;

    }
}
