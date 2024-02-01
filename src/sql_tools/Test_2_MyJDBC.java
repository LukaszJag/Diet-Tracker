package sql_tools;

import java.sql.*;

public class Test_2_MyJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/university",
                    "root",
                    "sword444"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test_simple_table");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("int_test"));
                System.out.println(resultSet.getString("string_test"));
            }
            String two = "two";
            String sql = "INSERT INTO `university`.`test_simple_table`(`int_test`,`string_test`) VALUES (2,\"" +two + "\");";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}
