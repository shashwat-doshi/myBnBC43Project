package Operations;

import java.time.LocalDate;
import java.util.Scanner;

public class User {
    public void createUser() {
        Scanner myObj = new Scanner(System.in); // Create a Scanner object

        System.out.println("Before you get started, let us create your profile...\n");

        System.out.println("Enter your first name");
        String fname = myObj.nextLine();
        System.out.println("Enter your last name");
        String lname = myObj.nextLine();
        System.out.println("Are you an admin? 1 - yes, 0 - no");
        Integer adminChoice = myObj.nextInt();
        boolean isAdmin = adminChoice == 1 ? true : false;
        System.out.println("Enter your SIN Number");
        String SIN = myObj.nextLine();
        System.out.println("Enter your Address");
        String addr = myObj.nextLine();
        System.out.println("Enter your date of birth (in yyyy-mm-dd)");
        String userDob = myObj.nextLine();
        LocalDate dob = LocalDate.parse(userDob);
        System.out.println("Enter your occupation");
        String occupation = myObj.nextLine();

    }
}
