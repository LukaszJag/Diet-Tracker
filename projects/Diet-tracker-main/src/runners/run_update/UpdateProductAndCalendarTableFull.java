package runners.run_update;

import runners.run_export_import_data.AddAllCalendarDaysInTXTFromDirectory;
import runners.run_export_import_data.AddAllProductInTXTFromDirectory;

import java.sql.SQLException;

public class UpdateProductAndCalendarTableFull {
    public static void main(String[] args) throws SQLException {
        updateProductAndCalendarTableFull();
    }

    public static void updateProductAndCalendarTableFull() throws SQLException {
        System.out.println("Run: UpdateProductAndCalendarTableFull");

        System.out.println("\nAddAllProductInTXTFromDirectory: START");
        AddAllProductInTXTFromDirectory.main(null);
        System.out.println("\nAddAllProductInTXTFromDirectory: PASS");

        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: START");
        AddAllCalendarDaysInTXTFromDirectory.main(null);
        System.out.println("\nAddAllCalendarDaysInTXTFromDirectory: PASS");
    }
}
