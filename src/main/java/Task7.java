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

//            deleteCourse();
//            createCourse();
//
//            deleteJobCategory();
//            createJobCategory();
//
//            deletePerson();
//            createPerson();

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
                    "delete from (select * from job_history natural join job_listing)\n" +
                            "where job_code = 91");

            rset = stmt.executeQuery(
                    "delete " +
                            "from (select * from paid_by natural join job_listing) " +
                            "where job_code = 91");

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
                            "            VALUES(91, 435789, 'required')");

            rset = stmt.executeQuery(
                    "INSERT INTO job_skill\n" +
                            "            VALUES(91, 435783, 'preferred')");

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
                    "delete" +
                            "from course_knowledge where c_code = 3120");

            rset = stmt.executeQuery(
                    "delete" +
                            "from section where c_code = 3120");

            rset = stmt.executeQuery(
                    "delete" +
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
                    "INSERT INTO course_knowledge\n" +
                            "VALUES (435786, 3120)");

            System.out.println("\n" + item + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + item + ": " + e);
        }
    }

//    public static void deleteJobCategory () {
//        String item = "job_category";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rset;
//
//            rset = stmt.executeQuery(
//                    "delete from job where cate_code = 5");
//
//            rset = stmt.executeQuery(
//                    "delete from job_cateogory where cate_code = 5");
//
//            System.out.println("\n" + item + " has been deleted.\n");
//        } catch(Exception e) {
//            System.out.println("\nError deleting " + item + ": " + e);
//        }
//    }

//    public static void createJobCategory () {
//        String item = "job_category";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rset;
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job_category (\n" +
//                            "  cate_code number,\n" +
//                            "  parent_cate number,\n" +
//                            "  pay_range_high number,\n" +
//                            "  pay_range_low number,\n" +
//                            "  title varchar(70),\n" +
//                            "  ks_code number,\n" +
//                            "  primary key (cate_code),\n" +
//                            "  foreign key (parent_cate) references job_category(cate_code),\n" +
//                            "  foreign key (title) references soc(soc_title),\n" +
//                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
//                            ")");
//
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job (\n" +
//                            "  job_code number,\n" +
//                            "  pay_rate number,\n" +
//                            "  pay_type varchar(20),\n" +
//                            "  hours number,\n" +
//                            "  cate_code number,\n" +
//                            "  job_title varchar(70),\n" +
//                            "  primary key (job_code),\n" +
//                            "  foreign key (cate_code) references job_category(cate_code)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job_skill (\n" +
//                            "  job_code number,\n" +
//                            "  ks_code number,\n" +
//                            "  importance varchar(30),\n" +
//                            "  foreign key (job_code) references job(job_code),\n" +
//                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job_listing (\n" +
//                            "    listing_id number,\n" +
//                            "    comp_id number,\n" +
//                            "    job_code number,\n" +
//                            "    primary key (listing_id),\n" +
//                            "    foreign key (comp_id) references company(comp_id),\n" +
//                            "    foreign key (job_code) references job(job_code)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE paid_by(\n" +
//                            "  per_id number,\n" +
//                            "  listing_id number,\n" +
//                            "  foreign key (per_id) references person(per_id),\n" +
//                            "  foreign key (listing_id) references job_listing(listing_id),\n" +
//                            "  unique (listing_id)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job_history (\n" +
//                            "  start_date varchar(50),\n" +
//                            "  end_date varchar(50),\n" +
//                            "  listing_id number,\n" +
//                            "  per_id number,\n" +
//                            "  foreign key (per_id) references person(per_id),\n" +
//                            "  foreign key (listing_id) references job_listing(listing_id)\n" +
//                            ")");
//
//            System.out.println("\n" + item + " has been created.\n");
//        } catch(Exception e) {
//            System.out.println("\nError creating " + item + ": " + e);
//        }
//    }
//
//    public static void deletePerson () {
//        String item = "person";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rset;
//
//            rset = stmt.executeQuery(
//                    "drop item job_history");
//
//            rset = stmt.executeQuery(
//                    "drop item paid_by");
//
//            rset = stmt.executeQuery(
//                    "drop item phone_number");
//
//            rset = stmt.executeQuery(
//                    "drop item person_skill");
//
//            rset = stmt.executeQuery(
//                    "drop item takes");
//
//            rset = stmt.executeQuery(
//                    "drop item person");
//
//            System.out.println("\n" + item + " has been deleted.\n");
//        } catch(Exception e) {
//            System.out.println("\nError deleting " + item + ": " + e);
//        }
//    }
//
//    public static void createPerson () {
//        String item = "person";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rset;
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE person (\n" +
//                            "  per_id number,\n" +
//                            "  name varchar(30),\n" +
//                            "  city varchar(30),\n" +
//                            "  street varchar(30),\n" +
//                            "  state varchar(2),\n" +
//                            "  zip_code varchar(10),\n" +
//                            "  email varchar(30),\n" +
//                            "  gender varchar(30),\n" +
//                            "  primary key (per_id)\n" +
//                            ")");
//
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE takes (\n" +
//                            "  per_id number,\n" +
//                            "  sec_id number,\n" +
//                            "  foreign key (sec_id) references section(sec_id),\n" +
//                            "  foreign key (per_id) references person(per_id)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE person_skill (\n" +
//                            "  per_id number,\n" +
//                            "  ks_code number,\n" +
//                            "  foreign key (per_id) references person(per_id),\n" +
//                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE phone_number (\n" +
//                            "  per_id number,\n" +
//                            "  home varchar(30),\n" +
//                            "  work varchar(30),\n" +
//                            "  foreign key (per_id) references person(per_id)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE paid_by(\n" +
//                            "  per_id number,\n" +
//                            "  listing_id number,\n" +
//                            "  foreign key (per_id) references person(per_id),\n" +
//                            "  foreign key (listing_id) references job_listing(listing_id),\n" +
//                            "  unique (listing_id)\n" +
//                            ")");
//
//            rset = stmt.executeQuery(
//                    "CREATE TABLE job_history (\n" +
//                            "  start_date varchar(50),\n" +
//                            "  end_date varchar(50),\n" +
//                            "  listing_id number,\n" +
//                            "  per_id number,\n" +
//                            "  foreign key (per_id) references person(per_id),\n" +
//                            "  foreign key (listing_id) references job_listing(listing_id)\n" +
//                            ")");
//
//            System.out.println("\n" + item + " has been created.\n");
//        } catch(Exception e) {
//            System.out.println("\nError creating " + item + ": " + e);
//        }
//    }
}