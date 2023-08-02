package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import app.Main;

@SuppressWarnings("resource")
public class User {

    public String fname, lname, SIN, userAddress, occupation;
    public LocalDate dob;
    public int userID; // as only one user operates in our app at a time, should we make userID
                       // static??
    public int age;
    public String role; // need this? check -- how else will we identify when a user is a renter and
                        // when are they a host?

    public User() {
        int userID = createUser();
        this.userID = userID;
        setAge(userID);
    }

    public User(int userID) {
        try {
            String sql = "SELECT * FROM User WHERE userID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                this.userID = rs.getInt("userID");
                this.fname = rs.getString("firstName");
                this.lname = rs.getString("lastName");
                this.SIN = rs.getString("SIN");
                this.userAddress = rs.getString("userAddress");
                this.occupation = rs.getString("occupation");
                this.dob = rs.getDate("DOB").toLocalDate();
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve user from the given user ID, please try again!");
            System.out.println(e.getMessage());
        }
    }

    private void setAge(int userID) {
        try {
            String sql = "SELECT age FROM User WHERE userID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                this.age = rs.getInt("age");
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Cannot retrieve the age of the user");
        }
    }

    public void setNewUserInfo() {

        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Before you get started, let us create your profile...\n");

        System.out.println("Enter your first name");
        this.fname = input.nextLine();
        System.out.println("Enter your last name");
        this.lname = input.nextLine();
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
    }

    public boolean isUserExists(int userID) {
        try {
            String sql = "SELECT * FROM User WHERE userID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("\nUser " + userID + " does not exist!");
                return false;
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve user from the given user ID, please try again!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int createUser() {

        setNewUserInfo();

        int candidateID = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO User (SIN, userAddress, DOB, firstName, lastName, occupation) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = Main.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.SIN);
            preparedStatement.setString(2, this.userAddress);
            preparedStatement.setObject(3, this.dob); // check
            preparedStatement.setString(4, this.fname);
            preparedStatement.setString(5, this.lname);
            preparedStatement.setString(6, this.occupation);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
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
                    preparedStatement.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                System.out.println("\nCreated user with User ID: " + candidateID);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return candidateID;
    }

    public void deleteUserRecord(int userID) {
        try {
            String sql = "DELETE FROM User " +
                    "WHERE userID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User " + userID + " successfully deleted");
            } else {
                System.out.println("User does not exist, no record is deleted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
