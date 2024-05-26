package runners_and_tests.tests;

import sql_queries.csv_tools.ExportDataBaseToCVSFile;

import java.io.IOException;
import java.sql.SQLException;

public class TestExportDataBaseToCVSFile {
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
