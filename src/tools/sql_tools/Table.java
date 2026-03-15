package tools.sql_tools;

import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.statements.Select;

import java.util.ArrayList;

public class Table {
    ArrayList<RowInTable> rows;
    String SQLQuery;

    //<editor-fold desc="Constructors">
    public Table(){}
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
            System.out.println("Row: " + i);
            rows.get(i).printAlLValuesAndKey();
            System.out.println();
            System.out.println();
        }
    }
}
