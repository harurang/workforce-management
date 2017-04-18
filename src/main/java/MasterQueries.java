import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MasterQueries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            query1();
            query2();
            query3();          // incorrect
            query4();
            query5();
            query6();
            query7a();
            query7b();
            query8();
            query9();
            query10();
            query11();
            query15();
            query16();          // incorrect
            query17();          // incorrect
            query18();          // incorrect
            query19();
            query20();
            query21();
            query22();
            query23();
            query24();
            query25();

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void query1 () {
        System.out.println("\nQuery 1: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT PERSON.NAME\n" +
                            "FROM PAID_BY INNER JOIN PERSON\n" +
                            "ON PAID_BY.PER_ID = PERSON.PER_ID\n" +
                            "INNER JOIN JOB_LISTING\n" +
                            "ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "WHERE COMP_ID = 13");
            while ( rset.next() ) {
                String name = rset.getString("name");
                System.out.println("Name: " + name + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 1: " + e);
        }
    }

    public static void query2 () {
        System.out.println("\nQuery 2: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT NAME, PAY_RATE \n" +
                            "FROM PERSON INNER JOIN PAID_BY\n" +
                            "ON PERSON.PER_ID = PAID_BY.PER_ID\n" +
                            "INNER JOIN JOB_LISTING\n" +
                            "ON JOB_LISTING.LISTING_ID = PAID_BY.LISTING_ID\n" +
                            "INNER JOIN JOB \n" +
                            "ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "WHERE COMP_ID = 13 \n" +
                            "AND PAY_TYPE = 'salary'\n" +
                            "ORDER BY PAY_RATE DESC");
            while ( rset.next() ) {
                String name = rset.getString("name");
                Integer payRate = rset.getInt("pay_rate");
                System.out.println("Name: " + name + "\n" +  "Pay_rate:" + payRate +  "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 2: " + e);
        }
    }

    public static void query3 () {
        System.out.println("\nQuery 3: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT COMP_ID, SUM(nvl((PAY_RATE * HOURS) / 1920 , 0)) AS TOTAL_WAGE, \n" +
                            "SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS TOTAL_SAL\n" +
                            "FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                            "ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "INNER JOIN JOB\n" +
                            "ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                            "GROUP BY COMP_ID\n" +
                            "ORDER BY TOTAL_SAL DESC");
            while ( rset.next() ) {
                Integer compId = rset.getInt("comp_id");
                Integer totalSal = rset.getInt("total_sal");
                Float totalWage = rset.getFloat("total_wage");
                System.out.println("Company Id: " + compId + "\n" + "Total Salary: " + totalSal + "\n" +
                "Total Wage Rate: " + totalWage + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 3: " + e);
        }
    }

    public static void query4 () {
        System.out.println("\nQuery 4: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT PER_ID, JOB_TITLE, START_DATE, END_DATE \n" +
                            "FROM JOB_HISTORY  NATURAL JOIN JOB \n" +
                            "WHERE PER_ID=2");
            while ( rset.next() ) {
                Integer perId = rset.getInt("per_id");
                String jobTitle = rset.getString("job_title");
                String startDate = rset.getString("start_date");
                String endDate = rset.getString("end_date");
                System.out.println("Person Id: " + perId + "\n" + "Job Title:" + jobTitle + "\n" + "Start Date:" +
                        startDate + "\n" + "End Date:" + endDate + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 4: " + e);
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
                    "-- get job skills of person\n" +
                            "SELECT KS_CODE \n" +
                            "FROM PAID_BY INNER JOIN JOB_LISTING \n" +
                            "ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "INNER JOIN JOB_SKILL\n" +
                            "ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                            "WHERE PER_ID=2 \n" +
                            "\n" +
                            "MINUS\n" +
                            "\n" +
                            "-- get person skills \n" +
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

    public static void query11 () {
        System.out.println("\nQuery 11: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH COURSES AS (\n" +
                            "  SELECT TITLE, PRICE, SEC_ID FROM SECTION NATURAL JOIN (\n" +
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
                            "SELECT TITLE, PRICE, SEC_ID FROM COURSES WHERE PRICE = (SELECT MIN(PRICE) FROM COURSES)");
            while ( rset.next() ) {
                String title = rset.getString("title");
                String price = rset.getString("price");
                Integer sec_id = rset.getInt("sec_id");
                System.out.println("Course Title: " + title + "\n" + "Price: " + price + "\n"+
                        "Section Id: " + sec_id + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 11: " + e);
        }
    }

    public static void query15 () {
        System.out.println("\nQuery 15: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT DISTINCT NAME, EMAIL \n" +
                            "FROM PERSON A INNER JOIN PERSON_SKILL B\n" +
                            "ON A.PER_ID = B.PER_ID \n" +
                            "WHERE NOT EXISTS (\n" +
                            "\n" +
                            "  -- get skills of specific job\n" +
                            "  SELECT JOB_SKILL.KS_CODE\n" +
                            "  FROM JOB_SKILL\n" +
                            "  WHERE JOB_CODE=44\n" +
                            "  \n" +
                            "  MINUS\n" +
                            "  \n" +
                            "  -- get skills of person \n" +
                            "  (SELECT KS_CODE\n" +
                            "  FROM PERSON C INNER JOIN PERSON_SKILL D \n" +
                            "  ON C.PER_ID = D.PER_ID\n" +
                            "  WHERE A.NAME = C.NAME)\n" +
                            ")");
            while ( rset.next() ) {
                String name = rset.getString("name");
                String email = rset.getString("email");
                System.out.println("Name: " + name + "\n" + "Email:" + email + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 15: " + e);
        }
    }

    public static void query16 () {
        System.out.println("\nQuery 16: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT NAME\n" +
                            "FROM PERSON \n" +
                            "WHERE NOT EXISTS (\n" +
                            "\n" +
                            "SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE \n" +
                            "WHERE JOB_CODE=67 \n" +
                            "MINUS\n" +
                            "SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE");
            while ( rset.next() ) {
                String name = rset.getString("name");
                System.out.println("Name: " + name + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 16: " + e);
        }
    }

    public static void query17 () {
        System.out.println("\nQuery 17: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT DISTINCT PERSON_SKILL.KS_CODE, COUNT(DISTINCT NAME) AS TOTAL_COUNT\n" +
                            "FROM (SELECT NAME\n" +
                            "      FROM PERSON \n" +
                            "      WHERE NOT EXISTS (\n" +
                            "      \n" +
                            "      SELECT JOB_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "      FROM JOB_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "      ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE \n" +
                            "      WHERE JOB_CODE=67 \n" +
                            "      MINUS\n" +
                            "      SELECT PERSON_SKILL.KS_CODE, KNOWLEDGE_SKILL.TITLE \n" +
                            "      FROM PERSON_SKILL LEFT JOIN KNOWLEDGE_SKILL \n" +
                            "      ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE)\n" +
                            "), PERSON_SKILL, JOB \n" +
                            "WHERE JOB_CODE=91\n" +
                            "GROUP BY PERSON_SKILL.KS_CODE\n" +
                            "ORDER BY TOTAL_COUNT ASC");
            while ( rset.next() ) {
                Integer perCode = rset.getInt("ks_code");
                Integer totalCount = rset.getInt("total_count");
                System.out.println("knowledge Code:" + perCode + "\n" + "Total Count: " + totalCount + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 17: " + e);
        }
    }

    public static void query18 () {
        System.out.println("\nQuery 18: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH NEEDED_SKILLS AS ( \n" +
                    "\n" +
                    "SELECT KS_CODE \n" +
                    "FROM JOB_SKILL \n" +
                    "WHERE JOB_CODE=23), \n" +
                    "COUNT_NEEDED_SKILLS(PER_ID, MISSING_AMOUNT AS ( \n" +
                    "\n" +
                    "SELECT PER_ID, COUNT(KS_CODE) \n" +
                    "FROM PERSON P, NEEDED_SKILLS \n" +
                    "WHERE KS_CODE IN ( \n" +
                    "\n" +
                    "SELECT KS_CODE \n" +
                    "FROM NEEDED_SKILLS \n" +
                    "MINUS \n" +
                    "SELECT KS_CODE \n" +
                    "FROM PERSON_SKILL \n" +
                    "WHERE PER_ID=P.PER_ID) \n" +
                    "GROUP BY PER_ID \n" +
                    ") \n" +
                    "SELECT PER_ID, MISSING_AMOUNT \n" +
                    "FROM COUNT_NEEDED_SKILLS \n" +
                    "WHERE MISSING_AMOUNT = ( \n" +
                    "SELECT MIN(MISSING_AMOUNT) \n" +
                    "FROM COUNT_NEEDED_SKILLS) \n" +
                    "ORDER BY PER_ID ASC");
            while ( rset.next() ) {
                Integer perId = rset.getString("per_id");
                Integer missingSkills = rset.getInt("missing_skills");
                System.out.println("Per_Id: " + perId + "\n" + "Missing Skills:" + missingSkills + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 18: " + e);
        }
    }


   public static void query19 () {
        System.out.println("\nQuery 19: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                     "WITH MISSING_SKILLS AS ( \n" +
                     "\n" +
                     "SELECT KS_CODE \n" +
                     "FROM JOB_CATEGORY \n" +
                     "WHERE CATE_CODE=11), \n" +
                     "COUNT_MISSING_SKILLS(PER_ID, MISSING_AMOUNT) AS ( \n" +
                     "SELECT PER_ID, COUNT(KS_CODE) \n" +
                     "FROM PERSON P, MISSING_SKILLS \n" +
                     "WHERE KS_CODE IN ( \n" +
                     "SELECT KS_CODE \n" +
                     "FROM MISSING_SKILLS \n" +
                     "MINUS \n" +
                     "SELECT KS_CODE \n" +
                     "FROM PERSON_SKILL \n" +
                     "WHERE PER_ID=P.PER_ID) \n" +
                     "GROUP BY PER_ID \n" +
                     ") \n" +
                     "SELECT PER_ID, MISSING_AMOUNT \n" +
                     "FROM COUNT_MISSING_SKILLS \n" +
                     "WHERE MISSING_AMOUNT <=2 \n" +
                     "ORDER BY MISSING_AMOUNT ASC");
            while ( rset.next() ) {
                Integer perId = rset.getString("per_id");
                Integer missingSkills = rset.getString("missing_skills");
                System.out.println("Per_Id: " + perId + "\n" + "Missing Skills:" + missingSkills + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 19: " + e);
        }
    }


    public static void query20 () {
        System.out.println("\nQuery 20: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH MISSING_SKILLS AS ( \n" +
                            "\n" +
                            "SELECT KS_CODE \n" +
                            "FROM JOB_CATEGORY \n" +
                            "WHERE CATE_CODE=11), \n" +
                            "COUNT_MISSING_SKILLS(PER_ID, MISSING_SKILLS) AS ( \n" +
                            "\n" +
                            "SELECT PER_ID, COUNT(KS_CODE) \n" +
                            "FROM PERSON P, MISSING_SKILLS \n" +
                            "WHERE KS_CODE IN ( \n" +
                            "\n" +
                            "SELECT KS_CODE \n" +
                            "FROM MISSING_SKILLS \n" +
                            "MINUS \n" +
                            "SELECT KS_CODE \n" +
                            "FROM PERSON_SKILL \n" +
                            "WHERE PER_ID=P.PER_ID) \n" +
                            "GROUP BY PER_ID \n" +
                            "), \n" +
                            "MISSING_K AS ( \n" +
                            "\n" +
                            "SELECT PER_ID \n" +
                            "FROM COUNT_MISSING_SKILLS \n" +
                            "WHERE MISSING_AMOUNT <=2), \n" +
                            "PERSON_SKILLS_COUNT(KS_CODE, SKILLS_COUNT) AS \n" +
                            "SELECT KS_CODE, COUNT(PER_ID) \n" +
                            "FROM MISSING_K P, MISSING_SKILLS \n" +
                            "WHERE KS_CODE IN ( \n"  +
                            "\n" +
                            "SELECT KS_CODE \n" +
                            "FROM MISSING_SKILLS \n" +
                            "MINUS \n" +
                            "SELECT KS_CODE \n" +
                            "FROM PERSON_SKILL \n" +
                            "WHERE PER_ID=P.PER_ID) \n" +
                            "GROUP BY KS_CODE \n" +
                            ") \n" +
                            "SELECT KS_CODE, SKILLS_COUNT \n" +
                            "FROM PERSON_SKILLS_COUNT \n" +
                            "ORDER BY SKILLS_COUNT DESC");
            while ( rset.next() ) {
                Integer ksCode = rset.getString("ks_code");
                Integer skillsCount = rset.getString("skills_count");
                System.out.println("Ks_Code: " + ksCode + "\n" + "Skills_Count:" + skillsCount + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 20: " + e);
        }
    }
 


    public static void query21 () {
        System.out.println("\nQuery 21: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "select name \n" +
                            "from job_history inner join person\n" +
                            "on person.per_id = job_history.per_id\n" +
                            "inner join job_listing \n" +
                            "on job_history.listing_id = job_listing.listing_id\n" +
                            "inner join job\n" +
                            "on job_listing.job_code = job.job_code\n" +
                            "inner join job_category\n" +
                            "on job.cate_code = job_category.cate_code \n" +
                            "where job_category.title = 'Computer User Support Specialists'\n");
            while ( rset.next() ) {
                String name = rset.getString("name");
                System.out.println("Name: " + name + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 21: " + e);
        }
    }

    public static void query22 () {
        System.out.println("\nQuery 22: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "with unemployed as (\n" +
                            "  select per_id, name \n" +
                            "  from person\n" +
                            "  minus \n" +
                            "  select person.per_id, person.name\n" +
                            "  from paid_by inner join person\n" +
                            "  on paid_by.per_id = person.per_id\n" +
                            ")\n" +
                            "\n" +
                            "select name \n" +
                            "from unemployed inner join job_history \n" +
                            "on job_history.per_id = unemployed.per_id\n" +
                            "inner join job_listing\n" +
                            "on job_listing.listing_id = job_history.listing_id\n" +
                            "inner join job\n" +
                            "on job_listing.job_code = job.job_code\n" +
                            "inner join job_category\n" +
                            "on job.cate_code = job_category.cate_code \n" +
                            "where job_category.title = 'Computer User Support Specialists'");
            while ( rset.next() ) {
                String name = rset.getString("name");
                System.out.println("Name: " + name + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 22: " + e);
        }
    }

    public static void query23 () {
        System.out.println("\nQuery 23: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH COMP_PAYCHECKS AS (\n" +
                            "  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, COMP_NAME \n" +
                            "  FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                            "  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "  INNER JOIN JOB\n" +
                            "  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  GROUP BY COMP_NAME\n" +
                            "),\n" +
                            "\n" +
                            "-- gets number of employees for each company\n" +
                            "COMP_EMPLOYEE_COUNT AS (\n" +
                            "SELECT COMP_NAME, COUNT(*) AS NUMB_EMPLOYEES \n" +
                            "  FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                            "  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  GROUP BY COMP_NAME\n" +
                            ")\n" +
                            "\n" +
                            "SELECT COMP_NAME, SUM_SAL, NUMB_EMPLOYEES FROM COMP_PAYCHECKS NATURAL JOIN COMP_EMPLOYEE_COUNT\n" +
                            "WHERE SUM_SAL = \n" +
                            "(SELECT MAX(SUM_SAL) FROM COMP_PAYCHECKS) OR \n" +
                            "NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM COMP_EMPLOYEE_COUNT)");
            while ( rset.next() ) {
                String compName = rset.getString("comp_name");
                String sumSal = rset.getString("sum_sal");
                Integer numbEmployees = rset.getInt("numb_employees");
                System.out.println("Company Name: " + compName + "\n" + "Paycheck Sum: " + sumSal + "\n"+
                        "Number of Employees: " + numbEmployees + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 23: " + e);
        }
    }

    public static void query24 () {
        System.out.println("\nQuery 24: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH SECTOR_PAYCHECKS AS (\n" +
                            "  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, PRIMARY_SECTOR \n" +
                            "  FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                            "  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "  INNER JOIN JOB \n" +
                            "  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  GROUP BY PRIMARY_SECTOR\n" +
                            "),\n" +
                            "\n" +
                            "-- number of employees by sector\n" +
                            "SECTOR_EMPLOYEE_COUNT AS (\n" +
                            "  SELECT PRIMARY_SECTOR, COUNT(*) AS NUMB_EMPLOYEES \n" +
                            "  FROM PAID_BY INNER JOIN JOB_LISTING\n" +
                            "  ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  GROUP BY PRIMARY_SECTOR\n" +
                            ")\n" +
                            "\n" +
                            "SELECT PRIMARY_SECTOR, SUM_SAL, NUMB_EMPLOYEES FROM SECTOR_PAYCHECKS NATURAL JOIN SECTOR_EMPLOYEE_COUNT\n" +
                            "WHERE SUM_SAL = \n" +
                            "(SELECT MAX(SUM_SAL) FROM SECTOR_PAYCHECKS) OR \n" +
                            "NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM SECTOR_EMPLOYEE_COUNT)");
            while ( rset.next() ) {
                String sector = rset.getString("primary_sector");
                Integer numbEmployees = rset.getInt("numb_employees");
                Integer sumSal = rset.getInt("sum_sal");
                System.out.println("Primary Sector: " + sector + "\n" + "Number of Employees: " + numbEmployees + "\n"+
                        "Sum of Paychecks: " + sumSal + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 24: " + e);
        }
    }

    public static void query25 () {
        System.out.println("\nQuery 25: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "with previous_sal as (\n" +
                            "  -- get the previous salary or get the previous pay rate * hours\n" +
                            "  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as old_sal\n" +
                            "  from person \n" +
                            "  inner join job_history \n" +
                            "  on person.per_id = job_history.per_id\n" +
                            "  inner join job_listing\n" +
                            "  on job_history.listing_id = job_listing.listing_id\n" +
                            "  inner join job\n" +
                            "  on job.job_code = job_listing.job_code\n" +
                            "  inner join company\n" +
                            "  on job_listing.comp_id = company.comp_id\n" +
                            "  where primary_sector = 'Software Engineering'\n" +
                            "  group by name\n" +
                            "),\n" +
                            "\n" +
                            "-- Gets the current salary of employees in the Software Engineering primary sector\n" +
                            "current_sal as (\n" +
                            "  -- get the current salary or get the current pay rate * hours\n" +
                            "  select name, sum(nvl(pay_rate,0) + nvl(hours * pay_rate, 0)) as new_sal \n" +
                            "  from person \n" +
                            "  inner join paid_by \n" +
                            "  on person.per_id = paid_by.per_id\n" +
                            "  inner join job_listing\n" +
                            "  on job_listing.listing_id = paid_by.listing_id\n" +
                            "  inner join job\n" +
                            "  on job_listing.job_code = job.job_code\n" +
                            "  inner join company\n" +
                            "  on job_listing.comp_id = company.comp_id\n" +
                            "  where primary_sector = 'Software Engineering'\n" +
                            "  group by name\n" +
                            "),\n" +
                            "\n" +
                            "increase as (\n" +
                            "  select previous_sal.name, new_sal/old_sal as ratio from previous_sal \n" +
                            "  inner join current_sal\n" +
                            "  on previous_sal.name = current_sal.name\n" +
                            "  where new_sal/old_sal > 1\n" +
                            ")\n" +
                            "\n" +
                            "select avg(ratio) as average_increase from increase");
            while ( rset.next() ) {
                Float averageIncrease = rset.getFloat("average_increase");
                System.out.println("Average Increase: " + averageIncrease + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 25: " + e);
        }
    }
}

