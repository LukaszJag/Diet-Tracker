package tools.sql_tools.general;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetResultSet {


    Connection connection;
    ResultSet resultSet;
    Statement statementSQL;

    public GetResultSet(Connection connection) {
        this.connection = connection;
    }

    public GetResultSet() {

    }

    public GetResultSet(Connection connection, ResultSet resultSet, Statement statementSQL) {
        this.connection = connection;
        this.resultSet = resultSet;
        this.statementSQL = statementSQL;
    }


    public ResultSet getResultSetFromSQL(String SQLStatement) {
        Connection connection;
        ResultSet resultSet;
        Statement statementSQL;

        try {
            connection = GetConnection.getConnectionWithLocalHost();
            statementSQL = connection.createStatement();
            resultSet = statementSQL.executeQuery(SQLStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public static void getNextForResultSet(ResultSet resultSet) {
        try {
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFromResultSetGetString(String SQLColumnLabel, ResultSet resultSet) {
        try {
            return resultSet.getString(SQLColumnLabel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean resultSetNextReturnValue(ResultSet resultSet){
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
        } catch (Exception e) {}


    }
    public void closeAllVariables() {
        try {
            if (resultSet != null) resultSet.close();
        } catch (Exception e) {}

        try {
            if (statementSQL != null) statementSQL.close();
        } catch (Exception e) {}


        try {
            if (connection != null) connection.close();
        } catch (Exception e) {}

    }

    //<editor-fold desc="Getters and Setters">
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public Statement getStatementSQL() {
        return statementSQL;
    }

    public void setStatementSQL(Statement statementSQL) {
        this.statementSQL = statementSQL;
    }
    //</editor-fold>

}
