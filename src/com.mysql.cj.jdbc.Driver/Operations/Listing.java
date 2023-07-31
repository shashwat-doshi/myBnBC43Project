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
                this.startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
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
                this.endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
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

    public void findBookingsFromListing (int listingId, Timestamp startDate, Timestamp endDate) {
        try {
            String sqlStatement = "SELECT * FROM BOOKING WHERE listingID=? AND bookingStatus='confirmed')";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, listingID);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteCaseRemoveEntireRow(User host) {
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

    public void updateListing(Float newPricePerNight, Timestamp startDate, Timestamp endDate, int listingID) {
        try {
             System.out.println(" Passing startDate1 " + startDate.toString()+ " endDate1 " + endDate.toString()  + " listingID1 " + listingID);
            String updateSQLStatment = "UPDATE Listing SET startDate = ?, endDate = ?, pricePerNight = ? WHERE listingID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(updateSQLStatment,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, startDate.toString());
            preparedStatement.setObject(2, endDate.toString());
            preparedStatement.setFloat(3, newPricePerNight);
            preparedStatement.setInt(4, listingID);

            preparedStatement.executeUpdate();
            return;
        }
        catch (Exception e) {
            System.out.println(e);
            return;
        }
    }

    public void updateForeignKeysForBookings (Timestamp startDate1, Timestamp startDate2, int listingID1, int listingID2) {
        try {
            String updateSQLString = "UPDATE Booking b " +
                 "SET listingID = CASE WHEN ? BETWEEN b.startDate AND b.endDate THEN ? ELSE ? END"
                 + " WHERE listingID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(updateSQLString,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, startDate1);
            preparedStatement.setInt(2, listingID1);
            preparedStatement.setInt(3, listingID2);
            preparedStatement.setInt(4, this.listingID);

            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public boolean isListingUnavailable (int listingId, Timestamp startDate, Timestamp endDate) {
        Timestamp maxTimestamp = startDate;
        Timestamp minTimestamp = endDate;
        try {
            String sqlMin = "SELECT MIN(startDate) AS min_timestamp FROM Booking b WHERE b.listingID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlMin,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, listingId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                minTimestamp = rs.getTimestamp("min_timestamp");
            }

            String sqlMax = "SELECT MAX(endDate) AS max_timestamp FROM Booking b WHERE b.listingID = ?";
            PreparedStatement preparedStatementMax = Main.conn.prepareStatement(sqlMax,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatementMax.setInt(1, listingId);
            ResultSet rsMax = preparedStatementMax.executeQuery();
            if (rsMax.next()) {
                maxTimestamp = rsMax.getTimestamp("max_timestamp");
            }

            if (maxTimestamp.equals(endDate) && minTimestamp.equals(startDate)) {
                return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean doesListingHaveBooking (int listingId) {
         try {
            String selectSqlString = "SELECT * FROM Booking WHERE listingID = ? and bookingStatus = 'confirmed'";
             PreparedStatement preparedStatement = Main.conn.prepareStatement(selectSqlString,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, listingId);

            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.next()) {
                return false;
            }
            else {
                return true;
            }
        }
        catch(Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public void updateListingStatus (int listingId, Timestamp startDate, Timestamp endDate) {
        if (!doesListingHaveBooking(listingId)) {
            return;
        }
        if (!isListingUnavailable(listingId, startDate, endDate)){
            return;
        }
        try {
            String updateSqlString = "UPDATE Listing SET listingStatus = 'unavailable' WHERE listingID = ?";
             PreparedStatement preparedStatement = Main.conn.prepareStatement(updateSqlString,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, listingId);

            preparedStatement.executeUpdate();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    public void breakListingIntoTwo (Timestamp startDate1, Timestamp endDate1, Timestamp startDate2, Timestamp endDate2) {
        int listingID2 = -1;
        try {
            String insertSQLStatment = "INSERT INTO Listing (startDate, endDate, pricePerNight, propertyID, posterID, currencyID) "
                                        + "SELECT startDate, endDate, pricePerNight, propertyID, posterID, currencyID FROM Listing l WHERE l.ListingID= ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(insertSQLStatment,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, this.listingID);

            int rowAffected = preparedStatement.executeUpdate();
            ResultSet rs;
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                     listingID2 = rs.getInt(1);
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
            return;
        }

        updateListing(this.pricePerNight, startDate1, endDate1, this.listingID);
        updateListing(this.pricePerNight, startDate2, endDate2, listingID2);

        updateForeignKeysForBookings(startDate1, startDate2, this.listingID, listingID2);

        updateListingStatus(listingID2, startDate2, endDate2);
        updateListingStatus(this.listingID, startDate1, endDate1);

    }
    public boolean deleteListing(User host, Timestamp startDate, Timestamp endDate) {
        if ((new Booking()).isDateTakenInBooking(startDate, this)
            || (new Booking()).isDateTakenInBooking(endDate, this)){
            System.out.println("Confirmed Booking exits for these days cancel them if you want to delete the listing");
            return false;
        }
        if (startDate.equals(this.startDate) && endDate.equals(this.endDate)) {
            deleteCaseRemoveEntireRow(host);
            return true;
        }

        if (startDate.equals(this.startDate)) {
            LocalDateTime updated = endDate.toLocalDateTime().withHour(15).withMinute(0).withSecond(0);
            updateListing(pricePerNight, Timestamp.valueOf(updated), this.endDate, listingID);
            updateListingStatus(listingID, Timestamp.valueOf(updated), this.endDate);
            return true;
        }

        if (endDate.equals(this.endDate)) {
            LocalDateTime updated = startDate.toLocalDateTime().withHour(11).withMinute(0).withSecond(0);
            updateListing(pricePerNight, this.startDate, Timestamp.valueOf(updated) , listingID);
            updateListingStatus(listingID, this.startDate, Timestamp.valueOf(updated));
            return true;
        }

        Timestamp startDate1 = this.startDate;
        Timestamp endDate1 = Timestamp.valueOf(startDate.toLocalDateTime().withHour(11).withMinute(0).withSecond(0));
        Timestamp startDate2 = Timestamp.valueOf(endDate.toLocalDateTime().withHour(15).withMinute(0).withSecond(0));;
        Timestamp endDate2 = this.endDate;
        breakListingIntoTwo(startDate1, endDate1, startDate2, endDate2);
        return true;
    }

    public void deleteListingPrompt (User user) {
        if (user.userID != this.posterID) {
            System.out.println("You are not poster");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the start date yyyy-mm-dd for delete");
        System.out.println("This will remove the listing starting from yyyy-mm-dd 15:00:00");
        Timestamp startDate;
        Timestamp endDate;
        while (true) {
            String startDateString = input.nextLine();
            try {
                LocalDate localDate = LocalDate.parse(startDateString);
                startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
                if (startDate.before(this.startDate) || startDate.after(this.endDate)) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Please try again");
            }
        }

        System.out.println("Enter the end date yyyy-mm-dd for delete");
        System.out.println("This will remove the listing till yyyy-mm-dd 11:00:00");
        while (true) {
            String endDateString = input.nextLine();
            try {

                LocalDate localDate = LocalDate.parse(endDateString);
                endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
                if (endDate.before(this.startDate) || endDate.after(this.endDate)) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Please try again\n");
            }
        }
        deleteListing(user, startDate, endDate);
    }
}
