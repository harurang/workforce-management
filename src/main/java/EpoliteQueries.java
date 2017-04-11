import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EpoliteQueries {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("epolite", "wjXXw4t7");

            query1();
            query2();
            query3();
            query4();
            query15();
            query16();
            query17();
            query18();

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
                    "SELECT NAME\n" +
                            "FROM PERSON NATURAL JOIN PAID_BY NATURAL JOIN COMP_JOB\n" +
                            "WHERE COMP_ID=19");
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
                    "SELECT NAME, PAY_RATE\n" +
                            "FROM PERSON INNER JOIN PAID_BY\n" +
                            "ON PERSON.PER_ID = PAID_BY.PER_ID\n" +
                            "INNER JOIN JOB\n" +
                            "ON PAID_BY.JOB_CODE = JOB.JOB_CODE\n" +
                            "INNER JOIN COMP_JOB\n" +
                            "ON PAID_BY.JOB_CODE = COMP_JOB.JOB_CODE\n" +
                            "WHERE COMP_ID = 19\n" +
                            "AND PAY_TYPE = 'salary'\n" +
                            "ORDER BY PAY_RATE DESC;");
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
                    "SELECT COMP_ID, SUM(NVL(PAY_RATE,0) + NVL(HOURS * PAY_RATE, 0)) AS TOTAL_SAL\n" +
                            "FROM PAID_BY INNER JOIN COMP_JOB\n" +
                            "ON PAID_BY.JOB_CODE = COMP_JOB.JOB_CODE\n" +
                            "INNER JOIN JOB\n" +
                            "ON JOB.JOB_CODE = COMP_JOB.JOB_CODE\n" +
                            "GROUP BY COMP_ID\n" +
                            "ORDER BY TOTAL_SAL DESC");
            while ( rset.next() ) {
                Integer compId = rset.getInt("comp_id");
                Integer totalSal = rset.getInt("total_sal");
                System.out.println("Company Id: " + compId + "\n" + "Total Paycheck: " + totalSal + "\n");
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




}

