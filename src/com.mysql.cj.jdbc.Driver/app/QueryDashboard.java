package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import Operations.Listing;

@SuppressWarnings("resource")
public class QueryDashboard {

    public static String applyTemporalFilterDates() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sqlFilterDate = "";
        Timestamp startDate, endDate;
        while (true) {
            try {
                System.out.println("Do you want to apply a temporal filter? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y")) {
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
                        sqlFilterDate = "(UNIX_TIMESTAMP(l.startDate) >= " + " UNIX_TIMESTAMP('"
                                + startDate.toString() + "')"
                                + " AND UNIX_TIMESTAMP(l.endDate) <= "
                                + " UNIX_TIMESTAMP('" + endDate.toString() + "') "
                                + ") ";
                    } else {
                        throw new Exception("End date should be greater than start date! Please try again...");
                    }
                    break;
                } else if (choice.equals("N")) {
                    sqlFilterDate = "";
                    break;
                } else {
                    throw new Exception("Incorrect choice! Please try again...");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sqlFilterDate;
    }

    public static void findListingsByLocation(double locationLatitude, double locationLongitude,
            float searchDistance) {
        try {
            String temporalFilterDate = applyTemporalFilterDates();
            String sql;
            if (temporalFilterDate.equals("")) {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            } else {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "WHERE " + temporalFilterDate +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            }

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("\nNo listings found near the given latitude/longitude and the search radius!");
                return;
            }

            int listingID = 0;
            Listing listing = null;
            System.out.println("Here are the listings according to the given input: \n");
            int count = 1;
            do {
                System.out.println("Listing Result " + count++ + ":");
                listingID = rs.getInt("listingID");
                listing = Listing.getListingByListingID(listingID);
                ListingDashboard.viewListingInfo(listing);
            } while (rs.next());

            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve listings from the given input, please try again!");
            System.out.println(e.getMessage());
        }
    }

    public static boolean queryDashboardHandler(String cmd) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        double locationLatitude, locationLongitude;
        float searchDistance = 6000; // default search distance

        switch (cmd) {
            case "1":
                System.out.println("Please enter the latitude of the location you want to search...");
                while (true) {
                    try {
                        locationLatitude = input.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect latitude input! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();
                System.out.println("Please enter the longitude of the location you want to search...");
                while (true) {
                    try {
                        locationLongitude = input.nextDouble();
                        break;
                    } catch (Exception e) {
                        System.out.println("Incorrect longitude input! Please try again...");
                        input.nextLine();
                    }
                }
                input.nextLine();

                System.out.println("Default search distance: 6000km. Do you want to modify it? (Y/N)");
                String choice;
                while (true) {
                    try {
                        choice = input.nextLine();
                        if (choice.equals("Y")) {
                            System.out.println("Please enter the new search distance:");
                            searchDistance = input.nextFloat();
                            input.nextLine();
                            break;
                        } else if (choice.equals("N")) {
                            System.out.println("Default search distance = 6000km selected.");
                            break;
                        } else {
                            throw new Exception("Incorrect choice! Please try again!");
                        }
                    } catch (Exception e) {
                        System.out.println("Incorrect choice! Please try again...");
                        input.nextLine();
                    }
                }

                findListingsByLocation(locationLatitude, locationLongitude, searchDistance);
                break;

            case "2":
                System.out.println("Please enter the address you want to search for:");
                String addr = input.nextLine();
                String temporalFilterDate = applyTemporalFilterDates();
                try {
                    String sql;
                    if (temporalFilterDate.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "WHERE p.street = '" + addr + "'";
                    } else {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "WHERE p.street = '" + addr + "' AND " + temporalFilterDate;
                    }

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No property exists with the given address!\n");
                    } else {
                        int listingID = 0;
                        Listing listing = null;
                        System.out.println("Here are the listings according to the given input: \n");
                        int count = 1;
                        do {
                            System.out.println("Listing Result " + count++ + ":");
                            listingID = rs.getInt("listingID");
                            listing = Listing.getListingByListingID(listingID);
                            ListingDashboard.viewListingInfo(listing);
                        } while (rs.next());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                try {
                    int orderChoice;
                    String order = "";
                    while (true) {
                        System.out.println(
                                "Select the order in which you want your listings to be ordered: 1 - Ascending, 2 - Descending");
                        try {
                            orderChoice = input.nextInt();
                            if (orderChoice == 1) {
                                order = "ASC";
                            } else if (orderChoice == 2) {
                                order = "DESC";
                            } else {
                                throw new Exception("Incorrect choice! Please try again...");
                            }
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect choice! Please try again...");
                            input.nextLine();
                        }
                    }
                    input.nextLine();

                    String temporalFilterDateOrderByPrice = applyTemporalFilterDates();
                    String sql;

                    if (temporalFilterDateOrderByPrice.equals("")) {
                        sql = "SELECT * FROM Listing " +
                                "ORDER BY pricePerNight " + order;
                    } else {
                        sql = "SELECT * FROM Listing l " +
                                "WHERE " + temporalFilterDateOrderByPrice +
                                "ORDER BY pricePerNight " + order;
                    }

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No listings exist!\n");
                    } else {
                        int listingID;
                        Listing listing = null;
                        do {
                            listingID = rs.getInt("listingID");
                            listing = Listing.getListingByListingID(listingID);
                            ListingDashboard.viewListingInfo(listing);
                        } while (rs.next());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                try {
                    System.out.println("Enter the postal code you want to search for:");
                    String postalCode = input.nextLine();

                    String temporalFilterDateSearchPCode = applyTemporalFilterDates();

                    String sql;
                    if (temporalFilterDateSearchPCode.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "WHERE p.postalCode = '" + postalCode + "'";
                    } else {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "WHERE p.postalCode = '" + postalCode + "' AND " + temporalFilterDateSearchPCode;
                    }

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No listing exists with the given postal code!\n");
                    } else {
                        int listingID = 0;
                        Listing listing = null;
                        System.out.println("Here are the listings according to the given input: \n");
                        int count = 1;
                        do {
                            System.out.println("Listing Result " + count++ + ":");
                            listingID = rs.getInt("listingID");
                            listing = Listing.getListingByListingID(listingID);
                            ListingDashboard.viewListingInfo(listing);
                        } while (rs.next());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "exit":
                return false;
            default:
                System.out.println("Incorrect choice! Please try again...\n");
        }
        return true;
    }

    public static void queryDashboardInterface() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;

        while (true) {
            System.out.println("Please enter the appropriate query you wish to perform...\n");
            System.out.println("1: Search for listings by latitude and longitude\n" +
                    "2: Search for listings by address (exact match)\n" +
                    "3: Show listings ordered by price\n" +
                    "4: Search for listings by postal code\n" +
                    "exit: Go back to main menu\n");
            command = input.nextLine(); // Read user input
            if (!queryDashboardHandler(command)) {
                break;
            }
        }
    }

}
