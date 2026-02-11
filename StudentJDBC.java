

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class StudentJDBC 
{
    static final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    static final String USER = "system";
    static final String PASSWORD = "samaina_14";

    public static void main(String[] args) 
    {   
        // try-with-resources for Connection
        try (
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD)
        ) 
        {
            //load driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Database connected!");
            //insert data using PreparedStatement
            String insertSql = "INSERT INTO system.students (name, age, email) VALUES (?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSql)) 
            {
                ps.setString(1, "Aishani");
                ps.setInt(2, 20);
                ps.setString(3, "aish@example.com");
                ps.executeUpdate();
                System.out.println("Record inserted");
            }
            //update record
            String updateSql = "UPDATE system.students SET age = ? WHERE id = ?";
            try (PreparedStatement updatePs = con.prepareStatement(updateSql)) 
            {
                updatePs.setInt(1, 22);
                updatePs.setInt(2, 1);
                int rowsUpdated = updatePs.executeUpdate();
                System.out.println(rowsUpdated + " record updated");
            }
            //delete record
            String deleteSql = "DELETE FROM system.students WHERE id = ?";
            try (PreparedStatement deletePs = con.prepareStatement(deleteSql)) 
            {
                deletePs.setInt(1, 1);
                int rowsDeleted = deletePs.executeUpdate();
                System.out.println(rowsDeleted + " record deleted");
            }
            //retrieve data
            String selectSql = "SELECT * FROM system.students";
            try (
                PreparedStatement selectPs = con.prepareStatement(selectSql);
                ResultSet rs = selectPs.executeQuery()
            ) 
            {
                while (rs.next()) 
                    {
                    System.out.println(
                        rs.getInt("id") + " " +
                        rs.getString("name") + " " +
                        rs.getInt("age") + " " +
                        rs.getString("email")
                    );
                }
            }

        }
        catch (ClassNotFoundException e) 
        {
            System.out.println("JDBC Driver not found. Please add MySQL Connector JAR.");
        }
        catch (SQLException e) 
        {
            System.out.println("Database error occurred!");
            System.out.println("Error Message: " + e.getMessage());
        }
        catch (Exception e) 
        {
            System.out.println("Unexpected error occurred.");
        }
    }
}