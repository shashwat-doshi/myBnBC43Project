package Reports;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import app.Main;

public class PerCountry {
    public static void reportPerCountryCityPostalCode (Boolean country, Boolean city, Boolean postalCode) {
        try {
            String selectStatement = "SELECT COUNT(*), p.country";
            String groupByStatement = "GROUP BY p.country";
            if (city){
                selectStatement = selectStatement + ", p.city";
                groupByStatement = groupByStatement + ", p.city";
            }
            if (postalCode){
                selectStatement = selectStatement + ", p.postalCode";
                groupByStatement = groupByStatement + ", p.postalCode";
            }



            String sqlQuery = selectStatement + " FROM Listing l "
                            + "INNER JOIN Property p ON l.propertyID = p.propertyID "
                            + groupByStatement
                            + " ORDER BY COUNT(*) DESC;";
            System.out.println(sqlQuery);
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int countResult = rs.getInt(1);
                String countryResult = rs.getString(2);
                String printString = "Count " + countResult + " Country " + countryResult;
                if (city) {
                    String cityResult = rs.getString(3);
                    printString = printString + " City " + cityResult;
                }
                if (postalCode) {
                    String postalResult = rs.getString(4);
                    printString = printString + " PostalCode " + postalResult;
                }
                System.out.println(printString);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void reportPerPosterPerCounteryPerCity (Boolean country, Boolean city) {
        try {
            String selectStatement = "SELECT COUNT(*), l.posterID, p.country";
            String groupByStatement = "GROUP BY l.posterID, p.country";
            if (city){
                selectStatement = selectStatement + ", p.city";
                groupByStatement = groupByStatement + ", p.city";
            }

            String sqlQuery = selectStatement + " FROM Listing l "
                            + "INNER JOIN Property p ON l.propertyID = p.propertyID "
                            + groupByStatement
                            + " ORDER BY COUNT(*) DESC;";
            System.out.println(sqlQuery);
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int countResult = rs.getInt(1);
                int posterIDResult = rs.getInt(2);
                String countryResult = rs.getString(3);
                String printString = "Count " + + countResult + " PosterID " + posterIDResult
                                        + " Country " + countryResult;
                if (city) {
                    String cityResult = rs.getString(4);
                    printString = printString + " City " + cityResult;
                }
                System.out.println(printString);
            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
}
