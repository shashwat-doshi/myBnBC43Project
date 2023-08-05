package Reports;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import app.Main;

@SuppressWarnings("resource")
public class RenterReports {
    public static void reportWithinDates (Timestamp startDate, Timestamp endDate) {
        try {
            String sqlQuery = "SELECT Count(*), b.renterID " +
                                "FROM Booking b " +
                                "WHERE (b.startDate BETWEEN ? AND ? OR b.endDate BETWEEN ? AND ?) AND b.bookingStatus = 'confirmed' " +
                                "GROUP BY b.renterID " +
                                " ORDER BY Count(*) DESC";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, startDate);
            preparedStatement.setTimestamp(2, endDate);
            preparedStatement.setTimestamp(3, startDate);
            preparedStatement.setTimestamp(4, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Reporting users with most bookings between " + startDate.toString() + " and " + endDate.toString());

            while (rs.next()) {
                int countResult = rs.getInt(1);
                int bookingID = rs.getInt(2);
                System.out.println("Count " + countResult + " renterID " + bookingID);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void reportWithinDatesPerCity (Timestamp startDate, Timestamp endDate) {
        try {
            String sqlQuery = "SELECT Count(*), b.renterID, p.city " +
                    "FROM Booking b " +
                    "INNER JOIN Listing l ON b.listingID = l.listingID " + //
                    "INNER JOIN Property p ON l.propertyID = p.propertyID " + //
                    "WHERE (b.startDate BETWEEN ? AND ? OR b.endDate BETWEEN ? AND ?) AND b.bookingStatus = 'confirmed' " + //
                    "GROUP BY b.renterID, p.city  \n" + //
                    "HAVING COUNT(*) >= 2\n" + //
                    "ORDER BY Count(*) DESC;";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, startDate);
            preparedStatement.setTimestamp(2, endDate);
            preparedStatement.setTimestamp(3, startDate);
            preparedStatement.setTimestamp(4, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Reporting users with most bookings between " + startDate.toString() + " and " + endDate.toString());

            while (rs.next()) {
                int countResult = rs.getInt(1);
                int bookingID = rs.getInt(2);
                String cityResult = rs.getString(3);
                System.out.println("Count " + countResult + " RenterID " + bookingID + " City " + cityResult);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void reportWithinDatesPrompt(Boolean perCity) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
         System.out.println("Enter Start Date for report yyyy-mm-dd");
        Timestamp startDate, endDate;
        while (true) {
            String startDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(startDateString);
                startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }

        System.out.println("Enter End date for report yyyy-mm-dd");
        while (true) {
            String endDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(endDateString);
                endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
                break;
            } catch (Exception e) {
                System.out.println("Please try again\n");
            }
        }
        if (!perCity){
            reportWithinDates(startDate, endDate);
        }
        else {
            reportWithinDatesPerCity(startDate, endDate);
        }
    }
}
