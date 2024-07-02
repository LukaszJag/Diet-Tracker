package runners_and_tests.run_update;

import runners_and_tests.run_export_inport_data.AddAllCalendarDaysInTXTFromDirectory;
import runners_and_tests.run_export_inport_data.AddAllProductInTXTFromDirectory;

import java.sql.SQLException;

public class UpdateProductAndCalendarTableFull {
    public static void main(String[] args) throws SQLException {
        System.out.println("Run: UpdateProductAndCalendarTableFull");

        System.out.println("\nAddAllProductInTXTFromDirectory: START");
        AddAllProductInTXTFromDirectory.main(null);
        System.out.println("\nAddAllProductInTXTFromDirectory: PASS");

        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: START");
        AddAllCalendarDaysInTXTFromDirectory.main(null);
        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: PASS");
    }

}
