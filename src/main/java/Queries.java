import java.sql.Connection;

/**
 * Created by hillaryarurang on 4/8/17.
 */
public class Queries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
