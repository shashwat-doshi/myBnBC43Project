package HostToolkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import app.Main;

public class SuggestAmenity {
    public static void SuggestAmenityForProperty(String city) {
        try {
            String sqlStatement = "SELECT COUNT(*), a.amenityType " +
                    "FROM Offers o " +
                    "INNER JOIN Amenity a ON a.amenityID  = o.amenityID " +
                    "INNER JOIN Property p ON p.propertyID = o.propertyID " +
                    "WHERE p.city = ? " +
                    "GROUP BY o.amenityID " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 3; ";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, city);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String amenityType = rs.getString(2);
                System.out.println("We suggest you add amenity " + amenityType);
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public static Map<Integer, Integer> suggestIncreaseInPrice(String city) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        try {
            for (int i = 1; i <= 10; i++) {
                String dropViewIfExists = "DROP VIEW IF EXISTS PropertiesWithoutAmenity";
                PreparedStatement preparedStatement = Main.conn.prepareStatement(dropViewIfExists);
                preparedStatement.execute();
                String createView = "CREATE VIEW PropertiesWithoutAmenity AS " +
                        "(SELECT * " +
                        "FROM Property p " +
                        "WHERE NOT EXISTS(SELECT * " +
                        "FROM Property p2 " +
                        "INNER JOIN Offers o ON p2.propertyID = o.propertyID " +
                        "WHERE o.amenityID = " + i + " AND p2.propertyID = p.propertyID))";
                preparedStatement = Main.conn.prepareStatement(createView);
                preparedStatement.execute();
                String getDiffInPrice = "SELECT (T2.priceWithAmenity - T1.priceWithoutAmenity) as diff, T1.city " +
                        "FROM " +
                        "(SELECT AVG(l.pricePerNight/capacity) as priceWithoutAmenity, p.city " +
                        "FROM Listing l " +
                        "INNER JOIN PropertiesWithoutAmenity p ON l.propertyID = p.propertyID " +
                        "INNER JOIN (SELECT capacity, r.propertyID  FROM Room r " +
                        "UNION ALL " +
                        "SELECT capacity, ha.propertyID  FROM HotelApartment ha " +
                        "UNION ALL " +
                        "SELECT capacity, h.propertyID FROM House h) AS t ON l.propertyID = t.propertyID " +
                        "WHERE p.city = '" + city + "') as T1 " +
                        ", " +
                        "(" +
                        "SELECT AVG(l.pricePerNight/capacity) as priceWithAmenity, p.city " +
                        "FROM Listing l " +
                        "INNER JOIN (SELECT * FROM Property EXCEPT SELECT * FROM PropertiesWithoutAmenity) p ON l.propertyID = p.propertyID "
                        +
                        "INNER JOIN (SELECT capacity, r.propertyID  FROM Room r " +
                        "UNION ALL " +
                        "SELECT capacity, ha.propertyID  FROM HotelApartment ha " +
                        "UNION ALL " +
                        "SELECT capacity, h.propertyID FROM House h) AS t ON l.propertyID = t.propertyID " +
                        "WHERE p.city = '" + city + "') as T2 ";
                preparedStatement = Main.conn.prepareStatement(getDiffInPrice);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    rs.close();
                    preparedStatement.close();
                    // System.out.println("No results found with the given input!\n");
                } else {
                    int differenceInPrice;
                    System.out.println("Here are the listings according to the given input: \n");
                    System.out.println("HostID\t\tCity\t\tCountry\n");
                    do {
                        differenceInPrice = rs.getInt("diff");
                        // System.out.println(differenceInPrice + "\t\t" + inputCity);
                        map.put(i, differenceInPrice);
                    } while (rs.next());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return map;

    }
}
