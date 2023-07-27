package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import app.Main;

public class Comment {
    public int commentID;
    public String details;

    public Comment() {
        this.commentID = createComment();
    }

    public int createComment() {
        int commentID = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            Scanner input = new Scanner(System.in); // Create a Scanner object
            System.out.println("Please enter the comment you want to post...");
            this.details = input.nextLine();
            String sql = "INSERT INTO Comment (details) " +
                    "VALUES (?)";
            preparedStatement = Main.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.details);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    commentID = rs.getInt(1);
                }
            }

            rs.close();
            preparedStatement.close();
            // input.close();
            System.out.println("Created comment with commentID: " + commentID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not create comment! Try again...");
        }

        return commentID;
    }
}
