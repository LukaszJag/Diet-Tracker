package tools.sql_tools.general;

import tools.sql_tools.general.statements.Select;

import java.util.ArrayList;

public class Table {

    String tableName = "not set";;
    ArrayList<RowInTable> rows = new ArrayList<RowInTable>();
    String SQLQuery = "SELECT * FROM calendar WHERE day_date=\"2026-03-11\"";

    //<editor-fold desc="Constructors">
    public Table() {
//        this.tableName = "not set";
//        this.rows = new ArrayList<RowInTable>();
//        // default SQL Query
//        this.SQLQuery = "SELECT * FROM calendar WHERE day_date=\"2026-03-11\"";
//        getAllRowFromQuery();

    }

    // TODO - no code, add code to constructor -> public Table(String tableName, String SQLQuery)
    public Table(String SQLQuery) {
        this.rows = new ArrayList<RowInTable>();
        this.SQLQuery = SQLQuery;
        getAllRowFromQuery();
    }

    public Table(String tableName, String SQLQuery) {
        this.rows = new ArrayList<RowInTable>();
        getAllRowFromQuery();
    }


    //</editor-fold>

    //<editor-fold desc="Put methods">
    public void putRowToTable(RowInTable rowToInsert) {
        this.rows.add(rowToInsert);
    }
    //</editor-fold>

    //<editor-fold desc="Get methods">

    public void getAllRowFromQuery() {
        if (SQLQuery == null) {
            System.out.println("SQLQuery is null");
        }
        if (SQLQuery.isBlank()) {
            System.out.println("SQLQuery is Blank");
        }
        if (SQLQuery.isEmpty()) {
            System.out.println("SQLQuery is Empty");
        }

        setRows(Select.allRowsDataFromQuery(this.SQLQuery));
    }

    public RowInTable getRowInTable(int index) {
        if (rows.size() <= index) {
            System.out.println("getRowInTable(int index -> index: Out of bond: " + index);
            return null;
        }

        if (rows == null){
            System.out.println("rows is null");
            return null;
        }

        if (rows.isEmpty()){
            System.out.println("rows is empty");
            return null;
        }

        return rows.get(index);
    }
    //</editor-fold>

    //<editor-fold desc="Print methods">
    public void printTable() {
        for (int i = 0; i < rows.size(); i++) {
            System.out.println("Row:" + i);
            rows.get(i).printAlLValuesAndKey(rows.get(i));
            System.out.println();
            System.out.println();
        }
    }

    public void printSizeOfRows() {
        System.out.println("Size of rows: " + rows.size());
    }

    public void printSQLQuery() {
        System.out.println("SQLQuery: " + SQLQuery);
    }

    public void printTableName() {
        System.out.println("Table name: " + tableName);
    }


//    public void pe
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public ArrayList<RowInTable> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowInTable> rows) {
        this.rows = rows;
    }

    public String getSQLQuery() {
        return SQLQuery;
    }

    public void setSQLQuery(String SQLQuery) {
        this.SQLQuery = SQLQuery;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", rows=" + rows +
                ", SQLQuery='" + SQLQuery + '\'' +
                '}';
    }
}
