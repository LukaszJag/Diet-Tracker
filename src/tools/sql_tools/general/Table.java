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

    // TODO - no code, add code
    public Table(String tableName, String SQLQuery) {

    }

    public Table(String SQLQuery) {
        this.SQLQuery = SQLQuery;
        getAllRowFromQuery(SQLQuery);
    }
    //</editor-fold>

    // TODO - no code, add code

    public void getAllRowFromQuery(String SQLQuery) {
        rows = Select.selectAllRowsDataFromQuery(SQLQuery);
    }

    public void printTable(){
        for (int i = 0; i < rows.size(); i++) {
            System.out.println("Row in Table: " + i);
            rows.get(i).printAlLValuesAndKey(rows.get(i));
            System.out.println();
            System.out.println();
        }
    }

    public void putRowToTable(RowInTable rowToInsert){
        this.rows.add(rowToInsert);
    }
}
