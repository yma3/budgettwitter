import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class SQLiteJDBCDriverConnection {
    //Connect to a single database
    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/test.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    //Create a dtabase
    public void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;

        try(Connection conn = DriverManager.getConnection(url)) {
            if(conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver is: " + meta.getDriverName());
                System.out.println("A new database has been created!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDatabase(String fdir) {
        File f = new File(fdir);
        if(f.delete()) {
            System.out.println("File at " + fdir + " deleted.");
        } else {
            System.out.println("Could not delete.");
        }
    }

    public void createNewUserTable(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS userTable (\n" + "username text PRIMARY KEY,\n" + "first_name text,\n" + "last_name text,\n" + "email text,\n" + "birthday int,\n" + "FollowerNum int\n"+");";
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            //System.out.println("CREATING TABLE WITH FOLLOWING INPUT:");
            //System.out.println(sql);
            stmt.execute(sql);
            stmt.close();
            System.out.println("Table Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewPostTable(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS postTable (\n" + "username text PRIMARY KEY,\n" + "postData text,\n" + "postTime int,\n" + "likeNum int\n" + ");";

        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Post table created.");
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    public void dropTable(String fileName, String tableName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table dropped");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printAllTables(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        try(Connection conn = DriverManager.getConnection(url);) {
            DatabaseMetaData m = conn.getMetaData();
            ResultSet rs = m.getTables(conn.getCatalog(), null, "%", null);
            int i = 1;
            while(rs.next()) {
                System.out.println(i + ". " + rs.getString(3));
                i++;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropAllTables(String fileName) {
        List<String> tableNames = new ArrayList<>();
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "DROP TABLE IF EXISTS ";
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(conn.getCatalog(), null, "%", null);
            while(tables.next()) {
                tableNames.add(tables.getString(3));
            }
            tables.close();
            for (String table : tableNames) {
                System.out.println("Table name: " + table);
                String sqldrop = sql + table + ";";
                System.out.println("Command: " + sqldrop);
                stmt.execute(sqldrop);
                System.out.println("Table dropped.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUserTable(String username, String first_name, String last_name, String email, LocalDate birthday, Integer FollowerNum, String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "INSERT INTO userTable(username, first_name, last_name, email, birthday, FollowerNum) VALUES(?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, email);
            pstmt.setObject(5, birthday);
            pstmt.setInt(6, FollowerNum);
            pstmt.executeUpdate();
            System.out.println("Inserted: " + username + "," + first_name + ", " + last_name + "," + email + "," + birthday.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserTable(String username, String first_name, String last_name, String email, LocalDate birthday) {
        String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "UPDATE userTable SET first_name = ? , " + "last_name = ? , " + "email = ? ," + "birthday = ?" + "WHERE username = ?";

        try(Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setObject(4, birthday);
            pstmt.setString(5, username);
            pstmt.executeUpdate();
            System.out.println("Database Updated.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertPostTable(String username, String postData, LocalDate postTime, Integer likeNum) {
        String url = "jdbc:sqlite:C:/sqlite/db/test.db";
        String sql = "INSERT INTO postTable(username, postData, postTime, likeNum) VALUES(?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, postData);
            pstmt.setObject(3, postTime);
            pstmt.setInt(4, likeNum);
            pstmt.executeUpdate();
            System.out.println("Inserted into postTable");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}