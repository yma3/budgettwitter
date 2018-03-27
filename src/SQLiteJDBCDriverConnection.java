import java.sql.*;

public class BTDatabase {
    //Connect to a single database
    public static void connect() {
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
    }

    //Create a dtabase
    public static void createNewDatabase(String fileName) {
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

    public static void createNewTable(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS userTable (\n" + "username text PRIMARY KEY,\n" + "first_name text,\n" + "last_name text,\n" + "birthday int\n" + ");";

        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            System.out.println("CREATING TABLE WITH FOLLOWING INPUT:");
            System.out.println(sql);
            stmt.execute(sql);
            System.out.println("Table Added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printAllTables(String fileName) {
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

    public static void dropAllTables(String fileName) {
        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
        String sql = "DROP TABLE IF EXISTS ";
        try(Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement();) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(conn.getCatalog(), null, "%", null);
            while(tables.next()) {
                System.out.println("Table name: " + tables.getString(3));
                String sqldrop = sql + tables.getString(3) + ";";
                System.out.println("Command: " + sqldrop);
                stmt.execute(sqldrop);
                System.out.println("Table dropped.");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /*
        MAIN
     */
    public static void main(String[] args) {
        //createNewDatabase("test.db");
        createNewTable("test.db");
        System.out.println("Print Tables");
        printAllTables("test.db");
        System.out.println("Drop Tables");
        dropAllTables("test.db");
    }
}