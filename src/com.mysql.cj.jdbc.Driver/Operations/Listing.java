package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import app.Main;

@SuppressWarnings("resource")
public class Listing {

    public Timestamp startDate, endDate;
    public String listingStatus;
    public Float pricePerNight;
    public int listingID;
    public int propertyID, posterID, currencyID; // foreign keys

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
                listing.listingStatus = rs.getString("listingStatus");
                listing.startDate = rs.getTimestamp("startDate");
                listing.endDate = rs.getTimestamp("endDate");
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

    public void createNewListing(User host) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input listing information...\n");
        this.listingStatus = "available";
        System.out.println("Enter Listing Start Date yyyy-mm-dd");
        while (true) {
            String startDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(startDateString);
                this.startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }

        System.out.println("Enter Listing End date yyyy-mm-dd");
        while (true) {
            String endDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(endDateString);
                this.endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
                break;
            } catch (Exception e) {
                System.out.println("Please try again\n");
            }
        }
        System.out.println("Enter Currency type\n" +
                "1: Ruble\n" +
                "2: Franc\n" +
                "3: Yuan Renminbi\n" +
                "4: UAE Dirham\n" +
                "5: Krona\n" +
                "6: Real\n" +
                "7: Manat\n" +
                "8: Indian Rupee\n" +
                "9: Euro\n" +
                "10: Peso");
        Integer type;
        while (true) {
            try {
                type = input.nextInt();
                input.nextLine();
                if (type > 10 || type < 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        this.currencyID = type;

        System.out.println("Enter Price Per night");
        Float price;
        while (true) {
            try {
                price = input.nextFloat();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect type! Please enter a floating type...");
                input.nextLine();
            }
        }
        this.pricePerNight = price;
        System.out.println("Enter Property ID");
        int propID;
        while (true) {
            try {
                propID = input.nextInt();
                input.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect type! Please enter an int type...");
                input.nextLine();
            }
        }
        this.propertyID = propID;

        try {
            String insertProprtySql = "INSERT INTO Listing (startDate, endDate, pricePerNight, propertyID, posterID, currencyID)"
                    +
                    "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(insertProprtySql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, this.startDate);
            preparedStatement.setTimestamp(2, this.endDate);
            preparedStatement.setFloat(3, this.pricePerNight);
            preparedStatement.setInt(4, this.propertyID);
            preparedStatement.setInt(5, host.userID);
            preparedStatement.setInt(6, this.currencyID);

            int rowAffected = preparedStatement.executeUpdate();
            ResultSet rs;
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.listingID = rs.getInt(1);
                }
            }
            System.out.println("Created listing with listing ID: " + this.listingID);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteListing(User host) {
        try {
            String deleteListingSql = "DELETE FROM Listing l " +
                    "WHERE posterID = ? AND listingID = ? AND NOT EXISTS(SELECT * FROM BOOKING WHERE listingID=l.listingID AND bookingStatus='confirmed')";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(deleteListingSql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, host.userID);
            preparedStatement.setInt(2, this.listingID);

            int numOfAffected = preparedStatement.executeUpdate();
            if (numOfAffected >= 1) {
                System.out.println("Deleted listing with listing ID: " + this.listingID);
            } else {
                System.out.println("Unable to delete listing, please try again...");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}