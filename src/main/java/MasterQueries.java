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
            query15();
            query16();
            query17();
            query18();
            query23();
            query24();

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
                    "SELECT NAME \n" +
                            "FROM PERSON NATURAL JOIN PAID_BY NATURAL JOIN JOB \n" +
                            "WHERE JOB.COMP_ID=14");
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
                    "SELECT PAY_RATE, NAME \n" +
                            "FROM PERSON NATURAL JOIN PAID_BY NATURAL JOIN JOB \n" +
                            "WHERE JOB.COMP_ID=14" +
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
                    "SELECT COMP_ID, SUM(PAY_RATE) AS TOTAL_RATE\n" +
                            "FROM JOB NATURAL JOIN COMPANY NATURAL JOIN PAID_BY\n" +
                            "GROUP BY COMP_ID\n" +
                            "ORDER BY TOTAL_RATE DESC");
            while ( rset.next() ) {
                Integer compId = rset.getInt("comp_id");
                Integer payRate = rset.getInt("total_rate");
                System.out.println("Company Id: " + compId + "\n" + "Total Rate: " + payRate + "\n");
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
                    "SELECT DISTINCT NAME, EMAIL, JOB_TITLE \n" +
                            "FROM PERSON NATURAL JOIN PERSON_SKILL  NATURAL JOIN JOB");
            while ( rset.next() ) {
                String name = rset.getString("name");
                String email = rset.getString("email");
                String jobTitle = rset.getString("job_title");
                System.out.println("Name: " + name + "\n" + "Email:" + email + "\n" + "Job Title:" + jobTitle + "\n");
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
                            "FROM PERSON NATURAL JOIN JOB\n" +
                            "WHERE JOB_CODE=67\n" +
                            "MINUS\n" +
                            "SELECT NAME\n" +
                            "FROM PERSON NATURAL JOIN PERSON_SKILL\n" +
                            "WHERE PERSON_SKILL.KS_CODE=435785");
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
                            "      FROM PERSON NATURAL JOIN JOB\n" +
                            "      WHERE JOB_CODE=67\n" +
                            "      MINUS\n" +
                            "      SELECT NAME\n" +
                            "      FROM PERSON NATURAL JOIN PERSON_SKILL\n" +
                            "      WHERE PERSON_SKILL.KS_CODE=435785), PERSON_SKILL, JOB\n" +
                            "WHERE JOB_CODE=91\n" +
                            "GROUP BY PERSON_SKILL.KS_CODE\n" +
                            "ORDER BY TOTAL_COUNT ASC");
            while ( rset.next() ) {
                Integer perCode = rset.getInt("ks_code");
                Integer totalCount = rset.getInt("total_count");
                System.out.println("Knowledge Code:" + perCode + "\n" + "Total Count: " + totalCount + "\n");
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
                    "SELECT NAME, COUNT(KNOWLEDGE_SKILL.KS_CODE) AS NUMB_SKILLS \n" +
                            "FROM PERSON NATURAL JOIN KNOWLEDGE_SKILL \n" +
                            "WHERE KNOWLEDGE_SKILL.KS_CODE=435786 \n" +
                            "GROUP BY NAME \n" +
                            "MINUS \n" +
                            "SELECT NAME, PERSON_SKILL.KS_CODE \n" +
                            "FROM PERSON NATURAL JOIN PERSON_SKILL \n" +
                            "WHERE PERSON_SKILL.KS_CODE=435786");
            while ( rset.next() ) {
                String name = rset.getString("name");
                Integer numbSkills = rset.getInt("numb_skills");
                System.out.println("Name: " + name + "\n" + "Number of Skills:" + numbSkills + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 18: " + e);
        }
    }

    public static void query23 () {
        System.out.println("\nQuery 23: \n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rset = stmt.executeQuery(
                    "WITH COMP_PAYCHECKS AS (\n" +
                            "  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, COMP_NAME FROM PERSON LEFT JOIN PAID_BY NATURAL JOIN JOB NATURAL JOIN COMPANY\n" +
                            "  ON PERSON.PER_ID = PAID_BY.PER_ID GROUP BY COMP_NAME\n" +
                            "),\n" +
                            "\n" +
                            "COMP_EMPLOYEE_COUNT AS \n" +
                            "(SELECT COMP_NAME, COUNT(*) AS NUMB_EMPLOYEES FROM \n" +
                            "  PERSON LEFT JOIN PAID_BY NATURAL JOIN JOB NATURAL JOIN COMPANY\n" +
                            "  ON PERSON.PER_ID = PAID_BY.PER_ID GROUP BY COMP_NAME)\n" +
                            "\n" +
                            "SELECT COMP_NAME, SUM_SAL, NUMB_EMPLOYEES FROM COMP_PAYCHECKS NATURAL JOIN COMP_EMPLOYEE_COUNT\n" +
                            "WHERE SUM_SAL = \n" +
                            "(SELECT MAX(SUM_SAL) FROM COMP_PAYCHECKS) OR \n" +
                            "NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM COMP_EMPLOYEE_COUNT)\n");
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
                    "WITH JOB_COUNT AS (\n" +
                            "  SELECT COUNT(*) AS NUMB_JOBS, JOB_TITLE\n" +
                            "  FROM COMP_JOB LEFT JOIN JOB\n" +
                            "  ON COMP_JOB.JOB_CODE = JOB.JOB_CODE GROUP BY JOB_TITLE\n" +
                            "),\n" +
                            "\n" +
                            "JOB_DISTRIBUTION AS (\n" +
                            "  SELECT CLUSTER_TITLE, JOB_COUNT.JOB_TITLE, JOB_COUNT.NUMB_JOBS\n" +
                            "  FROM JOB_COUNT LEFT JOIN JOB \n" +
                            "  ON JOB_COUNT.JOB_TITLE = JOB.JOB_TITLE NATURAL JOIN JOB_SKILL NATURAL JOIN KNOWLEDGE_SKILL\n" +
                            "),\n" +
                            "\n" +
                            "SECTOR_PAYCHECKS AS (\n" +
                            "  SELECT SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS SUM_SAL, CLUSTER_TITLE \n" +
                            "  FROM PAID_BY LEFT JOIN JOB \n" +
                            "  ON PAID_BY.JOB_CODE = JOB.JOB_CODE \n" +
                            "  NATURAL JOIN JOB_DISTRIBUTION\n" +
                            "  GROUP BY CLUSTER_TITLE\n" +
                            "),\n" +
                            "\n" +
                            "SECTOR_EMPLOYEE_COUNT AS \n" +
                            "(SELECT CLUSTER_TITLE, COUNT(*) AS NUMB_EMPLOYEES \n" +
                            "  FROM PAID_BY LEFT JOIN JOB \n" +
                            "  ON PAID_BY.JOB_CODE = JOB.JOB_CODE \n" +
                            "  NATURAL JOIN JOB_DISTRIBUTION\n" +
                            "  GROUP BY CLUSTER_TITLE)\n" +
                            "\n" +
                            "SELECT CLUSTER_TITLE, SUM_SAL, NUMB_EMPLOYEES FROM SECTOR_PAYCHECKS NATURAL JOIN SECTOR_EMPLOYEE_COUNT\n" +
                            "WHERE SUM_SAL = \n" +
                            "(SELECT MAX(SUM_SAL) FROM SECTOR_PAYCHECKS) OR \n" +
                            "NUMB_EMPLOYEES = (SELECT MAX(NUMB_EMPLOYEES) FROM SECTOR_EMPLOYEE_COUNT)\n");
            while ( rset.next() ) {
                String clusterTitle = rset.getString("cluster_title");
                Integer numbEmployees = rset.getInt("numb_employees");
                Integer sumSal = rset.getInt("sum_sal");
                System.out.println("Cluster Title: " + clusterTitle + "\n" + "Number of Employees: " + numbEmployees + "\n"+
                        "Sum of Paychecks: " + sumSal + "\n");
            }
        } catch(Exception e) {
            System.out.println("\nError at query 24: " + e);
        }
    }
}
