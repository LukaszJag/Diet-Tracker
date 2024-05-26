package sql_queries.csv_tools;


import tools.sql_tools.general.GetConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ExportDataBaseToCVSFile {

    public static void exportProductTableToCSVFile() throws SQLException, IOException {

        Connection connection = GetConnection.getConnectionWithLocalHost();


        String csvFilePath = "src/csv_files/product_table_csv_backup.csv";


        String sql = "SELECT * FROM product_table";

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery(sql);

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

        // write header line containing column names
        fileWriter.write("product_name, product_band,product_package_has,product_macro_for," +
                 "product_kcal,product_protein,product_fat,product_carbs,comment_optional");

        String product_name;
        String product_brand;
        float product_package_has;
        float product_macro_for;
        Float product_kcal;
        Float product_protein;
        Float product_fat;
        Float product_carbs;
        String comment_optional;

        while (result.next()) {

            product_name = result.getString("product_name");
            product_brand = result.getString("product_brand");
            product_package_has = result.getFloat("product_package_has");
            product_macro_for = result.getFloat("product_macro_for");

            product_kcal = result.getFloat("product_kcal");
            product_protein = result.getFloat("product_protein");
            product_fat = result.getFloat("product_fat");
            product_carbs = result.getFloat("product_carbs");

            comment_optional = result.getString("comment_optional");

            // public static final String[] SQL_COLUMNS_PRODUCT = {"`product_name`", "`product_brand`", "`product_package_has`",
            //        "`product_macro_for`", "`product_kcal`", "`product_protein`", "`product_fat`", "`product_carbs`", "`comment_optional`"};


            String line = String.format("\"%s\", %s, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %s",
                    product_name, product_brand, product_package_has, product_macro_for, product_kcal, product_protein, product_fat, product_carbs, comment_optional);

            fileWriter.newLine();
            fileWriter.write(line);
        }

        statement.close();
        fileWriter.close();

    }

    public static void exportDataFromTxtToSQLCalendarTable() {
    }
}
