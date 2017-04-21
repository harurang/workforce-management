import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Task7 {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");
        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            deleteJob();
            createJob();

            deleteCourse();
            createCourse();

            deleteJobCategory();
            createJobCategory();

            deletePerson();
            createPerson();

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void deleteJob () {
        String item = "job";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "delete from job_history " +
                            "where listing_id in " +
                            "(select listing_id from job_history natural join job_listing where job_code = 91)");

            rset = stmt.executeQuery(
                    "delete " +
                            "from paid_by where listing_id in " +
                            "(select listing_id from paid_by natural join job_listing where job_code = 91)");

            rset = stmt.executeQuery(
                    "delete " +
                            "from job_listing " +
                            "where job_code = 91");

            rset = stmt.executeQuery(
                    "delete " +
                            "from job_skill " +
                            "where job_code = 91");

            rset = stmt.executeQuery(
                    "delete " +
                            "from job " +
                            "where job_code = 91");

            System.out.println("\nA " + item + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + item + ": " + e);
        }
    }

    public static void createJob () {
        String item = "job";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "INSERT INTO job\n" +
                            "VALUES(91, 62000, 'salary', null, 6,'Front End Web Developer')");

            // revert DB to original properties
            rset = stmt.executeQuery(
                    " INSERT INTO job_skill\n" +
                            "VALUES(91, 435789, 'required')");

            rset = stmt.executeQuery(
                    "INSERT INTO job_skill\n" +
                            "VALUES(91, 435783, 'preferred')");

            rset = stmt.executeQuery(
                    "INSERT INTO job_listing\n" +
                            "VALUES(10, 20, 91)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_listing\n" +
                            "VALUES(9, 19, 91)");

            rset = stmt.executeQuery(
                    "INSERT INTO paid_by\n" +
                            "VALUES (4, 10)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_history\n" +
                            "VALUES ('02/24/2013', '02/27/2015', 9, 5)");

            System.out.println("\nA " + item + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + item + ": " + e);
        }
    }

    public static void deleteCourse () {
        String item = "course";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "delete " +
                            "from course_knowledge where c_code = 3120");

            rset = stmt.executeQuery(
                    "delete " +
                            "from takes where sec_id in (select sec_id from section where c_code = 3120)");

            rset = stmt.executeQuery(
                    "delete " +
                            "from section where c_code = 3120");

            rset = stmt.executeQuery(
                    "delete " +
                            "from course where c_code = 3120");

            System.out.println("\nA " + item + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + item + ": " + e);
        }
    }

    public static void createCourse () {
        String item = "course";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "INSERT INTO course\n" +
                            "VALUES (3120, 'Introduction to Networking', 3000, 'Learn about the depths of networking and how\n" +
                            "computers depend on the network, learn terminolgy and the basics', 'Closed', null)");

            // revert DB to original properties
            rset = stmt.executeQuery(
                    "INSERT INTO section\n" +
                            "VALUES (326, TO_DATE('2015/08/12','YYYY/MM/DD'), TO_DATE('2015/12/08','YYYY/MM/DD'), 'classroom', 'University', 3120, 300)");

            rset = stmt.executeQuery(
                    "INSERT INTO takes\n" +
                            "VALUES (100, 326)");

            rset = stmt.executeQuery(
                    "INSERT INTO course_knowledge\n" +
                            "VALUES (435786, 3120)");

            System.out.println("\n" + item + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + item + ": " + e);
        }
    }

    public static void deleteJobCategory () {
        String item = "job_category";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "delete from job_history \n" +
                            "where listing_id in (select listing_id from job_history natural join job_listing natural join job where cate_code = 4)");

            rset = stmt.executeQuery(
                    "delete from paid_by \n" +
                            "where listing_id in (select listing_id from paid_by natural join job_listing natural join job where cate_code = 4)");

            rset = stmt.executeQuery(
                    "delete " +
                            "from job_listing where job_code in (select job_code from job_listing natural join job where cate_code = 4)");

            rset = stmt.executeQuery(
                    "delete from job_skill where job_code in (select job_code from job where cate_code = 4)");

            rset = stmt.executeQuery(
                    "delete from job where cate_code = 4");

            rset = stmt.executeQuery(
                    "delete from job_category where cate_code = 4");

            System.out.println("\n" + item + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + item + ": " + e);
        }
    }

    public static void createJobCategory () {
        String item = "job_category";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "INSERT INTO job_category \n" +
                            "VALUES (4, 3, 50000, 45000, 'Computer User Support Specialists', 435787)");

            // revert DB to original properties
            rset = stmt.executeQuery(
                    "INSERT INTO job\n" +
                            "VALUES(73, 15, 'hourly', 2080 , 4, 'Geek Squad Agent')");

            rset = stmt.executeQuery(
                    "INSERT INTO job_skill\n" +
                            "VALUES(73, 435787, 'required')");

            rset = stmt.executeQuery(
                    "INSERT INTO job_listing\n" +
                            "VALUES(6, 15, 73)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_listing\n" +
                            "VALUES (16, 15, 73)");

            rset = stmt.executeQuery(
                    "INSERT INTO paid_by \n" +
                            "VALUES (105, 6)");

            rset = stmt.executeQuery(
                    "INSERT INTO paid_by\n" +
                            "VALUES (105, 16)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_history\n" +
                            "VALUES ('02/15/2013', '04/23/2015', 6, 207)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_history\n" +
                            "VALUES ('05/14/2013', '08/04/2015', 6, 206)");

            System.out.println("\nA " + item + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + item + ": " + e);
        }
    }

    public static void deletePerson () {
        String item = "person";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "delete from paid_by where per_id = 2");

            rset = stmt.executeQuery(
                    "delete from job_history where per_id = 2");

            rset = stmt.executeQuery(
                    "delete from phone_number where per_id = 2");

            rset = stmt.executeQuery(
                    "delete from person_skill where per_id = 2");

            rset = stmt.executeQuery(
                    "delete from takes where per_id = 2");

            rset = stmt.executeQuery(
                    "delete from person where per_id = 2");

            System.out.println("\nA " + item + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + item + ": " + e);
        }
    }

    public static void createPerson () {
        String item = "person";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "INSERT INTO person\n" +
                            "VALUES (2, 'Jane', 'Metairie', '3325 Causway', 'LA', 70001, 'jane@yahoo.com', 'female')");

            // revert DB to original properties
            rset = stmt.executeQuery(
                    "INSERT INTO phone_number\n" +
                            "VALUES (2, '702-324-2342', '124-854-2342')");

            rset = stmt.executeQuery(
                    "INSERT INTO person_skill\n" +
                            "VALUES(2, 435789)");

            rset = stmt.executeQuery(
                    "INSERT INTO job_history\n" +
                            "VALUES ('05/10/2013', '01/23/2015', 5, 2)");

            rset = stmt.executeQuery(
                    "INSERT INTO takes\n" +
                            "VALUES (2, 321)");

            rset = stmt.executeQuery(
                    "INSERT INTO paid_by\n" +
                            "VALUES(2, 11)");

            System.out.println("\nA " + item + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + item + ": " + e);
        }
    }
}