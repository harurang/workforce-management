import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The career planning service needs information about workers’ education, training and skills in order to help find
 * workers suitable for a job.
 */

public class CareerPlanning {
    private Connection conn;

    public CareerPlanning (Connection conn) {
        this.conn = conn;
    }

    /**
     * Gets all qualified people for a job.
     *
     * @param jobCode
     * @return qualified people
     */
    public ArrayList<Person> getQualifiedPeople(int jobCode) {
        ArrayList<Person> results = new ArrayList<Person>();

        String name = "";
        String email = "";

        try {
            PreparedStatement pStmt = conn.prepareStatement("SELECT DISTINCT NAME, EMAIL \n" +
                    "FROM PERSON A INNER JOIN PERSON_SKILL B\n" +
                    "ON A.PER_ID = B.PER_ID \n" +
                    "WHERE NOT EXISTS (\n" +
                    "\n" +
                    "  -- get skills of specific job\n" +
                    "  SELECT JOB_SKILL.KS_CODE\n" +
                    "  FROM JOB_SKILL\n" +
                    "  WHERE JOB_CODE=?\n" +
                    "  \n" +
                    "  MINUS\n" +
                    "  \n" +
                    "  -- get skills of person\n" +
                    "  (SELECT KS_CODE\n" +
                    "  FROM PERSON C INNER JOIN PERSON_SKILL D \n" +
                    "  ON C.PER_ID = D.PER_ID\n" +
                    "  WHERE A.NAME = C.NAME)\n" +
                    ")");

            pStmt.setString(1, jobCode + "");
            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                name = rset.getString(1);
                email = rset.getString(2);

                Person person = new Person(name, email);
                results.add(person);
            }

        } catch(Exception e) {
            System.out.println("\nError in method getQualifiedPeople in CareerPlanning: " + e);
        }

        return results;
    }

    /**
     * Gets all job openings.
     *
     * @return a list of job openings
     */
    public ArrayList<String> getAllOpenings () {
        ArrayList<String> results = new ArrayList<String>();

        String title = "";
        int jobCode = 0;
        int numbOpenings = 0;

        try {
            PreparedStatement pStmt = conn.prepareStatement("  SELECT JOB_TITLE, JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS\n" +
                    "  FROM JOB_LISTING\n" +
                    "  NATURAL JOIN (\n" +
                    "    -- all job listings\n" +
                    "    SELECT JOB.JOB_CODE, JOB.JOB_TITLE\n" +
                    "    FROM JOB INNER JOIN JOB_LISTING\n" +
                    "    ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                    "    MINUS\n" +
                    "    -- filled job listings \n" +
                    "    SELECT JOB.JOB_CODE, JOB.JOB_TITLE\n" +
                    "    FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                    "    ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                    "    INNER JOIN JOB\n" +
                    "    ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE)\n" +
                    "    GROUP BY JOB_CODE, JOB_TITLE\n");

            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                title = rset.getString(1);
                jobCode = rset.getInt(2);
                numbOpenings = rset.getInt(3);

                String opening = "\n Job Title: " + title + " Job Code: " + jobCode + " Number of Openings: " + numbOpenings + "\n";
                results.add(opening);
            }

        } catch(Exception e) {
            System.out.println("\nError in method getAllOpenings in CareerPlanning: " + e);
        }

        return results;
    }
}
