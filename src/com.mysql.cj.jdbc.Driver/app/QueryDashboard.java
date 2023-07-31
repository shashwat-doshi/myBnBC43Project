package app;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import Operations.Listing;

@SuppressWarnings("resource")
public class QueryDashboard {

    public static void findListingsByLocation(double locationLatitude, double locationLongitude,
            float searchDistance) {
        try {
            String sql = "SELECT l.* , ST_Distance(" +
                    "ST_GeomFromText('POINT(" + locationLongitude + " " + locationLatitude + ")', 4326), " +
                    "ST_SRID(coordinates, 4326), 'kilometre') as distance_in_km " +
                    "FROM Property p " +
                    "INNER JOIN Listing l " +
                    "ON l.propertyID = p.propertyID " +
                    "HAVING distance_in_km <= " + searchDistance + " " +
                    "ORDER BY distance_in_km";

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
                        System.out.println("CHOICE: " + choice);
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
                try {
                    String sql = "SELECT l.* " +
                            "FROM Listing l " +
                            "INNER JOIN Property p " +
                            "ON l.propertyID = p.propertyID " +
                            "WHERE p.street = '" + addr + "'";

                    PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        preparedStatement.close();
                        System.out.println("No property exists with the given address!\n");
                    } else {
                        int listingID = rs.getInt("listingID");
                        Listing listing = Listing.getListingByListingID(listingID);
                        ListingDashboard.viewListingInfo(listing);
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
                    "3: Delete user\n" +
                    "4: Perform queries on the database\n" +
                    "exit: Go back to main menu\n");
            command = input.nextLine(); // Read user input
            if (!queryDashboardHandler(command)) {
                break;
            }
        }
    }

}
