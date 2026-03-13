package tools.sql_tools;

import tools.sql_tools.general.RowInTable;
import tools.sql_tools.general.get_check_data.GetAmountOfRows;
import tools.sql_tools.general.statements.Select;

import java.util.ArrayList;

public class Table {
    ArrayList<RowInTable> rows;
    String SQLQuery;

    public Table(){}
    // TODO - no code, add code
    public Table(String tableName, String SQLQuery) {

    }

    public Table(String SQLQuery) {
        this.SQLQuery = SQLQuery;
        System.out.println("body");
    }

    public void getAllRowFromQuery(String SQLQuery) {
        rows = Select.selectAllRowsDataFromQuery(SQLQuery);
    }

    // TODO - no code, add code
    public void printTable(){

    }
}
