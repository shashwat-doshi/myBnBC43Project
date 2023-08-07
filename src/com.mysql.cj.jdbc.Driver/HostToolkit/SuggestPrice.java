package HostToolkit;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import app.Main;

public class SuggestPrice {
    public static void SuggestPriceBasedOnProperty (String city, int capacity) {
        try {
            String sqlStatement = "SELECT AVG(l.pricePerNight/capacity), p.city " +
                                    "FROM Listing l " +
                                    "INNER JOIN Property p ON l.propertyID = p.propertyID " +
                                    "INNER JOIN (SELECT capacity, r.propertyID  FROM Room r " +
                                    "UNION ALL " +
                                    "SELECT capacity, ha.propertyID FROM HotelApartment ha " +
                                    "UNION ALL " +
                                    "SELECT capacity, h.propertyID FROM House h) AS t ON l.propertyID = t.propertyID " +
                                    "WHERE p.city = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, city);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Float avgPrice = rs.getFloat(1);
                System.out.println("We suggest you price your listing around " + avgPrice * capacity);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static Integer getCapacityOfProperty (Integer propertyID) {
        try {
            String sqlStatement = "SELECT capacity " +
                                    "FROM Property p " +
                                    "INNER JOIN (SELECT capacity, r.propertyID  FROM Room r " +
                                    "UNION ALL " +
                                    "SELECT capacity, ha.propertyID  FROM HotelApartment ha " +
                                    "UNION ALL " +
                                    "SELECT capacity, h.propertyID FROM House h) AS t ON p.propertyID = t.propertyID " +
                                    " WHERE p.propertyID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, propertyID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt(1);
                return capacity;
            }
            return -1;
        }
        catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}
