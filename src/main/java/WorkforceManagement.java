import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class WorkforceManagement {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");

        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            // c) A company finds the right people for a job with training
            ArrayList<Person> results = instantiateCompany(3).getQualifiedPeople(44);
            for(Person person : results) {
                System.out.println(person.getName());
            }

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Company instantiateCompany (int compId) {
        String compName = "";
        int nCode = 0;
        String city = "";
        String state = "";
        int zip = 0;
        String website = "";
        String primarySector = "";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "select * from company where comp_id=" + compId);

            while ( rset.next() ) {
                compName = rset.getString("comp_name");
                nCode = rset.getInt("n_code");
                city = rset.getString("city");
                state = rset.getString("state");
                zip = rset.getInt("zip");
                website = rset.getString("website");
                primarySector = rset.getString("primary_sector");
            }

        } catch(Exception e) {
            System.out.println("\nError at method getQualifiedPeople() in WorkforceManagement: " + e);
        }

        return new Company(compId, compName, nCode, city, state, zip, website, primarySector, conn);
    }
}
