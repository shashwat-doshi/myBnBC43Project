package Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import app.Main;

@SuppressWarnings("resource")
public class ReportQueries {

    public static void getNoOfBookingsByCity() {

        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sql = "";
        Timestamp startDate, endDate;
        try {
            System.out.println("Enter the start date for the filter:");
            while (true) {
                String startDateString = input.nextLine();
                try {
                    LocalDate localDate = LocalDate.parse(startDateString);
                    startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect format of the start date! Please try again...");
                }
            }
            System.out.println("Enter the end date for the filter:");
            while (true) {
                String endDateString = input.nextLine();
                try {
                    LocalDate localDate = LocalDate.parse(endDateString);
                    endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect format of the end date! Please try again...");
                }
            }

            if (startDate.compareTo(endDate) < 0) {
                sql = "SELECT p.city, COUNT(*) " +
                        "FROM Booking b " +
                        "INNER JOIN Listing l " +
                        "ON l.listingID = b.listingID " +
                        "INNER JOIN Property p " +
                        "ON l.propertyID = p.propertyID " +
                        "WHERE (UNIX_TIMESTAMP(b.startDate) >= UNIX_TIMESTAMP('" + startDate
                        + "') AND UNIX_TIMESTAMP(b.endDate) <= UNIX_TIMESTAMP('" + endDate + "')) " +
                        "GROUP BY p.city";

                PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    rs.close();
                    preparedStatement.close();
                    System.out.println("No results found with the given input!\n");
                } else {
                    int bookingCount;
                    String bookingCity;
                    System.out.println("Here are the listings according to the given input: \n");
                    int count = 1;
                    System.out.println("City\tCOUNT(*)\n");
                    do {
                        System.out.println("Result " + count++ + ":");
                        bookingCount = rs.getInt("COUNT(*)");
                        bookingCity = rs.getString("city");
                        System.out.println(bookingCity + "\t" + bookingCount);
                    } while (rs.next());
                }
            } else {
                throw new Exception("End date should be greater than start date! Please try again...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
