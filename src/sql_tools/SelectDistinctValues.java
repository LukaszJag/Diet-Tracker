package sql_tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectDistinctValues {

    public static String[] selectAllUniqueCalendarByDayDate(){
        // It may cause error by hard code array length
        int daysAmount = 100;
        String[] tempArray = new String[daysAmount];
        String[] allUnique;
        String sqlStatement = "SELECT DISTINCT day_date FROM calendar";
        Connection connection;
        ResultSet resultSet;
        Statement statement;
        int counter = 0;
        try {
            connection = GetConnection.getConnectionWithLocalHost();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);

            while(resultSet.next()){
                tempArray[counter] = resultSet.getString(1);
                counter++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        allUnique = new String[counter];

        for (int i = 0; i < counter; i++) {
            allUnique[i] = tempArray[i];
        }

        return allUnique;
    }
}
