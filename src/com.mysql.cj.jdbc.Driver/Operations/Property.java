package Operations;

import java.sql.Statement;
import java.util.Scanner;

import HostToolkit.SuggestAmenity;
import app.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("resource")
public class Property {
    public int propertyID;
    public String city;

    public static void createNewProperty(Connection conn) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input property information...\n");
        System.out.println("Enter Property Latitude");
        Float latitude;
        while (true) {
            try {
                latitude = input.nextFloat();
                break;
            } catch (Exception e) {
                System.out.println("Try again");
                input.nextLine();
            }
        }

        System.out.println("Enter Property Longitude");
        Float longitude;
        while (true) {
            try {
                longitude = input.nextFloat();
                break;
            } catch (Exception e) {
                System.out.println("Try again");
                input.nextLine();
            }
        }
        System.out.println("Enter Property Street");
        input.nextLine();
        String street = input.nextLine();
        System.out.println("Enter City");
        String city = input.nextLine();
        System.out.println("Enter Postal Code");
        String postalCode = input.nextLine();
        System.out.println("Enter Country");
        String country = input.nextLine();
        System.out.println("Enter Type: \n valid types are \n 0: House, 1: Room, 2: Apartment");
        Integer type;
        while (true) {
            try {
                type = input.nextInt();
                if (type > 2 || type < 0) {
                    throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        input.nextLine();
        ArrayList<Integer> amenities = new ArrayList<Integer>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        ArrayList<Boolean> availabilities = new ArrayList<Boolean>();
        int amenity = -1;
        int price = 0;
        Boolean availability = true;

        SuggestAmenity.SuggestAmenityForProperty(city);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map = SuggestAmenity.suggestIncreaseInPrice(city);

        while (true) {
            System.out.println("Enter ammenities to add options are:\n" +
                    "1:washer or dryer, adding this will increase your profit by: " + map.get(1) + "\n" +
                    "2:Heating, adding this will increase your profit by: " + map.get(2) + "\n" +
                    "3:pool, adding this will increase your profit by: " + map.get(3) + "\n" +
                    "4:kitchen, adding this will increase your profit by: " + map.get(4) + "\n" +
                    "5:mini bar, adding this will increase your profit by: " + map.get(5) + "\n" +
                    "6:Pets, adding this will increase your profit by: " + map.get(6) + "\n" +
                    "7:Wifi, adding this will increase your profit by: " + map.get(7) + "\n" +
                    "8:Jacuzzi, adding this will increase your profit by: " + map.get(8) + "\n" +
                    "9:Free parking, adding this will increase your profit by: " + map.get(9) + "\n" +
                    "10:Laptop friendly, adding this will increase your profit by: " + map.get(10) + "\n" +
                    "11:to finish selecting amenities");
            try {
                amenity = input.nextInt();
                if (amenity > 11 || amenity < 0) {
                    throw new Exception();
                }

                if (amenity == 11) {
                    break;
                }
                System.out.println("Please input price of amenity");
                price = input.nextInt();
                System.out.println("Please input availability of amenity");
                availability = input.nextBoolean();
                availabilities.add(availability);
                amenities.add(amenity);
                prices.add(price);
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        input.nextLine();
        try {
            String insertProprtySql = "INSERT INTO Property (street, country, city, postalCode, coordinates) "
                    + "values (?, ?, ?, ?, POINT(?, ?))";
            PreparedStatement preparedStatement = conn.prepareStatement(insertProprtySql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, street);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, city); // check
            preparedStatement.setString(4, postalCode);
            preparedStatement.setFloat(5, latitude);
            preparedStatement.setFloat(6, longitude);

            int rowAffected = preparedStatement.executeUpdate();
            ResultSet rs;
            int insertId = 0;
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    insertId = rs.getInt(1);
                }
            }
            System.out.println("Enter Capacity");
            Integer capacity;
            while (true) {
                try {
                    capacity = input.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Try again");
                    input.nextLine();
                }
            }
            input.nextLine();
            PreparedStatement preparedStatementType;
            if (type == 0) {
                String insertRoomSql = "INSERT INTO House (propertyID, capacity) values (?, ?)";
                preparedStatementType = conn.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS);
                preparedStatementType.setInt(1, insertId);
                preparedStatementType.setInt(2, capacity);
            } else if (type == 1) {
                System.out.println("Is this room shared");
                Boolean isRoomShared = input.nextBoolean();
                String insertRoomSql = "INSERT INTO Room (propertyID, isShared, capacity) values (?, ?, ?)";
                preparedStatementType = conn.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS);
                preparedStatementType.setInt(1, insertId);
                preparedStatementType.setBoolean(2, isRoomShared);
                preparedStatementType.setInt(3, capacity);
            } else {
                String insertRoomSql = "INSERT INTO HotelApartment (propertyID, capacity) values (?, ?)";
                preparedStatementType = conn.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS);
                preparedStatementType.setInt(1, insertId);
                preparedStatementType.setInt(2, capacity);
            }
            preparedStatementType.executeUpdate();
            System.out.println("Your property id is " + insertId);

            for (int i = 0; i < amenities.size(); i++) {
                try {
                    String amenitySql = "INSERT INTO Offers (amenityID, propertyID, isAvailable, cost) values (?, ?, ?, ?)";
                    PreparedStatement amenityInsert = conn.prepareStatement(amenitySql,
                            Statement.RETURN_GENERATED_KEYS);
                    amenityInsert.setInt(1, amenities.get(i));
                    amenityInsert.setInt(2, insertId);
                    amenityInsert.setBoolean(3, availabilities.get(i));
                    amenityInsert.setInt(4, prices.get(i));

                    amenityInsert.executeUpdate();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public boolean isPropertyFreeOnDate(Timestamp date) {
        try {
            String propertySql = "SELECT * FROM Booking INNER JOIN Listing ON Booking.listingID = Listing.listingID" +
                    " WHERE Listing.propertyID = ? AND Booking.bookingStatus = 'confirmed' AND ? BETWEEN Booking.startDate AND Booking.endDate;";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(propertySql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, this.propertyID);
            preparedStatement.setTimestamp(2, date);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public void printAvailableDates(Timestamp startDate, Timestamp endDate, Float pricePerNight) {
        Timestamp currentTime = startDate;
        while (!currentTime.after(endDate)) {
            if (isPropertyFreeOnDate(currentTime)) {
                System.out.println("Property " + this.propertyID + " is available on " + currentTime.toString()
                        + " for price of " + pricePerNight);
            }
            currentTime.setTime(currentTime.getTime() + 24 * 60 * 60 * 1000); // Add one day in milliseconds
        }
    }

    public void getPropertyCalender() {
        try {
            String sqlStatement = "SELECT startDate, endDate, pricePerNight FROM Listing WHERE propertyID = ? AND listingStatus = 'available' ORDER BY startDate";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, this.propertyID);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Timestamp startDate = rs.getTimestamp("startDate");
                Timestamp endDate = rs.getTimestamp("endDate");
                Float pricePerNight = rs.getFloat("pricePerNight");
                printAvailableDates(startDate, endDate, pricePerNight);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Property getPropertyFromID(int propertyId) {
        try {
            String sqlStatement = "SELECT * FROM Property WHERE propertyId = ?;";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sqlStatement,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, propertyId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Property property = new Property();
                property.propertyID = rs.getInt("propertyId");
                property.city = rs.getString("city");
                return property;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}