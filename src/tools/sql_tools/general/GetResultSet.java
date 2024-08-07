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


    public static ResultSet getResultSetFromSQL(String SQLStatement) {
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

    public static String getFromResultSetByColumnNumberString(int columnNumber, ResultSet resultSet) {
        try {
            return resultSet.getString(columnNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean resultSetNextReturnValue(ResultSet resultSet) {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
        } catch (Exception e) {
        }


    }

    public void closeAllVariables() {
        try {
            if (this.resultSet != null) this.resultSet.close();
        } catch (Exception e) {
        }

        try {
            if (this.statementSQL != null) this.statementSQL.close();
        } catch (Exception e) {
        }


        try {
            if (this.connection != null) this.connection.close();
        } catch (Exception e) {
        }

    }

    public void checkGetResultSetStatus() {


        try {
            if (this.connection == null) {
                System.out.println("Connection is null");
            } else if (this.connection.isClosed() == false) {
                System.out.println("Connection is active");
            }

            if (this.statementSQL == null) {
                System.out.println("Statement is null");
            } else if (this.statementSQL.isClosed() == false) {
                System.out.println("Statement is active");
            }

            if (this.resultSet == null) {
                System.out.println("ResultSet is null");
            } else if (this.resultSet.next() == false) {
                System.out.println("ResultSet is active");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
