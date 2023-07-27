package Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;
import Operations.Payment;
public class Booking {
  public static void createNewBooking(Connection conn) {

    Payment.createNewPayment(conn);
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
        System.out.println("Enter Payment Type\n" +
            "1: Debit\n" +
            "2: Credit\n" +
            "3: Cheque\n" +
            "4: Bank Transfer\n");
        Integer type;
        while (true) {
            try {
                type = input.nextInt();
                if (type > 4 || type < 0){
                  throw new Exception();
                }
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        System.out.println("Enter Billing Address");
        String pricePerNight = input.nextLine();
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

        input.close();
  }
}
