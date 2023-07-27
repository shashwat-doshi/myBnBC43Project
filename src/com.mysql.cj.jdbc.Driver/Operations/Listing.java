package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Listing {
  public static void createNewListing(Connection conn) {
     Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input listing information...\n");
        System.out.println("Enter User Id");
        Integer userId = input.nextInt();
        System.out.println("Enter Listing Start date yyyy-mm-dd");
        LocalDateTime startDate;
        input.nextLine();
        while (true) {
          String startDateString = input.nextLine();
          try {
            LocalDate localDate = LocalDate.parse(startDateString);
            startDate = LocalDateTime.of(localDate, LocalTime.of(11, 0));
            break;
          }
          catch(Exception e) {
            System.out.println("Please try again");
          }
        }


        System.out.println("Enter Listing End date yyyy-mm-dd");
        LocalDateTime endDate;
        while (true) {
          String endDateString = input.nextLine();
          try {
            LocalDate localDate = LocalDate.parse(endDateString);
            endDate = LocalDateTime.of(localDate, LocalTime.of(15, 0));
            break;
          }
          catch(Exception e) {
            System.out.println("Please try again\n");
          }
        }
        System.out.println("Enter Currency type" +
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
                if (type > 10 || type < 0){
                  throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        System.out.println("Enter Price Per night");
        Float pricePerNight = input.nextFloat();
        System.out.println("Enter Proprty Id");
        Integer propertyId = input.nextInt();
        try {
          String insertProprtySql = "INSERT INTO Listing (startDate, endDate, pricePerNight, propertyID, posterID, currencyID)" +
          "values (?, ?, ?, ?, ?, ?)";
          PreparedStatement preparedStatement = conn.prepareStatement(insertProprtySql, Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
          preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
          preparedStatement.setFloat(3, pricePerNight); // check
          preparedStatement.setInt(4, propertyId);
          preparedStatement.setInt(5, userId);
          preparedStatement.setInt(6, type);

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
          System.out.println("Created listing with id" + insertId);
        }
        catch (Exception e) {
          System.out.println(e);
        }

  }
  public static void deleteListing(Connection conn) {
    Scanner input = new Scanner(System.in); // Create a Scanner object
    System.out.println("Enter Your User Id");
    Integer userId = input.nextInt();

    System.out.println("Enter Your Listing Id");
    Integer listing= input.nextInt();
    try {
      String deleteListingSql = "DELETE FROM Listing l " +
          "WHERE posterID = ? AND listingID = ? AND NOT EXISTS(SELECT * FROM BOOKING WHERE listingID=l.listingID AND bookingStatus='confirmed')";

      PreparedStatement preparedStatement = conn.prepareStatement(deleteListingSql, Statement.RETURN_GENERATED_KEYS);

      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, listing);

      int numOfAffected = preparedStatement.executeUpdate();
      if (numOfAffected > 1) {
        System.out.println("deleted listing");
      }
      else {
        System.out.println("Check your userId or listingId");
      }

    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
}
