package Reports;

import java.time.LocalDateTime;
import java.util.Scanner;

import app.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

@SuppressWarnings("resource")
public class CancellationReports {
    public static void cancellationForRenters (Timestamp firstDay, Timestamp lastDay) {
        try {
            String aggregateSql = "SELECT COUNT(*), b.renterID " +
                                    "FROM Booking b " +
                                    "WHERE (b.startDate BETWEEN ? AND ? OR b.endDate BETWEEN ? AND ?) AND b.bookingStatus = 'canceled by guest' " +
                                    "GROUP BY b.renterID " +
                                    "ORDER BY COUNT(*) DESC;";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(aggregateSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, firstDay);
            preparedStatement.setTimestamp(2, lastDay);
            preparedStatement.setTimestamp(3, firstDay);
            preparedStatement.setTimestamp(4, lastDay);

            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Reporting renters with most cancellations between " + firstDay.toString() + " and " + lastDay.toString());

            while (rs.next()) {
                int countResult = rs.getInt(1);
                int renterID = rs.getInt(2);
                System.out.println("Count " + countResult + " RenterID " + renterID);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

     public static void cancellationForHosts (Timestamp firstDay, Timestamp lastDay) {
        try {
            String aggregateSql = "SELECT COUNT(*), b.posterID " +
                                    "FROM Booking b " +
                                    "WHERE (b.startDate BETWEEN ? AND ? OR b.endDate BETWEEN ? AND ?) AND b.bookingStatus = 'canceled by host' " +
                                    "GROUP BY b.posterID " +
                                    "ORDER BY COUNT(*) DESC;";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(aggregateSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, firstDay);
            preparedStatement.setTimestamp(2, lastDay);
            preparedStatement.setTimestamp(3, firstDay);
            preparedStatement.setTimestamp(4, lastDay);

            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Reporting hosts with most cancellations between " + firstDay.toString() + " and " + lastDay.toString());

            while (rs.next()) {
                int countResult = rs.getInt(1);
                int hostID = rs.getInt(2);
                System.out.println("Count " + countResult + " hostID " + hostID);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void cancellationPrompt(Boolean forRenters) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Enter year for input");
        int year = input.nextInt();
        input.nextLine();

        LocalDateTime jan1 = LocalDateTime.of(year, 1, 1, 15, 0, 0);
        LocalDateTime dec31 = LocalDateTime.of(year, 12, 31, 11, 0, 0);
        if (forRenters){
            cancellationForRenters(Timestamp.valueOf(jan1), Timestamp.valueOf(dec31));
        }
        else {
            cancellationForHosts(Timestamp.valueOf(jan1), Timestamp.valueOf(dec31));
        }
    }
}
