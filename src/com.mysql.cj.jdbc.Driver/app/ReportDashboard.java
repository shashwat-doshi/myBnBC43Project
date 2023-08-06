package app;

import java.util.Scanner;

import Reports.PerCountry;
import Reports.RenterReports;
import Reports.CancellationReports;
import Reports.ReportQueries;

@SuppressWarnings("resource")
public class ReportDashboard {
    public static boolean ReportDashboardCommandHandler(String cmd) {
        switch (cmd) {
            case "1":
                PerCountry.reportPerCountryCityPostalCode(true, false, false);
                break;
            case "2":
                PerCountry.reportPerCountryCityPostalCode(true, true, false);
                break;
            case "3":
                PerCountry.reportPerCountryCityPostalCode(true, true, true);
                break;
            case "4":
                PerCountry.reportPerPosterPerCounteryPerCity(true, false);
                break;
            case "5":
                PerCountry.reportPerPosterPerCounteryPerCity(true, true);
                break;
            case "6":
                RenterReports.reportWithinDatesPrompt(false);
                break;
            case "7":
                RenterReports.reportWithinDatesPrompt(true);
                break;
            case "8":
                CancellationReports.cancellationPrompt(true);
                break;
            case "9":
                CancellationReports.cancellationPrompt(false);
                break;
            case "10":
                ReportQueries.getNoOfBookingsByCity();
                break;
            case "11":
                ReportQueries.getNoOfBookingsByPCode();
                break;
            case "12":
                ReportQueries.reportHostsTenPercent();
                break;
            case "13":
                ReportQueries.reportNounPhrases();
                break;
            case "exit":
                return false;
            default:
                System.out.println("Invalid input! Try again!");
        }
        return true;
    }

    public static void ReportDashboardInterface() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String command;
        while (true) {
            System.out.println("Choose one of the following options:\n");
            System.out.println("1: Report number of Listings per Country");
            System.out.println("2: Report number of Listings per Country per City");
            System.out.println("3: Report number of Listings per Country per City per PostalCode");
            System.out.println("4: rank the hosts by the total number of listings they have " +
                    "overall per country");
            System.out.println("5: rank the hosts by the total number of listings they have " +
                    "overall per country and per city");
            System.out.println("6: rank the renters by the number of bookings in a " +
                    "specific time period");
            System.out.println("7: rank the renters by the number of bookings in a " +
                    "specific time period per city and having atleast 2 bookings");
            System.out.println("8: report the renters with the largest number of cancellations within a year");
            System.out.println("9: report the hosts with the largest number of cancellations within a year");
            System.out.println("10: Get total number of bookings in a specific date range by city");
            System.out.println("11: Get total number of bookings in a specific date range in a city by postal code");
            System.out.println(
                    "12: Report hosts that have number of listings more than 10% of the number of listings in that city and country");
            System.out.println(
                    "13: Report that presents for each listing the set of most popular noun phrases associated with the listing");
            System.out.println("exit: Go to main menu");
            command = input.nextLine(); // Read user input
            if (!ReportDashboardCommandHandler(command)) {
                break;
            }
        }
    }
}
