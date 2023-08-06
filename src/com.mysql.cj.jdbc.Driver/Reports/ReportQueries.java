package Reports;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

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
                    System.out.println("City\t\ttotalBookings\n");
                    do {
                        bookingCount = rs.getInt("COUNT(*)");
                        bookingCity = rs.getString("city");
                        System.out.println(bookingCity + "\t\t" + bookingCount);
                    } while (rs.next());
                }
            } else {
                throw new Exception("End date should be greater than start date! Please try again...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getNoOfBookingsByPCode() {
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
                sql = "SELECT p.city, p.postalCode, COUNT(*) " +
                        "FROM Booking b " +
                        "INNER JOIN Listing l " +
                        "ON l.listingID = b.listingID " +
                        "INNER JOIN Property p " +
                        "ON l.propertyID = p.propertyID " +
                        "WHERE (UNIX_TIMESTAMP(b.startDate) >= UNIX_TIMESTAMP('" + startDate
                        + "') AND UNIX_TIMESTAMP(b.endDate) <= UNIX_TIMESTAMP('" + endDate + "')) " +
                        "GROUP BY p.city, p.postalCode";

                PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                if (!rs.next()) {
                    rs.close();
                    preparedStatement.close();
                    System.out.println("No results found with the given input!\n");
                } else {
                    int bookingCount;
                    String bookingCity, bookingPCode;
                    System.out.println("Here are the listings according to the given input: \n");
                    System.out.println("City\t\tPostalCode\t\tCOUNT(*)\n");
                    do {
                        bookingCount = rs.getInt("COUNT(*)");
                        bookingCity = rs.getString("city");
                        bookingPCode = rs.getString("postalCode");
                        System.out.println(bookingCity + "\t\t" + bookingPCode + "\t\t" + bookingCount);
                    } while (rs.next());
                }
            } else {
                throw new Exception("End date should be greater than start date! Please try again...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void reportHostsTenPercent() {
        String sql = "";
        try {
            sql = "SELECT DISTINCT outerListing.posterID, outerProperty.city, outerProperty.country " +
                    "FROM Listing outerListing " +
                    "INNER JOIN Property outerProperty ON outerListing.propertyID = outerProperty.propertyID " +
                    "WHERE " +
                    "(SELECT COUNT(*) " +
                    "FROM Listing l " +
                    "INNER JOIN Property p " +
                    "ON p.propertyID = l.propertyID " +
                    "WHERE p.city = outerProperty.city and p.country = outerProperty.country AND l.posterID = outerListing.posterID) "
                    +
                    ">= " +
                    "(SELECT COUNT(*) * 0.1 AS totalListings " +
                    "FROM Listing l2 " +
                    "INNER JOIN Property p2 " +
                    "ON p2.propertyID = l2.propertyID " +
                    " WHERE p2.country = outerProperty.country AND p2.city = outerProperty.city) " +
                    "ORDER BY outerListing.posterID";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                rs.close();
                preparedStatement.close();
                System.out.println("No results found with the given input!\n");
            } else {
                int hostID;
                String city, country;
                System.out.println("Here are the listings according to the given input: \n");
                System.out.println("HostID\t\tCity\t\tCountry\n");
                do {
                    hostID = rs.getInt("outerListing.posterID");
                    city = rs.getString("outerProperty.city");
                    country = rs.getString("outerProperty.country");
                    System.out.println(hostID + "\t\t" + city + "\t\t" + country);
                } while (rs.next());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void reportNounPhrases() {
        ArrayList<String> commonNounPhrases = new ArrayList<String>();

        commonNounPhrases.add("great stay!");
        commonNounPhrases.add("highly recommended!");
        commonNounPhrases.add("amazing experience!");
        commonNounPhrases.add("top-notch");
        commonNounPhrases.add("impeccable");
        commonNounPhrases.add("clean");
        commonNounPhrases.add("neat");
        commonNounPhrases.add("tidy");
        commonNounPhrases.add("cozy accommodation");
        commonNounPhrases.add("disappointing");
        commonNounPhrases.add("would definitely come back.");
        commonNounPhrases.add("overrated");
        commonNounPhrases.add("overpriced");
        commonNounPhrases.add("awesome");

        String sqlFirstQuery = "", sqlSecondQuery = "";
        Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();

        try {
            sqlFirstQuery = "SELECT l.listingID, b.reviewForRenter, b.reviewForOwner, b.reviewForProperty, rRenter.commentID as RenterCommentID, rOwner.commentID as OwnerCommentID, rProperty.commentID as PropertyCommentID "
                    +
                    "FROM Listing l " +
                    "INNER JOIN Booking b " +
                    "ON b.listingID = l.listingID " +
                    "LEFT JOIN Review rRenter " +
                    "ON rRenter.reviewID = b.reviewForRenter " +
                    "LEFT JOIN Review rOwner " +
                    "ON rOwner.reviewID = b.reviewForOwner " +
                    "LEFT JOIN Review rProperty " +
                    "ON rProperty.reviewID = b.reviewForProperty";

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlFirstQuery);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                rs.close();
                preparedStatement.close();
                System.out.println("No results found with the given input!\n");
            } else {
                int renterCommentID, ownerCommentID, propertyCommentID, listingID;
                do {
                    listingID = rs.getInt("listingID");
                    renterCommentID = rs.getInt("RenterCommentID");
                    ownerCommentID = rs.getInt("OwnerCommentID");
                    propertyCommentID = rs.getInt("PropertyCommentID");

                    if (renterCommentID != 0 || ownerCommentID != 0 || propertyCommentID != 0) {
                        sqlSecondQuery = "SELECT c.details " +
                                "FROM Comment c " +
                                "WHERE c.commentID = " + renterCommentID + " OR c.commentID = " + ownerCommentID
                                + " OR c.commentID = " + propertyCommentID;

                        PreparedStatement preparedStatementSecondQuery = Main.conn.prepareStatement(sqlSecondQuery);
                        ResultSet rsSecondQuery = preparedStatementSecondQuery.executeQuery();

                        if (!rsSecondQuery.next()) {
                            rsSecondQuery.close();
                            preparedStatementSecondQuery.close();
                            System.out.println(
                                    "No details present for this commentID! (empty comment)\n" + sqlSecondQuery);
                        } else {
                            String details;
                            do {
                                details = rsSecondQuery.getString("c.details");
                                details = details.toLowerCase();

                                String regex = "[!._,'@? ]";
                                StringTokenizer str = new StringTokenizer(details, regex);

                                if (commonNounPhrases.contains(details)) {
                                    if (map.containsKey(listingID)) {
                                        ArrayList<String> temp = new ArrayList<String>();
                                        temp = map.get(listingID);
                                        if (!map.get(listingID).contains(details)) {
                                            temp.add(details);
                                        }
                                        map.put(listingID, temp);
                                    } else {
                                        ArrayList<String> temp = new ArrayList<String>();
                                        temp.add(details);
                                        map.put(listingID, temp);
                                    }
                                } else {
                                    while (str.hasMoreTokens()) {
                                        String nounPhrase = str.nextToken();
                                        nounPhrase = nounPhrase.toLowerCase();
                                        if (commonNounPhrases.contains(nounPhrase)) {
                                            if (map.containsKey(listingID)) {
                                                ArrayList<String> temp = new ArrayList<String>();
                                                temp = map.get(listingID);
                                                if (!map.get(listingID).contains(nounPhrase)) {
                                                    temp.add(nounPhrase);
                                                }
                                                map.put(listingID, temp);
                                            } else {
                                                ArrayList<String> temp = new ArrayList<String>();
                                                temp.add(nounPhrase);
                                                map.put(listingID, temp);
                                            }
                                        }
                                    }
                                }
                            } while (rsSecondQuery.next());
                        }
                    }
                } while (rs.next());

                System.out.println("ListingID\t\tNoun Phrases\n");

                for (Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + "\t\t" + entry.getValue());
                }

                System.out.print("\n");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
