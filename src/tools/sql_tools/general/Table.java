package tools.sql_tools.general;

import tools.sql_tools.general.statements.Select;

import java.util.ArrayList;

public class Table {


    ArrayList<RowInTable> rows;
    String SQLQuery;

    //<editor-fold desc="Constructors">
    public Table(){
        this.rows = new ArrayList<RowInTable>();
    }

    // TODO - no code, add code to constructor -> public Table(String tableName, String SQLQuery)
    public Table(String tableName, String SQLQuery) {

    }

    public Table(String SQLQuery) {
        this.SQLQuery = SQLQuery;
        getAllRowFromQuery(SQLQuery);
    }
    //</editor-fold>

    //<editor-fold desc="Put methods">
    public void putRowToTable(RowInTable rowToInsert){
        this.rows.add(rowToInsert);
    }
    //</editor-fold>

    //<editor-fold desc="Get methods">
    public void getAllRowFromQuery(String SQLQuery) {
        rows = Select.selectAllRowsDataFromQuery(SQLQuery);
    }

    public RowInTable getRowInTable(int index){
        if(rows.size() < index){
            System.out.println("index: Out of bond");
            return null;
        }
        return getRowInTable(index);
    }
    //</editor-fold>

    //<editor-fold desc="Print methods">
    public void printTable(){
        for (int i = 0; i < rows.size(); i++) {
            System.out.println("Row in Table: " + i);
            rows.get(i).printAlLValuesAndKey(rows.get(i));
            System.out.println();
            System.out.println();
        }
    }
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
    //</editor-fold>

}
