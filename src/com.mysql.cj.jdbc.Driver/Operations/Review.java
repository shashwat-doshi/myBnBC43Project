package Operations;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

public class Review {
    public int reviewID, rating;
    public int commentID; // foreign key

    public Review(Connection conn, User user) {
        int reviewID = createReview(conn, user);
        this.reviewID = reviewID;
        // do something for comment?
    }

    public void setReviewInfo() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        Integer rating;

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
        int commentID = getCommentID();
        input.close();
    }

    public int createReview(Connection conn) {
        setReviewInfo();
        // do something here

    }

}
