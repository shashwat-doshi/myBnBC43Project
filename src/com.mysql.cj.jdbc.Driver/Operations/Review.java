package Operations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import app.Main;

@SuppressWarnings("resource")
public class Review {
    public int reviewID, rating;
    public int commentID; // foreign key

    public Review(User user) {
        int reviewID = createReview(user);
        this.reviewID = reviewID;
    }

    public void setReviewInfo() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        int rating;

        System.out.println("Let us create a review...\n");
        System.out.println("Enter the rating you wish to provide...");
        while (true) {
            try {
                rating = input.nextInt();
                if (!(rating >= 1 && rating <= 5))
                    throw new Exception();
                break;
            } catch (Exception e) {
                System.out.println("Rating must be between 1 and 5 (included)! Choose again...");
                input.nextLine();
            }
        }
        input.nextLine(); // need to read \n after nextInt() is called

        this.rating = rating;
        Comment comment = new Comment();
        this.commentID = comment.commentID;
    }

    public boolean hasComment() {
        try {
            String sql = "SELECT * FROM Comment WHERE commentID = ?";
            PreparedStatement preparedStatement = Main.conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.commentID);
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next()) {
                System.out.println("No comments exist for this review!");
                return false;
            }
            do {
                // Display values
                System.out.print("Comment ID: " + rs.getInt("commentID"));
                System.out.print(", Details: " + rs.getString("Details"));
            } while (rs.next());
            rs.close();
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println("Unable to retrieve comment from the given comment ID, please try again!");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public int createReview(User user) {
        setReviewInfo();
        int reviewID = 0;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO Review (rating, commentID) " +
                    "VALUES (?, ?)";
            preparedStatement = Main.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, this.rating);
            preparedStatement.setInt(2, this.commentID);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    reviewID = rs.getInt(1);
                }
            }

            rs.close();
            preparedStatement.close();
            System.out.println("Created review with reviewID: " + reviewID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not create Review! Try again...");
        }

        return reviewID;
    }

}
