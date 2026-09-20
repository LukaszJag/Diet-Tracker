package tools.sql_tools.general;

import tools.debug_tools.Debug;

import java.util.ArrayList;

public class Table {

    String tableName = "not set";;
    ArrayList<RowInTable> rows;
    String SQLQuery;

    //<editor-fold desc="Constructors">
    public Table() {
        this.rows = new ArrayList<>();
    }

    // TODO - no code, add code to constructor -> public Table(String tableName, String SQLQuery)
    public Table(String SQLQuery) {
        this.SQLQuery = SQLQuery;
        getAllRowFromClassQuery();
    }

    public Table(String tableName, String SQLQuery) {
        this.rows = new ArrayList<RowInTable>();
        getAllRowFromClassQuery();
    }


    //</editor-fold>

    //<editor-fold desc="Put methods">
    public void putRowToTable(RowInTable rowToInsert) {
        rows.add(rowToInsert);
    }

    public void putRowToTable(RowInTable rowToInsert, boolean printEveryRow) {
        this.rows.add(rowToInsert);
        if (printEveryRow) {
            rowToInsert.printAlLValuesAndKey();
            System.out.println();
            System.out.println();
        }
    }

    public void printAllRowsInTable(){
        for (int i = 0; i < rows.size(); i++) {
            Debug.printYellowSystemPrintln("Row: " + i);
            rows.get(i).printAlLValuesAndKey();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Get methods">

    public int getAmountOfRowsInTable(){
        return rows.size();
    }

    public void getAllRowFromClassQuery() {
        if (SQLQuery == null) {
            System.out.println("SQLQuery is null");
        }
        if (SQLQuery.isBlank()) {
            System.out.println("SQLQuery is Blank");
        }
        if (SQLQuery.isEmpty()) {
            System.out.println("SQLQuery is Empty");
        }

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
            rows.get(i).printAlLValuesAndKey();
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
