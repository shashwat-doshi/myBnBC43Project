package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import app.Main;

public class Listing {

    public int listingID, propertyID, posterID, currencyID;

    public enum listingStatus {
        available,
        unavailable
    }

    String status; // need this? check

    public Timestamp startDate;
    public Timestamp endDate;
    public float pricePerNight;

    public Listing() {
        this.status = listingStatus.available.name(); // default status is "available"
        // int listingID = createListing();
        // this.listingID = listingID;
    }

    public static Listing getListingByListingID(int listingID) {
        try {
            String sql = "SELECT * FROM Listing WHERE listingID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, listingID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("Listing with listing ID " + listingID + " does not exist!");
                return null;
            }

            Listing listing = new Listing();

            do {
                listing.listingID = rs.getInt("listingID");
                // listing.listingStatus = rs.getString("listingStatus");
                // listing.startDate = rs.getDate("startDate")
                // listing.endDate = rs.getDate("endDate");
                listing.currencyID = rs.getInt("currencyID");
                listing.pricePerNight = rs.getFloat("pricePerNight");
                listing.propertyID = rs.getInt("propertyID");
                listing.posterID = rs.getInt("posterID");
            } while (rs.next());
            rs.close();
            preparedStatement.close();
            return listing;
        } catch (Exception e) {
            System.out.println("Unable to retrieve the listing from the given listing ID!");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
