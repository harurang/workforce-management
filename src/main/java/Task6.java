//Hillary Arurang
//Eresha Polite
//Spring 2017

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task6 {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            query1();
            query2();
            query3();
            query4();
            query5();
            query6();
            query7a();
            query7b();
            query8();
            query9();
            query10();
            query11();
            query13();
            query14();
            query15();
            query16();
            query17();
            query18();
            query19();
            query20();
            query21();
            query22();
            query23();
            query24();
            query25();
            query26();
            query27();

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
                    "SELECT COMP_ID, SUM(NVL((PAY_RATE * HOURS) / 1920 , 0)) AS TOTAL_WAGE, \n" +
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
                    "SELECT PER_ID, JOB_TITLE, START_DATE, END_DATE\n" +
                            "FROM JOB_HISTORY NATURAL JOIN JOB\n" +
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
                            "FROM PERSON INNER JOIN PERSON_SKILL \n" +
                            "ON PERSON.PER_ID = PERSON_SKILL.PER_ID \n" +
                            "INNER JOIN KNOWLEDGE_SKILL \n" +
                            "ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE PERSON.PER_ID = 209");
            while ( rset.next() ) {
                String name = rset.getString("name");
                Integer skillCode = rset.getInt("ks_code");
                System.out.println("Name: " + name + "\n" + "Knowledge Skill: " + skillCode + "\n");
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
                    "SELECT KS_CODE \n" +
                            "FROM PAID_BY INNER JOIN JOB_LISTING \n" +
                            "ON PAID_BY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "INNER JOIN JOB_SKILL\n" +
                            "ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                            "WHERE PER_ID=2 \n" +
                            "\n" +
                            "MINUS\n" +
                            "\n" +
                            "-- ger person skills\n" +
                            "SELECT KS_CODE \n" +
                            "FROM PERSON_SKILL WHERE PER_ID=2");
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
                    "SELECT TITLE\n" +
                            "FROM JOB_SKILL INNER JOIN KNOWLEDGE_SKILL \n" +
                            "ON JOB_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE JOB_SKILL.JOB_CODE = 23 AND IMPORTANCE='required'");
            while ( rset.next() ) {
                String skill = rset.getString("title");
                System.out.println("Knowledge Skill: " + skill + "\n");
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
                    "SELECT KNOWLEDGE_SKILL.TITLE\n" +
                            "FROM JOB_CATEGORY INNER JOIN KNOWLEDGE_SKILL \n" +
                            "ON JOB_CATEGORY.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                            "WHERE JOB_CATEGORY.CATE_CODE=4");
            while ( rset.next() ) {
                String skill = rset.getString("title");
                System.out.println("Knowledge Skill: " + skill + "\n");
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
                    "SELECT KS_CODE, TITLE FROM (\n" +
                            "  SELECT JOB_SKILL.KS_CODE\n" +
                            "  FROM JOB_SKILL \n" +
                            "  WHERE JOB_CODE=23\n" +
                            "  MINUS\n" +
                            "  SELECT PERSON_SKILL.KS_CODE\n" +
                            "  FROM PERSON_SKILL \n" +
                            "  WHERE PER_ID=2\n" +
                            ") NATURAL JOIN KNOWLEDGE_SKILL");
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
                            "FROM COURSE C\n" +
                            "WHERE NOT EXISTS (\n" +
                            "  -- skills a person is missing for a job\n" +
                            "  SELECT JOB_SKILL.KS_CODE\n" +
                            "  FROM JOB_SKILL \n" +
                            "  WHERE JOB_CODE=44\n" +
                            "  MINUS\n" +
                            "  SELECT PERSON_SKILL.KS_CODE \n" +
                            "  FROM PERSON_SKILL\n" +
                            "  WHERE PER_ID=176\n" +
                            "  \n" +
                            "  MINUS\n" +
                            "  -- skills of a course \n" +
                            "  (SELECT KS_CODE\n" +
                            "  FROM COURSE_KNOWLEDGE A\n" +
                            "  WHERE C.C_CODE = A.C_CODE)\n" +
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
                            "    SELECT DISTINCT C.C_CODE\n" +
                            "    FROM COURSE C\n" +
                            "    WHERE NOT EXISTS (\n" +
                            "    \n" +
                            "      SELECT JOB_SKILL.KS_CODE\n" +
                            "      FROM JOB_SKILL\n" +
                            "      WHERE JOB_CODE=23\n" +
                            "      MINUS\n" +
                            "      SELECT PERSON_SKILL.KS_CODE \n" +
                            "      FROM PERSON_SKILL \n" +
                            "      WHERE PER_ID=2\n" +
                            "      \n" +
                            "      MINUS\n" +
                            "      (SELECT KS_CODE\n" +
                            "      FROM COURSE_KNOWLEDGE A\n" +
                            "      WHERE C.C_CODE = A.C_CODE)\n" +
                            "    )\n" +
                            ")\n" +
                            "\n" +
                            "SELECT DISTINCT TITLE, START_DATE, END_DATE, FORMAT, OFFERED_BY \n" +
                            "FROM COURSES NATURAL JOIN SECTION NATURAL JOIN COURSE\n" +
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
                            "    SELECT DISTINCT C.C_CODE\n" +
                            "    FROM COURSE_KNOWLEDGE C\n" +
                            "    WHERE NOT EXISTS (\n" +
                            "    \n" +
                            "      SELECT JOB_SKILL.KS_CODE\n" +
                            "      FROM JOB_SKILL \n" +
                            "      WHERE JOB_CODE=23\n" +
                            "      MINUS\n" +
                            "      SELECT PERSON_SKILL.KS_CODE \n" +
                            "      FROM PERSON_SKILL\n" +
                            "      WHERE PER_ID=2\n" +
                            "      \n" +
                            "      MINUS\n" +
                            "      (SELECT KS_CODE\n" +
                            "      FROM COURSE_KNOWLEDGE A\n" +
                            "      WHERE A.C_CODE = C.C_CODE)\n" +
                            "  )\n" +
                            ")\n" +
                            "\n" +
                            "SELECT TITLE, PRICE \n" +
                            "FROM SECTION NATURAL JOIN COURSES \n" +
                            "NATURAL JOIN COURSE \n" +
                            "WHERE PRICE = (SELECT MIN(PRICE) FROM SECTION)");
            while ( rset.next() ) {
                String title = rset.getString("title");
                String price = rset.getString("price");
                System.out.println("Course Title: " + title + "\n" + "Price: " + price + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 11: " + e);
        }
    }
   
    public static void query13 () {
        System.out.println("\nQuery 13: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT DISTINCT TITLE\n" +
                            "FROM JOB_CATEGORY JC\n" +
                            "WHERE EXISTS (\n" +
                            "\n" +
                            "SELECT KS_CODE\n" +
                            "FROM JOB_SKILL JB\n" +
                            "WHERE JC.KS_CODE=JB.KS_CODE\n" +
                            "MINUS\n" +
                            "SELECT KS_CODE\n" +
                            "FROM PERSON_SKILL\n" +
                            "WHERE PER_ID = 7)");
            while ( rset.next() ) {
                String title = rset.getString("title");
                System.out.println("Title: " + title + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 13: " + e);
        }
    }

     public static void query14() {
        System.out.println("\nQuery 14: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH SKILL_QUALIFICATION AS (\n" +
                            "\n" +
                            "SELECT DISTINCT TITLE\n" +
                            "FROM JOB_CATEGORY JC\n" +
                            "WHERE EXISTS ( \n" +
                            "\n" +
                            "SELECT KS_CODE\n" +
                            "FROM JOB_SKILL JB \n" +
                            "WHERE JC.KS_CODE=JB.KS_CODE\n" +
                            "MINUS\n" +
                            "SELECT KS_CODE\n" +
                            "FROM PERSON_SKILL\n" +
                            "WHERE PER_ID=7) \n" +
                            ") \n" +
                            "SELECT MAX(PAY_RATE) AS PAY_RATE\n" +
                            "FROM SKILL_QUALIFICATION NATURAL JOIN JOB");
            while ( rset.next() ) {
                Integer payRate = rset.getInt("pay_rate");
                System.out.println("Highest Pay Rate: " + payRate + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 14: " + e);
        }
    }




    public static void query15 () {
        System.out.println("\nQuery 15: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "SELECT DISTINCT NAME, EMAIL \n" +
                            "FROM PERSON NATURAL JOIN (\n" +
                            "  SELECT PER_ID\n" +
                            "  FROM PERSON_SKILL A\n" +
                            "  WHERE NOT EXISTS (\n" +
                            "  \n" +
                            "    -- get skills of specific job\n" +
                            "    SELECT JOB_SKILL.KS_CODE\n" +
                            "    FROM JOB_SKILL\n" +
                            "    WHERE JOB_CODE=44\n" +
                            "    \n" +
                            "    MINUS\n" +
                            "    \n" +
                            "    -- get skills of person\n" +
                            "    (SELECT KS_CODE\n" +
                            "    FROM PERSON_SKILL B\n" +
                            "    WHERE A.PER_ID = B.PER_ID)\n" +
                            "  )\n" +
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
                    "WITH PERSON_REQUIRED_SKILL_CNT AS (\n" +
                            "  SELECT PER_ID, COUNT(KS_CODE) AS SKILL_COUNT\n" +
                            "  FROM PERSON_SKILL NATURAL JOIN JOB_SKILL\n" +
                            "  WHERE JOB_CODE = 31\n" +
                            "  GROUP BY PER_ID\n" +
                            ")\n" +
                            "\n" +
                            "SELECT PER_ID\n" +
                            "FROM PERSON_REQUIRED_SKILL_CNT\n" +
                            "WHERE SKILL_COUNT = (SELECT COUNT(*) - 1\n" +
                            "FROM JOB_SKILL\n" +
                            "WHERE JOB_CODE = 31)");
            while ( rset.next() ) {
                Integer personId = rset.getInt("per_id");
                System.out.println("Person Id: " + personId + "\n");
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
                    "WITH PERSON_SKILL_CNT AS (\n" +
                            "  SELECT PER_ID, COUNT(KS_CODE) AS SKILL_COUNT\n" +
                            "  FROM PERSON_SKILL NATURAL JOIN JOB_SKILL\n" +
                            "  WHERE JOB_CODE = 31\n" +
                            "  GROUP BY PER_ID\n" +
                            "),\n" +
                            "\n" +
                            "-- people who miss a skill by one\n" +
                            "MISSING_ONE AS (\n" +
                            "  SELECT PER_ID\n" +
                            "  FROM PERSON_SKILL_CNT\n" +
                            "  WHERE SKILL_COUNT = (SELECT COUNT(*) - 1\n" +
                            "  FROM JOB_SKILL\n" +
                            "  WHERE JOB_CODE = 31)\n" +
                            "),\n" +
                            "\n" +
                            "-- skills of a specific job\n" +
                            "SKILLS_OF_JOB AS (\n" +
                            "  SELECT KS_CODE \n" +
                            "  FROM JOB_SKILL\n" +
                            "  WHERE JOB_CODE = 31\n" +
                            ")\n" +
                            "\n" +
                            "SELECT SKILLS_OF_JOB.KS_CODE, COUNT(SKILLS_OF_JOB.KS_CODE) AS NUMB_MISSING \n" +
                            "FROM MISSING_ONE INNER JOIN PERSON_SKILL\n" +
                            "ON MISSING_ONE.PER_ID = PERSON_SKILL.PER_ID\n" +
                            "INNER JOIN SKILLS_OF_JOB\n" +
                            "ON SKILLS_OF_JOB.KS_CODE = PERSON_SKILL.KS_CODE\n" +
                            "GROUP BY SKILLS_OF_JOB.KS_CODE\n" +
                            "ORDER BY NUMB_MISSING ASC");
            while ( rset.next() ) {
                Integer perCode = rset.getInt("ks_code");
                Integer numbPpl = rset.getInt("numb_missing");
                System.out.println("Knowledge Code:" + perCode + "\n" + "Number of People : " + numbPpl + "\n");
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
                  "WITH COUNT_NEEDED_SKILLS AS (\n" +
                    "SELECT PER_ID, COUNT(DISTINCT KS_CODE) AS MISSING_AMOUNT\n" +
                    "FROM PERSON P NATURAL JOIN JOB_SKILL\n" +
                    "WHERE KS_CODE IN (\n" +
                        "SELECT KS_CODE\n" + 
                        "FROM JOB_SKILL\n" +
                        "WHERE JOB_CODE=23\n" +
                        "MINUS\n" +
                        "SELECT KS_CODE\n" + 
                        "FROM PERSON_SKILL\n" +
                        "WHERE PER_ID=P.PER_ID)\n" +
                        "GROUP BY PER_ID\n" +
                        ")\n" +
                        "SELECT PER_ID, MISSING_AMOUNT\n" +
                        "FROM COUNT_NEEDED_SKILLS\n" +
                        "WHERE MISSING_AMOUNT = (\n" +
                        "SELECT MIN(MISSING_AMOUNT)\n" +
                        "FROM COUNT_NEEDED_SKILLS)");
                while ( rset.next() ) {
                    Integer perId = rset.getInt("per_id");
                    Integer missingSkills = rset.getInt("missing_amount");
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
                            "WITH COUNT_MISSING_SKILLS AS ( \n" +
                            "\n" +
                            "SELECT PER_ID, COUNT(KS_CODE) AS MISSING_AMOUNT \n" +
                            "FROM PERSON P NATURAL JOIN JOB_CATEGORY \n" +
                            "WHERE KS_CODE IN ( \n" +
                            "\n" +
                            "SELECT KS_CODE \n" +
                            "FROM JOB_CATEGORY \n" +
                            "WHERE CATE_CODE=11\n" +
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
                Integer perId = rset.getInt("per_id");
                Integer missingSkills = rset.getInt("missing_amount");
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
                     "WITH COUNT_MISSING_SKILLS(PER_ID, KS_CODE) AS (\n" +
                             "     SELECT DISTINCT PER_ID, KS_CODE\n" +
                             "     FROM PERSON, JOB_CATEGORY\n" +
                             "     WHERE CATE_CODE=11\n" +
                             "     MINUS\n" +
                             "     SELECT PER_ID, KS_CODE\n" +
                             "     FROM PERSON_SKILL\n" +
                             "),\n" +
                             "MISSING_K AS (\n" +
                             "    SELECT MIN(COUNT(KS_CODE))\n" +
                             "    FROM JOB_CATEGORY NATURAL JOIN COUNT_MISSING_SKILLS\n" +
                             "    WHERE CATE_CODE=11\n" +
                             "    GROUP BY PER_ID\n" +
                             ")\n" +
                             "SELECT KS_CODE, COUNT(PER_ID) AS SKILLS_COUNT\n" +
                             "FROM COUNT_MISSING_SKILLS CMS\n" +
                             "WHERE EXISTS(\n" +
                             "     SELECT PER_ID\n" +
                             "     FROM COUNT_MISSING_SKILLS\n" +
                             "     WHERE PER_ID=CMS.PER_ID\n" +
                             "     GROUP BY PER_ID\n" +
                             "     HAVING COUNT(KS_CODE) <=2\n" +
                             ")\n" +
                             "GROUP BY KS_CODE\n" +
                             "ORDER BY SKILLS_COUNT DESC");
            while ( rset.next() ) {
                Integer ksCode = rset.getInt("ks_code");
                Integer skillsCount = rset.getInt("skills_count");
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
                    "SELECT NAME \n" +
                            "FROM JOB_HISTORY INNER JOIN PERSON\n" +
                            "ON PERSON.PER_ID = JOB_HISTORY.PER_ID\n" +
                            "INNER JOIN JOB_LISTING \n" +
                            "ON JOB_HISTORY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "INNER JOIN JOB\n" +
                            "ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "INNER JOIN JOB_CATEGORY\n" +
                            "ON JOB.CATE_CODE = JOB_CATEGORY.CATE_CODE \n" +
                            "WHERE JOB_CATEGORY.TITLE = 'Computer User Support Specialists'");
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
                    "WITH UNEMPLOYED AS (\n" +
                            "  SELECT PER_ID, NAME FROM (\n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PERSON\n" +
                            "    MINUS \n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PAID_BY\n" +
                            "  ) NATURAL JOIN PERSON\n" +
                            ")\n" +
                            "\n" +
                            "SELECT NAME \n" +
                            "FROM UNEMPLOYED INNER JOIN JOB_HISTORY \n" +
                            "ON JOB_HISTORY.PER_ID = UNEMPLOYED.PER_ID\n" +
                            "INNER JOIN JOB_LISTING\n" +
                            "ON JOB_LISTING.LISTING_ID = JOB_HISTORY.LISTING_ID\n" +
                            "INNER JOIN JOB\n" +
                            "ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "INNER JOIN JOB_CATEGORY\n" +
                            "ON JOB.CATE_CODE = JOB_CATEGORY.CATE_CODE \n" +
                            "WHERE JOB_CATEGORY.TITLE = 'Computer User Support Specialists'");
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
                            "-- Gets number of employees for each company \n" +
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
                            "  (SELECT MAX(SUM_SAL) FROM COMP_PAYCHECKS) OR \n" +
                            "  NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM COMP_EMPLOYEE_COUNT)");
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
                            "-- Number of employees by sector \n" +
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
                    "WITH PREVIOUS_SAL AS (\n" +
                            "  -- get the previous salary or get the previous pay rate * hours\n" +
                            "  SELECT NAME, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS OLD_SAL\n" +
                            "  FROM PERSON \n" +
                            "  INNER JOIN JOB_HISTORY \n" +
                            "  ON PERSON.PER_ID = JOB_HISTORY.PER_ID\n" +
                            "  INNER JOIN JOB_LISTING\n" +
                            "  ON JOB_HISTORY.LISTING_ID = JOB_LISTING.LISTING_ID\n" +
                            "  INNER JOIN JOB\n" +
                            "  ON JOB.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  WHERE PRIMARY_SECTOR = 'Software Engineering'\n" +
                            "  GROUP BY NAME\n" +
                            "),\n" +
                            "\n" +
                            "-- gets the current salary of employes in the software engineering primary sector \n" +
                            "CURRENT_SAL AS (\n" +
                            "  -- Get the current salary or get the current pay rate * hours \n" +
                            "  SELECT NAME, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS NEW_SAL \n" +
                            "  FROM PERSON \n" +
                            "  INNER JOIN PAID_BY \n" +
                            "  ON PERSON.PER_ID = PAID_BY.PER_ID\n" +
                            "  INNER JOIN JOB_LISTING\n" +
                            "  ON JOB_LISTING.LISTING_ID = PAID_BY.LISTING_ID\n" +
                            "  INNER JOIN JOB\n" +
                            "  ON JOB_LISTING.JOB_CODE = JOB.JOB_CODE\n" +
                            "  INNER JOIN COMPANY\n" +
                            "  ON JOB_LISTING.COMP_ID = COMPANY.COMP_ID\n" +
                            "  WHERE PRIMARY_SECTOR = 'Software Engineering'\n" +
                            "  GROUP BY NAME\n" +
                            "),\n" +
                            "\n" +
                            "INCREASE AS (\n" +
                            "  SELECT PREVIOUS_SAL.NAME, NEW_SAL/OLD_SAL AS RATIO FROM PREVIOUS_SAL \n" +
                            "  INNER JOIN CURRENT_SAL\n" +
                            "  ON PREVIOUS_SAL.NAME = CURRENT_SAL.NAME\n" +
                            "  WHERE NEW_SAL/OLD_SAL > 1\n" +
                            ")\n" +
                            "\n" +
                            "SELECT AVG(RATIO) AS AVERAGE_INCREASE FROM INCREASE");
            while ( rset.next() ) {
                Float averageIncrease = rset.getFloat("average_increase");
                System.out.println("Average Increase: " + averageIncrease + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 25: " + e);
        }
    }

    public static void query26 () {
        System.out.println("\nQuery 26: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH OPENINGS AS (\n" +
                            "  SELECT JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS\n" +
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
                            "  GROUP BY JOB_CODE\n" +
                            "),\n" +
                            "\n" +
                            "-- people who do not have a job \n" +
                            "UNEMPLOYED AS (\n" +
                            "  SELECT PER_ID, NAME FROM (\n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PERSON\n" +
                            "    MINUS \n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PAID_BY \n" +
                            "  ) NATURAL JOIN PERSON\n" +
                            "),\n" +
                            "\n" +
                            "-- number of people qualified per job \n" +
                            "NUMB_QUALIFIED AS (\n" +
                            "  SELECT JOB_CODE, COUNT(PER_ID) AS NUMB_QUAL\n" +
                            "  FROM OPENINGS O, UNEMPLOYED U\n" +
                            "  WHERE NOT EXISTS (\n" +
                            "    SELECT KS_CODE\n" +
                            "    FROM JOB_SKILL\n" +
                            "    WHERE O.JOB_CODE = JOB_SKILL.JOB_CODE\n" +
                            "    \n" +
                            "    MINUS\n" +
                            "    \n" +
                            "    SELECT KS_CODE\n" +
                            "    FROM PERSON_SKILL\n" +
                            "    WHERE U.PER_ID = PERSON_SKILL.PER_ID\n" +
                            "  )\n" +
                            "  GROUP BY JOB_CODE\n" +
                            "),\n" +
                            "\n" +
                            "-- sum(vacancies - qualifeid) according to job category \n" +
                            "DIFFERENCES AS (\n" +
                            "  SELECT JOB_CODE, SUM(OPENINGS.NUMB_OPENINGS - NUMB_QUALIFIED.NUMB_QUAL) AS DIFF\n" +
                            "  FROM OPENINGS NATURAL JOIN NUMB_QUALIFIED \n" +
                            "  GROUP BY JOB_CODE\n" +
                            ")\n" +
                            "\n" +
                            "-- select job category with max difference\n" +
                            "SELECT CATE_CODE\n" +
                            "FROM DIFFERENCES NATURAL JOIN JOB\n" +
                            "WHERE DIFF = (SELECT MAX(DIFF) FROM DIFFERENCES)");
            while ( rset.next() ) {
                Integer cateCode = rset.getInt("cate_code");
                System.out.println("Job Category Code: " + cateCode + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 26: " + e);
        }
    }

    public static void query27 () {
        System.out.println("\nQuery 27: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH OPENINGS AS (\n" +
                            "  SELECT JOB_CODE, COUNT(JOB_CODE) AS NUMB_OPENINGS\n" +
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
                            "  GROUP BY JOB_CODE\n" +
                            "),\n" +
                            "\n" +
                            "-- people who do not have a job \n" +
                            "UNEMPLOYED AS (\n" +
                            "  SELECT PER_ID, NAME FROM (\n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PERSON\n" +
                            "    MINUS \n" +
                            "    SELECT PER_ID\n" +
                            "    FROM PAID_BY \n" +
                            "  ) NATURAL JOIN PERSON\n" +
                            "),\n" +
                            "\n" +
                            "-- number of people qualified per job \n" +
                            "NUMB_QUALIFIED AS (\n" +
                            "  SELECT JOB_CODE, COUNT(PER_ID) AS NUMB_QUAL\n" +
                            "  FROM OPENINGS O, UNEMPLOYED U\n" +
                            "  WHERE NOT EXISTS (\n" +
                            "    SELECT KS_CODE\n" +
                            "    FROM JOB_SKILL\n" +
                            "    WHERE O.JOB_CODE = JOB_SKILL.JOB_CODE\n" +
                            "    \n" +
                            "    MINUS\n" +
                            "    \n" +
                            "    SELECT KS_CODE\n" +
                            "    FROM PERSON_SKILL\n" +
                            "    WHERE U.PER_ID = PERSON_SKILL.PER_ID\n" +
                            "  )\n" +
                            "  GROUP BY JOB_CODE\n" +
                            "),\n" +
                            "\n" +
                            "-- sum(vacancies - qualified) according to job category \n" +
                            "DIFFERENCES AS (\n" +
                            "  SELECT JOB_CODE, SUM(OPENINGS.NUMB_OPENINGS - NUMB_QUALIFIED.NUMB_QUAL) AS DIFF\n" +
                            "  FROM OPENINGS NATURAL JOIN NUMB_QUALIFIED \n" +
                            "  GROUP BY JOB_CODE\n" +
                            "),\n" +
                            "\n" +
                            "-- courses and number of jobless people they help\n" +
                            "COURSES AS (\n" +
                            "  SELECT C.C_CODE, COUNT(DISTINCT U.PER_ID) NUMB_PPL_COURSE_QUALIFIES\n" +
                            "  FROM COURSE_KNOWLEDGE C NATURAL JOIN OPENINGS NATURAL JOIN JOB_SKILL, UNEMPLOYED U \n" +
                            "  WHERE NOT EXISTS (\n" +
                            "  \n" +
                            "    -- get all skills required by job category \n" +
                            "    SELECT JOB_SKILL.KS_CODE\n" +
                            "    FROM JOB_SKILL INNER JOIN JOB\n" +
                            "    ON JOB_SKILL.JOB_CODE = JOB.JOB_CODE\n" +
                            "    WHERE JOB.CATE_CODE = (\n" +
                            "      SELECT CATE_CODE\n" +
                            "      FROM DIFFERENCES NATURAL JOIN JOB\n" +
                            "      WHERE DIFF = (SELECT MAX(DIFF) FROM DIFFERENCES))\n" +
                            "    MINUS\n" +
                            "    -- get all skills an unemployed person has \n" +
                            "    SELECT PERSON_SKILL.KS_CODE  \n" +
                            "    FROM PERSON_SKILL\n" +
                            "    WHERE PERSON_SKILL.PER_ID = U.PER_ID\n" +
                            "    \n" +
                            "    MINUS\n" +
                            "    -- get all the skills a course offers \n" +
                            "    (SELECT KS_CODE \n" +
                            "    FROM COURSE_KNOWLEDGE\n" +
                            "    WHERE COURSE_KNOWLEDGE.C_CODE = C.C_CODE)\n" +
                            "  )\n" +
                            "  GROUP BY C.C_CODE\n" +
                            ")\n" +
                            "\n" +
                            "SELECT TITLE, C_CODE, NUMB_PPL_COURSE_QUALIFIES\n" +
                            "FROM COURSES NATURAL JOIN COURSE\n" +
                            "WHERE NUMB_PPL_COURSE_QUALIFIES = (SELECT MAX(NUMB_PPL_COURSE_QUALIFIES) FROM COURSES)");
            while ( rset.next() ) {
                String courseTitle = rset.getString("title");
                Integer numbPpl = rset.getInt("numb_ppl_course_qualifies");
                System.out.println("Course Title: " + courseTitle + "\n" + "Number of People Course Qualifies:" + numbPpl + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 27: " + e);
        }
    }
}

