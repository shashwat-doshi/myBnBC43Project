package Operations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Payment {
  public static int createNewPayment(Connection conn) {
    int insertId = -1;
     Scanner input = new Scanner(System.in); // Create a Scanner object
        System.out.println("Lets input payment information...\n");
        System.out.println("Enter Card Number");
        String cardNumber;
        while (true) {
          cardNumber = input.nextLine();
          try {
            Integer.parseInt(cardNumber);
            break;
          }
          catch(Exception e) {
            System.out.println("Please try again");
          }
        }
        System.out.println("Enter Listing Start date yyyy-mm-dd");
        LocalDate expiryDate;
        while (true) {
          String expiryDateString = input.nextLine();
          try {
            expiryDate = LocalDate.parse(expiryDateString);
            break;
          }
          catch(Exception e) {
            System.out.println("Please try again");
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
      String billingAddress = input.nextLine();
        try {
          String insertPaymentSql = "INSERT INTO Payment (cardNumber, expiryDate, billingAddress, paymentTypeID) " +
          "values (?, ?, ?, ?);";
          PreparedStatement preparedStatement = conn.prepareStatement(insertPaymentSql, Statement.RETURN_GENERATED_KEYS);
          preparedStatement.setString(1, cardNumber);
          preparedStatement.setDate(2, Date.valueOf(expiryDate));
          preparedStatement.setString(3, billingAddress);
          preparedStatement.setInt(4, type);

          int rowAffected = preparedStatement.executeUpdate();
          ResultSet rs;
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    insertId = rs.getInt(1);
                }
            }
          System.out.println("Created payment with id" + insertId);
        }
        catch (Exception e) {
          System.out.println(e);
        }
        input.close();
        return insertId;
  }
}
