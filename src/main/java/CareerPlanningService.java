import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The career planning service needs information about workers’ education, training and skills in order to help find
 * workers suitable for a job.
 */

public class CareerPlanningService {
    private Connection conn;

    public CareerPlanningService(Connection conn) {
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
                    "FROM PERSON NATURAL JOIN (\n" +
                    "  SELECT PER_ID\n" +
                    "  FROM PERSON_SKILL A\n" +
                    "  WHERE NOT EXISTS (\n" +
                    "  \n" +
                    "    -- get skills of specific job\n" +
                    "    SELECT JOB_SKILL.KS_CODE\n" +
                    "    FROM JOB_SKILL\n" +
                    "    WHERE JOB_CODE=?\n" +
                    "    \n" +
                    "    MINUS\n" +
                    "    \n" +
                    "    -- get skills of person\n" +
                    "    (SELECT KS_CODE\n" +
                    "    FROM PERSON_SKILL B\n" +
                    "    WHERE A.PER_ID = B.PER_ID)\n" +
                    "  )\n" +
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
            System.out.println("\nError in method getQualifiedPeople in CareerPlanningService: " + e);
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

        int jobCode = 0;
        int numbOpenings = 0;

        try {
            PreparedStatement pStmt = conn.prepareStatement("SELECT JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS\n" +
                    "  FROM JOB_LISTING\n" +
                    "  NATURAL JOIN (\n" +
                    "    -- all job listings\n" +
                    "    SELECT JOB_CODE\n" +
                    "    FROM JOB_LISTING\n" +
                    "    MINUS\n" +
                    "    -- filled job listings \n" +
                    "    SELECT JOB_CODE\n" +
                    "    FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                    "    ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID)\n" +
                    "  GROUP BY JOB_CODE");

            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                jobCode = rset.getInt(1);
                numbOpenings = rset.getInt(2);

                String opening = "Job Code: " + jobCode + " Number of Openings: " + numbOpenings + "\n";
                results.add(opening);
            }

        } catch(Exception e) {
            System.out.println("\nError in method getAllOpenings in CareerPlanningService: " + e);
        }
        return results;
    }
}
