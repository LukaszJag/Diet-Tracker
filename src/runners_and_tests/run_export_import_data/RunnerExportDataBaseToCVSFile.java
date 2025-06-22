package runners_and_tests.run_export_import_data;

import sql_queries.csv_tools.ExportDataBaseToCVSFile;

import java.io.IOException;
import java.sql.SQLException;

public class RunnerExportDataBaseToCVSFile {
    public static void main(String[] args) {
        try {

            ExportDataBaseToCVSFile.exportProductTableToCSVFile();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
