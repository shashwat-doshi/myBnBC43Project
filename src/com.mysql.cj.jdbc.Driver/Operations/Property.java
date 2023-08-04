package Operations;

import java.sql.Statement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@SuppressWarnings("resource")
public class Property {
    public static void createNewProperty(Connection conn) {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input property information...\n");

        System.out.println("Enter Property Latitue");
        Float latitude = input.nextFloat();
        System.out.println("Enter Property Longitude");
        Float longitude = input.nextFloat();
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
        ArrayList<Integer> amenities = new ArrayList<Integer>();
        ArrayList<Integer> prices = new ArrayList<Integer>();
        ArrayList<Boolean> availabilities = new ArrayList<Boolean>();
        int amenity = -1;
        int price = 0;
        Boolean availability = true;
         while (true) {
            System.out.println("Enter ammenities to add options are:\n" +
                            "1:A washer or dryer\n" +
                            "2:Heating\n" +
                            "3:pool\n" +
                            "4:kitchen\n" +
                            "5:mini bar\n" +
                            "6:Pets\n" +
                            "7:Wifi\n" +
                            "8:Jacuzzi\n" +
                            "9:Free parking\n" +
                            "10:Laptop friendly\n" +
                            "11:to finish selecting amenities");
            try {
                amenity = input.nextInt();
                if (amenity > 11 || amenity < 0) {
                    throw new Exception();
                }

                if (amenity == 11) {
                    break;
                }
                amenities.add(amenity);
                System.out.println("Please input price of amenity");
                price = input.nextInt();
                prices.add(price);
                System.out.println("Please input availability of amenity");
                availability = input.nextBoolean();
                availabilities.add(availability);
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
            Integer capacity = input.nextInt();
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
                    PreparedStatement amenityInsert = conn.prepareStatement(amenitySql, Statement.RETURN_GENERATED_KEYS);
                    amenityInsert.setInt(1, amenities.get(i));
                    amenityInsert.setInt(2, insertId);
                    amenityInsert.setBoolean(3, availabilities.get(i));
                    amenityInsert.setInt(4, prices.get(i));

                    amenityInsert.executeUpdate();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
