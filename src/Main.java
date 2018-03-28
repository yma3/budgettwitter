public class Main {

    public static void main(String args[]) {
        SQLiteJDBCDriverConnection dbconn = new SQLiteJDBCDriverConnection();

        dbconn.deleteDatabase("C:/sqlite/db/test.db");


        dbconn.createNewDatabase("test.db");

        dbconn.createNewUserTable("test.db");
        dbconn.createNewPostTable("test.db");

        //dbconn.dropTable("test.db", "postTable");


        //dbconn.insertUserTable("yma3","Ben", "Ma", "ma3@cooper.edu", java.time.LocalDate.of(1990,1,1), 0,"test.db");
        //dbconn.updateUserTable("yma3","Bean","Mars","cheezey.goodness@gmail.com",java.time.LocalDate.of(2000,1,1));

        dbconn.insertPostTable("yma3","Hello World!", java.time.LocalDate.now(), 0);

        //dbconn.dropAllTables("test.db");
    }
}
