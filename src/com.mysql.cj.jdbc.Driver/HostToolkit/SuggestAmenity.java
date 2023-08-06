package HostToolkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import app.Main;

public class SuggestAmenity {
    public static void SuggestAmenityForProperty (String city) {
        try {
            String sqlStatement = "SELECT COUNT(*), a.amenityType " +
                                    "FROM Offers o " +
                                    "INNER JOIN Amenity a ON a.amenityID  = o.amenityID " +
                                    "INNER JOIN Property p ON p.propertyID = o.propertyID " +
                                    "WHERE p.city = ? " +
                                    "GROUP BY o.amenityID " +
                                    "ORDER BY COUNT(*) DESC " +
                                    "LIMIT 3; ";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, city);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String amenityType = rs.getString(2);
                System.out.println("We suggest you add amenity " + amenityType);
            }
        }
        catch (Exception e) {
            System.out.println(e);

        }
    }
}
