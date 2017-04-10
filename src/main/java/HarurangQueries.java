import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HarurangQueries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

//            query5();
//            query6();
//            query7a();
//            query7b();
//            query8();
//            query9();
            query10();

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
                Integer skillCode = rset.getInt("ks_code");
                System.out.println("Name: " + name + "Knowledge Skill: " + skillCode + "\n");
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
                Integer skillCode = rset.getInt("ks_code");
                System.out.println("Knowledge Code: " + skillCode + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 6: " + e);
        }
    }

    public static void query7a () {
        System.out.println("\nQuery 7a: \n");
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
            System.out.println("\nError at query 7a: " + e);
        }
    }

    public static void query7b () {
        System.out.println("\nQuery 7b: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT JOB_CATEGORY.TITLE AS JOB_CATEGORY_TITLE, KNOWLEDGE_SKILL.TITLE AS SKILL_TITLE\n" +
                            "FROM JOB_CATEGORY LEFT JOIN KNOWLEDGE_SKILL ON JOB_CATEGORY.KS_CODE = KNOWLEDGE_SKILL.KS_CODE");
            while ( rset.next() ) {
                String jobCat = rset.getString("job_category_title");
                String skill = rset.getString("skill_title");
                System.out.println("Job Category: " + jobCat + "\n" + "Knowledge Skill: " + skill + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 7b: " + e);
        }
    }

    public static void query8 () {
        System.out.println("\nQuery 8: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE JOB_CODE=91\n" +
                            "MINUS\n" +
                            "SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE PER_ID=5");
            while ( rset.next() ) {
                Integer skillCode = rset.getInt("ks_code");
                String skill = rset.getString("title");
                System.out.println("Knowledge Title: " + skill + "\n" + "Knowledge Code: " + skillCode + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 8: " + e);
        }
    }

    public static void query9 () {
        System.out.println("\nQuery 9: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT DISTINCT C.TITLE, C.C_CODE\n" +
                            "FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S\n" +
                            "ON C.C_CODE = S.C_CODE\n" +
                            "WHERE NOT EXISTS (\n" +
                            "\n" +
                            "  SELECT JOB_SKILL.KS_CODE\n" +
                            "  FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "  ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "  WHERE JOB_CODE=44\n" +
                            "  MINUS\n" +
                            "  SELECT PERSON_SKILL.KS_CODE \n" +
                            "  FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "  ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "  WHERE PER_ID=176\n" +
                            "  \n" +
                            "  MINUS\n" +
                            "  (SELECT KS_CODE\n" +
                            "  FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B\n" +
                            "  ON A.C_CODE = B.C_CODE\n" +
                            "  WHERE C.TITLE = A.TITLE)\n" +
                            ")");
            while ( rset.next() ) {
                Integer courseCode = rset.getInt("c_code");
                String courseTitle = rset.getString("title");
                System.out.println("Course Title: " + courseTitle + "\n" + "Course Code: " + courseCode + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 9: " + e);
        }
    }

    public static void query10 () {
        System.out.println("\nQuery 10: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH COURSES AS (\n" +
                            "  SELECT TITLE, SECTION.START_DATE, SECTION.END_DATE, SECTION.FORMAT, SECTION.OFFERED_BY FROM SECTION NATURAL JOIN (\n" +
                            "    SELECT DISTINCT C.TITLE, C.C_CODE\n" +
                            "    FROM COURSE C RIGHT JOIN COURSE_KNOWLEDGE S\n" +
                            "    ON C.C_CODE = S.C_CODE\n" +
                            "    WHERE NOT EXISTS (\n" +
                            "    \n" +
                            "      SELECT JOB_SKILL.KS_CODE\n" +
                            "      FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "      ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "      WHERE JOB_CODE=44\n" +
                            "      MINUS\n" +
                            "      SELECT PERSON_SKILL.KS_CODE \n" +
                            "      FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "      ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "      WHERE PER_ID=176\n" +
                            "      \n" +
                            "      MINUS\n" +
                            "      (SELECT KS_CODE\n" +
                            "      FROM COURSE A RIGHT JOIN COURSE_KNOWLEDGE B\n" +
                            "      ON A.C_CODE = B.C_CODE\n" +
                            "      WHERE C.TITLE = A.TITLE)\n" +
                            "    )\n" +
                            "  )\n" +
                            ")\n" +
                            "\n" +
                            "SELECT TITLE, START_DATE, END_DATE, FORMAT, OFFERED_BY \n" +
                            "FROM COURSES \n" +
                            "WHERE START_DATE = (SELECT MIN(TO_DATE(START_DATE)) FROM COURSES)");
            while ( rset.next() ) {
                String title = rset.getString("title");
                String startDate = rset.getString("start_date");
                String endDate = rset.getString("end_date");
                String sectionFormat = rset.getString("format");
                String sectionOffered = rset.getString("offered_by");
                System.out.println("Course Title: " + title + "\n" + "Start Date: " + startDate + "\n"+
                        "End Date: " + endDate + "\n" + "Section Format: " + "\n" + sectionFormat + "Section Offered: " + "\n"
                        + sectionOffered + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 10: " + e);
        }
    }
}
