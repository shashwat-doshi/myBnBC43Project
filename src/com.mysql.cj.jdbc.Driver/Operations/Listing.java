package Operations;

import java.sql.Timestamp;

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

}
