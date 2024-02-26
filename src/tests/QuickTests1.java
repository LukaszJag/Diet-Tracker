package tests;

import sql_tools.SQLSelect;

import java.sql.SQLException;

public class QuickTests1 {
    public static void main(String[] args) throws SQLException {
        SQLSelect.getRowFromProductTableByProductName("Burak");
        SQLSelect.getRowFromProductTableByProductName("Jogurt kiwi");
        SQLSelect.getRowFromProductTableByProductName("Rower");
    }
}
