package Models;

import javafx.collections.ObservableList;

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

    public static ArrayList<String> getManufacturersWithOS(String os) throws SQLException {
        ArrayList<String> manufacturers=new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phones?useSSL=false",
                    user, password);

            //2.  create a sql statement
            String sql = "SELECT manufacturer FROM manufacturers WHERE os=?";

            //3.  create the PreparedStatement
            ps = conn.prepareStatement(sql);

            //4.  bind values to the parameters
            ps.setString(1, os);

            //5. get the results
            resultSet = ps.executeQuery();

            //6.  loop over the results from the DB and set the os
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
            if(ps != null)
                ps.close();
            if(resultSet != null)
                resultSet.close();
        }
        return manufacturers;
    }

    public static String getOSWithManufacturer(String manufacturer) throws SQLException {
        String os=null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phones?useSSL=false",
                    user, password);

            //2.  create a sql statement
            String sql = "SELECT os FROM manufacturers WHERE manufacturer=?";

            //3.  create the PreparedStatement
            ps = conn.prepareStatement(sql);

            //4.  bind values to the parameters
            ps.setString(1, manufacturer);

            //5. get the results
            resultSet = ps.executeQuery();

            //6.  loop over the results from the DB and set the os
            while (resultSet.next())
            {
                os = resultSet.getString("os");
            }
        } catch (Exception e)
        {
            System.err.println(e);
        }
        finally
        {
            if (conn != null)
                conn.close();
            if(ps != null)
                ps.close();
            if(resultSet != null)
                resultSet.close();
        }
        return os;
    }

    public static ArrayList<String> getAllOperatingSystems() throws SQLException {
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
            resultSet = statement.executeQuery("SELECT * FROM os");

            //4.  loop over the results from the DB and add to ArrayList
            while (resultSet.next())
            {
                manufacturers.add(resultSet.getString("os"));
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
