package backend;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class SQLiteJDBCDriverConnection {
    //Connect to a single database
    public Connection connect() {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}

        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:~/twitter.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            //System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    //Create a dtabase
    public void createNewDatabase(String fileName) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;

        try(Connection conn = this.connect()) {
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
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        File f = new File(fdir);
        if(f.delete()) {
            System.out.println("File at " + fdir + " deleted.");
        } else {
            System.out.println("Could not delete.");
        }
    }

    public void createNewUserTable(String fileName) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS userTable (\n" + "username text PRIMARY KEY,\n" + "password text,\n"+"first_name text,\n" + "last_name text,\n" + "email text,\n" + "birthday int,\n" + "FollowerNum int\n"+");";
        try(Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
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
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS postTable (\n" + "postID int PRIMARY KEY,\n" + "username text,\n" + "postData text,\n" + "postTime int,\n" + "likeNum int\n" + ");";

        try(Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Post table created.");
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    public void dropTable(String fileName, String tableName) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;
        String sql = "DROP TABLE IF EXISTS " + tableName;
        try(Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table dropped");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void printAllTables(String fileName) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;
        try(Connection conn = this.connect();) {
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
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        List<String> tableNames = new ArrayList<>();
        String url = "jdbc:sqlite:~/" + fileName;
        String sql = "DROP TABLE IF EXISTS ";
        try(Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
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

    public void insertUserTable(String username, String password, String first_name, String last_name, String email, LocalDate birthday, Integer FollowerNum, String fileName) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/" + fileName;
        String sql = "INSERT INTO userTable(username, password, first_name, last_name, email, birthday, FollowerNum) VALUES(?,?,?,?,?,?,?)";

        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, first_name);
            pstmt.setString(4, last_name);
            pstmt.setString(5, email);
            pstmt.setObject(6, birthday);
            pstmt.setInt(7, FollowerNum);
            pstmt.executeUpdate();
            System.out.println("Inserted: " + username + "," + first_name + ", " + last_name + "," + email + "," + birthday.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserTable(String username, String first_name, String last_name, String email, LocalDate birthday) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/twitter.db";
        String sql = "UPDATE userTable SET first_name = ? , " + "last_name = ? , " + "email = ? ," + "birthday = ?" + "WHERE username = ?";

        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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

    public void deleteFromUserTable(String username) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/twitter.db";
        String sql = "DELETE FROM userTable WHERE username = ?";
        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            System.out.println("username: " + username + " deleted from userTable.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFromPostTable(int postID) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/twitter.db";
        String sql = "DELETE FROM postTable WHERE postID = ?";
        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.executeUpdate();
            System.out.println("postID: " + postID + " deleted from postTable.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertPostTable(Integer postID, String username, String postData, LocalDate postTime, Integer likeNum) {
    	try {
    	    Class.forName("org.sqlite.JDBC");
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    System.err.println("Error: can't find drivers!");
    	}
        String url = "jdbc:sqlite:~/twitter.db";
        String sql = "INSERT INTO postTable(postID, username, postData, postTime, likeNum) VALUES(?,?,?,?,?)";

        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postID);
            pstmt.setString(2, username);
            pstmt.setString(3, postData);
            pstmt.setObject(4, postTime);
            pstmt.setInt(5, likeNum);
            pstmt.executeUpdate();
            System.out.println("Inserted into postTable");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}