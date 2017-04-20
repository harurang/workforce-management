import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task7 {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            deleteJob();
            createJob();

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteJob () {
        String table = "job";
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "drop table job_history");

            rset = stmt.executeQuery(
                    "drop table job_skill");

            rset = stmt.executeQuery(
                    "drop table paid_by");

            rset = stmt.executeQuery(
                    "drop table job_listing");

            rset = stmt.executeQuery(
                    "drop table job");

            System.out.println("\n" + table + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + table + ": " + e);
        }
    }

    public static void createJob () {
        String table = "job";
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "CREATE TABLE job (\n" +
                            "  job_code number,\n" +
                            "  pay_rate number,\n" +
                            "  pay_type varchar(20),\n" +
                            "  hours number,\n" +
                            "  cate_code number,\n" +
                            "  job_title varchar(70),\n" +
                            "  primary key (job_code),\n" +
                            "  foreign key (cate_code) references job_category(cate_code)\n" +
                            ")");

            System.out.println("\n" + table + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + table + ": " + e);
        }
    }
}