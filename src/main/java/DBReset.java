import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBReset {
    private Connection conn;

    public DBReset(Connection conn) {
        this.conn = conn;
    }

    public void removeInsertFromTask8a(Person person, ArrayList<Integer> paidBy, ArrayList<Integer> takes,
                                       ArrayList<JobHistory> history, ArrayList<Integer> skills) {
        try {
            // delete job a person is paid by
            for(Integer jobListing : paidBy) {
                PreparedStatement pStmt = conn.prepareStatement("DELETE FROM PAID_BY " +
                        "WHERE LISTING_ID=? AND PER_ID=?");

                pStmt.setString(1, jobListing + "");
                pStmt.setString(2, person.getPerId() + "");
                ResultSet rset = pStmt.executeQuery();
            }

            // delete sections taken
            for(Integer section : takes) {
                PreparedStatement pStmt = conn.prepareStatement("DELETE FROM TAKES" +
                        " WHERE SEC_ID=? AND PER_ID=?");

                pStmt.setString(1, section + "");
                pStmt.setString(2, person.getPerId() + "");
                ResultSet rset = pStmt.executeQuery();
            }

            // delete job histories
            for(JobHistory job : history) {
                PreparedStatement pStmt = conn.prepareStatement("DELETE FROM JOB_HISTORY" +
                        " WHERE LISTING_ID=? AND PER_ID=?");

                pStmt.setString(1, job.getJobListing() + "");
                pStmt.setString(2, person.getPerId() + "");
                ResultSet rset = pStmt.executeQuery();
            }

            // delete skills
            for(Integer skill : skills) {
                PreparedStatement pStmt = conn.prepareStatement("DELETE FROM PERSON_SKILL" +
                        " WHERE KS_CODE=? AND PER_ID=?");

                pStmt.setString(1, skill + "");
                pStmt.setString(2, person.getPerId() + "");
                ResultSet rset = pStmt.executeQuery();
            }

            // delete phone numbers
            PreparedStatement pStmt = conn.prepareStatement("DELETE FROM PHONE_NUMBER" +
                    " WHERE PER_ID=?");

            pStmt.setString(1, person.getPerId() + "");
            ResultSet rset = pStmt.executeQuery();

            // delete person
            pStmt = conn.prepareStatement("DELETE FROM PERSON " +
                    "WHERE PER_ID=?");

            pStmt.setString(1, person.getPerId() + "");
            rset = pStmt.executeQuery();

        } catch(Exception e) {
            System.out.println("\nError removing inserts from Task 8 a: " + e);
        }
    }
}
