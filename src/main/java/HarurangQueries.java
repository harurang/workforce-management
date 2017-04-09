import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HarurangQueries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            query5();
            query6();
            query7();

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

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

    public static void query6 () {
        System.out.println("\nQuery 6: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT KS_CODE FROM PAID_BY LEFT JOIN JOB_SKILL ON PAID_BY.JOB_CODE = JOB_SKILL.JOB_CODE\n" +
                            "WHERE PER_ID=2 \n" +
                            "MINUS\n" +
                            "SELECT KS_CODE FROM PERSON_SKILL WHERE PER_ID=2");
            while ( rset.next() ) {
                Integer skill = rset.getInt("ks_code");
                System.out.println("Knowledge Code: " + skill + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 6: " + e);
        }
    }

    public static void query7 () {
        System.out.println("\nQuery 7: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT JOB_TITLE, TITLE AS SKILL \n" +
                            "FROM JOB_SKILL LEFT JOIN JOB ON JOB_SKILL.JOB_CODE = JOB.JOB_CODE \n" +
                            "LEFT JOIN KNOWLEDGE_SKILL ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE IMPORTANCE='required'");
            while ( rset.next() ) {
                String jobTitle = rset.getString("job_title");
                String skill = rset.getString("skill");
                System.out.println("Job Title: " + jobTitle + "\n" + "Knowledge Skill: " + skill + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 7: " + e);
        }
    }
}
