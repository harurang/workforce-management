import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *The training service can recommend the courses for a person who pursues
 *a job by recognizing the missing skills of the person for the job.
 */

public class TrainingService{
    private Connection conn;

    public TrainingService(Connection conn){
        this.conn = conn;
    }


    /**
     *Gets all people missing a skill for a job
     *
     *@param jobCode
     *@return person with missing skill
     */

    public ArrayList<String> getMissingSkills(int jobCode, int perId){
        ArrayList<String> results = new ArrayList<String>();

        int ksCode = 0;
        String title = "";

        try{
            PreparedStatement pStmt = conn.prepareStatement("SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                    "FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                    "ON JOB_SKILL.KS_CODE=KNOWLEDGE_SKILL.KS_CODE \n" +
                    "WHERE JOB_CODE=? \n" +
                    "MINUS \n" +
                    "SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                    "FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                    "ON PERSON_SKILL.KS_CODE=KNOWLEDGE_SKILL.KS_CODE \n" +
                    "WHERE PER_ID=?");



            pStmt.setString(1, jobCode + "");
            pStmt.setString(2, perId + "");
            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                ksCode = rset.getInt(1);
                title = rset.getString(2);

                String missingSkills= "\nKnowledge Skill: " + ksCode + "Job Title: " + title + "\n";
                results.add(missingSkills);

            }

        }catch(Exception e){
            System.out.println("\nError in method getMissingSkills in TrainingService: " + e);

        }
        return results;

    }


    /**
     *Gets courses needed for a job
     *
     *@returns the course needed of specific job
     */

    public ArrayList<Course> getNeededCourse(int jobCode, int perId) {
        ArrayList<Course> results = new ArrayList<Course>();

        String title = "";
        int cCode = 0;

        try{
            PreparedStatement pStmt = conn.prepareStatement(
                    "SELECT DISTINCT C.TITLE, C.C_CODE\n" +
                    "FROM COURSE C\n" +
                    "WHERE NOT EXISTS (\n" +
                      "SELECT JOB_SKILL.KS_CODE\n" +
                      "FROM JOB_SKILL\n" +
                      "WHERE JOB_CODE=?\n" +
                      "MINUS\n" +
                      "SELECT PERSON_SKILL.KS_CODE\n" +
                      "FROM PERSON_SKILL\n" +
                      "WHERE PER_ID=?\n" +

                      "MINUS\n" +

            "(SELECT KS_CODE\n" +
             "FROM COURSE_KNOWLEDGE A\n" +
             "WHERE C.C_CODE = A.C_CODE))");


            pStmt.setString(1, jobCode + "");
            pStmt.setString(2, perId + "");
            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                title = rset.getString(1);
                cCode = rset.getInt(2);

                Course course = new Course(title, cCode);
                results.add(course);
            }




        }catch(Exception e){
            System.out.println("\n Error in the method getNeededCourse in TrainingService: " + e);
        }
        return results;
    }
}

