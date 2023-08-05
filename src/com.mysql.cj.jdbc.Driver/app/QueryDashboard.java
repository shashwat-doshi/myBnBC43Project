package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import Operations.Listing;
import static Queries.TemporalFilter.*;

@SuppressWarnings("resource")
public class QueryDashboard {

    public static void findListingsByLocation(double locationLatitude, double locationLongitude,
            float searchDistance) {
        try {
            String temporalFilterDate = applyFilters();
            String amenityFilter;
            amenityFilter = getFilterAmenities();
            String sql = "";
            if (temporalFilterDate.equals("") && amenityFilter.equals("")) {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "WHERE l.listingStatus = 'available' " +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            } else if (!temporalFilterDate.equals("") && amenityFilter.equals("")) {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "WHERE l.listingStatus = 'available' AND " + temporalFilterDate + " " +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            } else if (temporalFilterDate.equals("") && !amenityFilter.equals("")) {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "INNER JOIN Offers o " +
                        "ON o.propertyID = p.propertyID " +
                        "WHERE l.listingStatus = 'available' AND " + amenityFilter + " " +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            } else if (!temporalFilterDate.equals("") && !amenityFilter.equals("")) {
                sql = "SELECT l.* , ST_Distance(" +
                        "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                        "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                        "FROM Property p " +
                        "INNER JOIN Listing l " +
                        "ON l.propertyID = p.propertyID " +
                        "INNER JOIN Offers o " +
                        "ON o.propertyID = p.propertyID " +
                        "WHERE l.listingStatus = 'available' AND " + amenityFilter + " AND "
                        + temporalFilterDate + " " +
                        "HAVING distance_in_km <= " + searchDistance + " " +
                        "ORDER BY distance_in_km";
            }

            System.out.println("SQL QUERY latitude/longitude: " + sql);

            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println(
                        "\nNo available listings found near the given latitude/longitude and the search radius!");
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
                String temporalFilterAddr = applyFilters();
                String amenityFilter;
                amenityFilter = getFilterAmenities();
                try {
                    String sql = "";
                    if (temporalFilterAddr.equals("") && amenityFilter.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = " + "'available' AND p.street = '" + addr + "'";
                    } else if (!temporalFilterAddr.equals("") && amenityFilter.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' AND p.street = '" + addr + "' AND "
                                + temporalFilterAddr;
                    } else if (temporalFilterAddr.equals("") && !amenityFilter.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = " + "'available' AND p.street = '" + addr + "' AND "
                                + amenityFilter;
                    } else if (!temporalFilterAddr.equals("") && !amenityFilter.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = " + "'available' AND p.street = '" + addr + "' AND "
                                + amenityFilter + " AND " + temporalFilterAddr;
                    }

                    System.out.println("ADDY: " + sql);

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No available listing exists with the given address!\n");
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

                    String temporalFilterOrderByPrice = applyFilters();
                    String sql = "";
                    String amenityFilterPrice;
                    amenityFilterPrice = getFilterAmenities();

                    if (temporalFilterOrderByPrice.equals("") && amenityFilterPrice.equals("")) {
                        sql = "SELECT * FROM Listing l " +
                                "WHERE l.listingStatus = 'available' " +
                                "ORDER BY pricePerNight " + order;
                    } else if (!temporalFilterOrderByPrice.equals("") && amenityFilterPrice.equals("")) {
                        sql = "SELECT l.* FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON l.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' AND " + temporalFilterOrderByPrice + " " +
                                "ORDER BY l.pricePerNight " + order;
                    } else if (temporalFilterOrderByPrice.equals("") && !amenityFilterPrice.equals("")) {
                        sql = "SELECT l.* FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON p.propertyID = l.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' AND " + amenityFilterPrice + " " +
                                "ORDER BY pricePerNight " + order;
                    } else if (!temporalFilterOrderByPrice.equals("") && !amenityFilterPrice.equals("")) {
                        sql = "SELECT l.* FROM Listing l " +
                                "INNER JOIN Property p " +
                                "ON p.propertyID = l.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' " + amenityFilterPrice + " "
                                + temporalFilterOrderByPrice + " " +
                                "ORDER BY pricePerNight " + order;
                    }

                    System.out.println("PRICE: " + sql);

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No available listings exist!\n");
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

                    String temporalFilterSearchPCode = applyFilters();
                    String sql = "";
                    String amenityFilterPostalCode;
                    amenityFilterPostalCode = getFilterAmenities();

                    if (temporalFilterSearchPCode.equals("") && amenityFilterPostalCode.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "WHERE l.listingStatus = 'available' AND p.postalCode = '" + postalCode + "'";
                    } else if (!temporalFilterSearchPCode.equals("") && amenityFilterPostalCode.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "WHERE l.listingStatus = 'available' AND p.postalCode = '" + postalCode + "' AND "
                                + temporalFilterSearchPCode;
                    } else if (temporalFilterSearchPCode.equals("") && !amenityFilterPostalCode.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' AND p.postalCode = '" + postalCode + "' AND "
                                + amenityFilterPostalCode;
                    } else if (!temporalFilterSearchPCode.equals("") && !amenityFilterPostalCode.equals("")) {
                        sql = "SELECT l.* " +
                                "FROM Property p " +
                                "INNER JOIN Listing l " +
                                "ON p.propertyID = l.propertyID " +
                                "INNER JOIN Offers o " +
                                "ON o.propertyID = p.propertyID " +
                                "WHERE l.listingStatus = 'available' AND p.postalCode = '" + postalCode + "' AND "
                                + amenityFilterPostalCode + " AND " + temporalFilterSearchPCode;
                    }

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();
                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No available listing exists with the given postal code!\n");
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
