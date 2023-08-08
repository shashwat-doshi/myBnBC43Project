package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import app.Main;
@SuppressWarnings("resource")
public class Booking {
    public Timestamp startDate, endDate;
    public String bookingStatus;
    public String accomodation;
    public int bookingID;
    public int listingID, renterID, paymentID; // foreign keys
    public Boolean isDateTakenInBooking(Timestamp date, Listing listing) {
        try {
            String checkSql = "SELECT * FROM Booking b WHERE  b.listingID = ? AND ? BETWEEN b.startDate AND b.endDate AND b.bookingStatus = 'confirmed'";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(checkSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, listing.listingID);
            preparedStatement.setTimestamp(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println(e);
            return true;
        }
    }
    public void createNewBooking(User user, Listing listing) {

        int paymentId = Payment.createNewPayment();
        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input Booking information...\n");
        System.out.println("Enter Booking Start date yyyy-mm-dd");
        LocalDateTime startDate;
        while (true) {
            String startDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(startDateString);
                startDate = LocalDateTime.of(localDate, LocalTime.of(15, 0));
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }

        System.out.println("Enter Booking End date yyyy-mm-dd");
        LocalDateTime endDate;
        while (true) {
            String endDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(endDateString);
                endDate = LocalDateTime.of(localDate, LocalTime.of(11, 0));
                break;
            } catch (Exception e) {
                System.out.println("Please try again\n");
                input.nextLine();
            }
        }
        System.out.println("Enter Any Accomodation you need");
        String accomodations = input.nextLine();
        try {
            String insertPropertySql = "INSERT INTO Booking (startDate, endDate, accommodations, listingID, paymentID, renterID)" +
                    "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(insertPropertySql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            preparedStatement.setString(3, accomodations); // check
            preparedStatement.setInt(4, listing.listingID);
            preparedStatement.setInt(5, paymentId);
            preparedStatement.setInt(6, user.userID);

            int rowAffected = preparedStatement.executeUpdate();
            ResultSet rs;
            int insertId = 0;
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    insertId = rs.getInt(1);
                }
            }
            System.out.println("Created booking with id " + insertId);
        } catch (Exception e) {
            System.out.println(e);
            Payment.RollBackPayment(paymentId);
        }
    }

    public static Booking getBookingByBookingID(int bookingID) {
        try {
            String sql = "SELECT * FROM Booking WHERE bookingID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("Booking with listing ID " + bookingID + " does not exist!");
                return null;
            }

            Booking booking = new Booking();

            do {
                booking.listingID = rs.getInt("listingID");
                booking.bookingStatus = rs.getString("bookingStatus");
                booking.startDate = rs.getTimestamp("startDate");
                booking.endDate = rs.getTimestamp("endDate");
                booking.paymentID = rs.getInt("paymentID");
                booking.accomodation = rs.getString("accommodations");
                booking.renterID = rs.getInt("renterID");
                booking.bookingID = rs.getInt("bookingID");
            } while (rs.next());
            rs.close();
            preparedStatement.close();
            return booking;
        } catch (Exception e) {
            System.out.println("Unable to retrieve the booking from the given booking ID!");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void cancelBooking (User user) {
        if (!this.bookingStatus.equals("confirmed")){
            System.out.println("Booking is already cancelled");
            return;
        }
        try {
            String newStatus = user.userID == this.renterID ? "canceled by guest" : "canceled by host";
            String updateSQLStatment = "UPDATE Booking SET bookingStatus = ? WHERE bookingID = ?";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(updateSQLStatment,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, this.bookingID);

            preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        try {
            String newStatusListing = "available";
            String updateSQLStatment = "UPDATE Listing SET listingStatus = ? WHERE listingID = ?";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(updateSQLStatment,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, newStatusListing);
            preparedStatement.setInt(2, this.listingID);

            preparedStatement.executeUpdate();
        }

        catch(Exception e) {
            System.out.println(e);
        }
    }
}
