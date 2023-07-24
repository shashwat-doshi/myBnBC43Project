package Operations;

import java.time.LocalDate;
import java.util.Scanner;

public class User {
    public void createUser() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        Integer adminChoice = 0;
        LocalDate dob;

        System.out.println("Before you get started, let us create your profile...\n");

        System.out.println("Enter your first name");
        String fname = input.nextLine();
        System.out.println("Enter your last name");
        String lname = input.nextLine();
        System.out.println("Are you an admin? 1 - yes, 0 - no");
        while (true) {
            try {
                adminChoice = input.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Incorrect choice! Choose again...");
                input.nextLine();
            }
        }
        boolean isAdmin = adminChoice == 1 ? true : false;
        input.nextLine(); // need to read \n after nextInt() is called
        System.out.println("Enter your SIN Number");
        String SIN = input.nextLine();
        System.out.println("Enter your Address");
        String addr = input.nextLine();
        System.out.println("Enter your date of birth (in yyyy-mm-dd)");
        while (true) {
            try {
                String userDob = input.nextLine();
                dob = LocalDate.parse(userDob);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }
        System.out.println("Enter your occupation");
        String occupation = input.nextLine();

        System.out.println("Here's your completed profile:");
        System.out.println("SIN is: " + SIN);
        System.out.println(
                fname + "\n" + lname + "\n" + isAdmin + "\n" + SIN + "\n" + addr + "\n" + dob
                        + "\n" + occupation);

        input.close();

    }
}
