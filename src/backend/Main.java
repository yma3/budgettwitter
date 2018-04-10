package f2018.ece366.budgettwitter;

public class Main {

    public static void main(String args[]) {
        SQLiteJDBCDriverConnection dbconn = new SQLiteJDBCDriverConnection();

        dbconn.deleteDatabase("~/twitter.db");
        String fileName = "twitter.db";

        dbconn.createNewDatabase(fileName);

        dbconn.createNewUserTable(fileName);
        dbconn.createNewPostTable(fileName);

        //dbconn.dropTable("test.db", "postTable");


        dbconn.insertUserTable("yma3", "password","Ben", "Ma", "ma3@cooper.edu", java.time.LocalDate.of(1990,1,1), 0,fileName);
        dbconn.insertUserTable("yma2", "password","Bean", "Mars", "cheezey.goodness@gmail.com", java.time.LocalDate.of(1990,1,1), 0,fileName);
        //dbconn.insertUserTable("yma2", "password","Ben", "Ma", "ma3@cooper.edu", java.time.LocalDate.of(1990,1,1), 0,"test.db");
        //dbconn.updateUserTable("yma3","Bean","Mars","cheezey.goodness@gmail.com",java.time.LocalDate.of(2000,1,1));

        //dbconn.deleteFromUserTable("yma3");

        dbconn.insertPostTable(0,"yma3","Hello World!", java.time.LocalDate.now(), 0);
        //dbconn.deleteFromPostTable(0);
        //dbconn.dropAllTables("test.db");
    }
}
