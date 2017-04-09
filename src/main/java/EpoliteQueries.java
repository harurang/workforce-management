import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EpoliteQueries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            // TODO: put your username and password here
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            query5();

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    // example
    public static void query5 () {
        System.out.println("\nQuery 5: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT PERSON.NAME, KNOWLEDGE_SKILL.KS_CODE \n" +
                            "FROM PERSON LEFT JOIN PERSON_SKILL ON PERSON.PER_ID = PERSON_SKILL.PER_ID \n" +
                            "LEFT JOIN KNOWLEDGE_SKILL ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE");
            while ( rset.next() ) {
                String name = rset.getString("name");
                Integer skill = rset.getInt("ks_code");
                System.out.println("Name: " + name + "Knowledge Skill: " + skill + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 5: " + e);
        }
    }
}

