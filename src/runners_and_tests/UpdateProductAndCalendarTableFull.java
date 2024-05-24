package runners_and_tests;

import java.sql.SQLException;

public class UpdateProductAndCalendarTableFull {
    public static void main(String[] args) throws SQLException {
        System.out.println("Run: UpdateProductAndCalendarTableFull");

        System.out.println("\nAddAllProductInTXTFromDirectory: START");
        AddAllProductInTXTFromDirectory.main();
        System.out.println("\nAddAllProductInTXTFromDirectory: PASS");

        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: START");
        AddAllCalendarDaysInTXTFromDirectory.main();
        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: PASS");
    }
}
