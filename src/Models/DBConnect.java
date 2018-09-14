package Models;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    private static String user = "student";
    private static String password = "student";

    public static ArrayList<String> getPhoneManufacturers() throws SQLException {
        ArrayList<String> manufacturers = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phones?useSSL=false",
                    user, password);

            //2.  create a statement object
            statement = conn.createStatement();

            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM manufacturers");

            //4.  loop over the results from the DB and add to ArrayList
            while (resultSet.next())
            {
                manufacturers.add(resultSet.getString("manufacturer"));
            }
        } catch (Exception e)
        {
            System.err.println(e);
        }
        finally
        {
            if (conn != null)
                conn.close();
            if(statement != null)
                statement.close();
            if(resultSet != null)
                resultSet.close();
        }
        return manufacturers;
    }
}
