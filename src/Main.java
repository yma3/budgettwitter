public class Main {

    public static void main(String args[]) {
        SQLiteJDBCDriverConnection dbconn = new SQLiteJDBCDriverConnection();
        dbconn.createNewTable("test.db");
    }
}
