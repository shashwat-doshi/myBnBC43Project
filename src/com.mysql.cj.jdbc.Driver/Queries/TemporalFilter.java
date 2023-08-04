package Queries;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

@SuppressWarnings("resource")
public class TemporalFilter {

    public static String filterString = "";

    public static String applyFilters() {
        filterString = "";
        String filterDate = applyTemporalFilterDates();
        if (!filterDate.equals("")) {
            filterString = filterDate;
        }
        String filterPriceRange = applyTemporalFilterPriceRange();
        if ((!filterString.equals("")) && (!filterPriceRange.equals(""))) {
            filterString += "AND " + filterPriceRange;
        } else if ((filterString.equals("") && (!filterPriceRange.equals("")))) {
            filterString = filterPriceRange;
        }
        String filterPostalCode = applyTemporalFilterPostalCode();
        if ((!filterString.equals("")) && (!filterPostalCode.equals(""))) {
            filterString += "AND " + filterPostalCode;
        } else if ((filterString.equals("") && (!filterPostalCode.equals("")))) {
            filterString = filterPostalCode;
        }
        String filterAddress = applyTemporalFilterAddress();
        if ((!filterString.equals("")) && (!filterAddress.equals(""))) {
            filterString += "AND " + filterAddress;
        } else if ((filterString.equals("") && (!filterAddress.equals("")))) {
            filterString = filterAddress;
        }
        filterString.trim();
        return filterString;
    }

    public static String applyTemporalFilterAddress() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sql = "";
        while (true) {
            try {
                System.out.println("Do you want to apply a filter for listing's address (exact match)? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y")) {
                    System.out.println("Enter the address you want to filter your listings by:");
                    String address = input.nextLine();
                    sql = "p.street = '" + address + "' ";
                    break;
                } else if (choice.equals("N")) {
                    sql = "";
                    break;
                } else {
                    throw new Exception("Incorrect choice! Please try again...");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sql;
    }

    public static String applyTemporalFilterDates() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sqlFilterDate = "";
        Timestamp startDate, endDate;
        while (true) {
            try {
                System.out.println("Do you want to apply a filter for listing's date range? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y")) {
                    System.out.println("Enter the start date for the filter:");
                    while (true) {
                        String startDateString = input.nextLine();
                        try {
                            LocalDate localDate = LocalDate.parse(startDateString);
                            startDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(15, 0)));
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect format of the start date! Please try again...");
                        }
                    }
                    System.out.println("Enter the end date for the filter:");
                    while (true) {
                        String endDateString = input.nextLine();
                        try {
                            LocalDate localDate = LocalDate.parse(endDateString);
                            endDate = Timestamp.valueOf(LocalDateTime.of(localDate, LocalTime.of(11, 0)));
                            break;
                        } catch (Exception e) {
                            System.out.println("Incorrect format of the end date! Please try again...");
                        }
                    }

                    if (startDate.compareTo(endDate) < 0) {
                        sqlFilterDate = "(UNIX_TIMESTAMP(l.startDate) >= " + "UNIX_TIMESTAMP('"
                                + startDate.toString() + "')"
                                + " AND UNIX_TIMESTAMP(l.endDate) <= "
                                + " UNIX_TIMESTAMP('" + endDate.toString() + "') "
                                + ") ";
                    } else {
                        throw new Exception("End date should be greater than start date! Please try again...");
                    }
                    break;
                } else if (choice.equals("N")) {
                    sqlFilterDate = "";
                    break;
                } else {
                    throw new Exception("Incorrect choice! Please try again...");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sqlFilterDate;
    }

    public static String applyTemporalFilterPostalCode() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sql = "";
        while (true) {
            try {
                System.out.println("Do you want to apply a filter for listing's postal code? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y")) {
                    System.out.println("Enter the postal code you want to filter your listings by:");
                    String postalCode = input.nextLine();
                    sql = "p.postalCode = '" + postalCode + "' ";
                    break;
                } else if (choice.equals("N")) {
                    sql = "";
                    break;
                } else {
                    throw new Exception("Incorrect choice! Please try again...");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sql;
    }

    public static String applyTemporalFilterPriceRange() {
        Scanner input = new Scanner(System.in); // Create a Scanner object
        String sql = "";
        Float minPrice, maxPrice;
        while (true) {
            try {
                System.out.println("Do you want to apply a filter for listing's price range? (Y/N)");
                String choice = input.nextLine();
                if (choice.equals("Y")) {
                    System.out.println("Enter the minimum price for the search:");
                    while (true) {
                        try {
                            minPrice = input.nextFloat();
                            if (minPrice < 0) {
                                throw new Exception("Price cannot be less than 0!");
                            }
                            input.nextLine();
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please enter a correct Floating type...");
                            input.nextLine();
                        }
                    }
                    System.out.println("Enter the maximum price for the search:");
                    while (true) {
                        try {
                            maxPrice = input.nextFloat();
                            if (maxPrice < 0) {
                                throw new Exception("Price cannot be less than 0!");
                            }
                            if (maxPrice < minPrice) {
                                throw new Exception("Max price cannot be less than min price!");
                            }
                            input.nextLine();
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            System.out.println("Please enter a correct Floating type...");
                            input.nextLine();
                        }
                    }
                    sql = "l.pricePerNight >= " + minPrice + " AND l.pricePerNight <= " + maxPrice + " ";
                    break;
                } else if (choice.equals("N")) {
                    sql = "";
                    break;
                } else {
                    throw new Exception("Incorrect choice! Please try again...");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return sql;
    }
}
