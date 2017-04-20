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
        String table = "job";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "drop table job_history");

            rset = stmt.executeQuery(
                    "drop table job_skill");

            rset = stmt.executeQuery(
                    "drop table paid_by");

            rset = stmt.executeQuery(
                    "drop table job_listing");

            rset = stmt.executeQuery(
                    "drop table job");

            System.out.println("\n" + table + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + table + ": " + e);
        }
    }

    public static void createJob () {
        String table = "job";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "CREATE TABLE job (\n" +
                            "  job_code number,\n" +
                            "  pay_rate number,\n" +
                            "  pay_type varchar(20),\n" +
                            "  hours number,\n" +
                            "  cate_code number,\n" +
                            "  job_title varchar(70),\n" +
                            "  primary key (job_code),\n" +
                            "  foreign key (cate_code) references job_category(cate_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_listing (\n" +
                            "    listing_id number,\n" +
                            "    comp_id number,\n" +
                            "    job_code number,\n" +
                            "    primary key (listing_id),\n" +
                            "    foreign key (comp_id) references company(comp_id),\n" +
                            "    foreign key (job_code) references job(job_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_history (\n" +
                            "  start_date varchar(50),\n" +
                            "  end_date varchar(50),\n" +
                            "  listing_id number,\n" +
                            "  per_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_skill (\n" +
                            "  job_code number,\n" +
                            "  ks_code number,\n" +
                            "  importance varchar(30),\n" +
                            "  foreign key (job_code) references job(job_code),\n" +
                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE paid_by(\n" +
                            "  per_id number,\n" +
                            "  listing_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id),\n" +
                            "  unique (listing_id)\n" +
                            ")");

            System.out.println("\n" + table + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + table + ": " + e);
        }
    }

    public static void deleteCourse () {
        String table = "course";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "drop table takes");

            rset = stmt.executeQuery(
                    "drop table section");

            rset = stmt.executeQuery(
                    "drop table course_knowledge");

            rset = stmt.executeQuery(
                    "drop table course");

            System.out.println("\n" + table + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + table + ": " + e);
        }
    }

    public static void createCourse () {
        String table = "course";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "CREATE TABLE course(\n" +
                            "    c_code number,\n" +
                            "    title varchar(70),\n" +
                            "    course_level varchar(70),\n" +
                            "    description varchar(200),\n" +
                            "    status varchar(70),\n" +
                            "    prereq number,\n" +
                            "    primary key (c_code),\n" +
                            "    foreign key (prereq) references course(c_code)\n" +
                            ")");


            rset = stmt.executeQuery(
                    "CREATE TABLE course_knowledge(\n" +
                            "   ks_code number,\n" +
                            "   c_code number,\n" +
                            "   foreign key (ks_code) references knowledge_skill(ks_code),\n" +
                            "   foreign key (c_code) references course(c_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE section (\n" +
                            "  sec_id number,\n" +
                            "  start_date varchar(50),\n" +
                            "  end_date varchar(50),\n" +
                            "  format varchar(40),\n" +
                            "  offered_by varchar(40),\n" +
                            "  c_code number,\n" +
                            "  price number,\n" +
                            "  primary key (sec_id),\n" +
                            "  foreign key (c_code) references course(c_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE takes (\n" +
                            "  per_id number,\n" +
                            "  sec_id number,\n" +
                            "  foreign key (sec_id) references section(sec_id),\n" +
                            "  foreign key (per_id) references person(per_id)\n" +
                            ")");

            System.out.println("\n" + table + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + table + ": " + e);
        }
    }

    public static void deleteJobCategory () {
        String table = "job_category";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "drop table job_history");

            rset = stmt.executeQuery(
                    "drop table paid_by");

            rset = stmt.executeQuery(
                    "drop table job_listing");

            rset = stmt.executeQuery(
                    "drop table job_skill");

            rset = stmt.executeQuery(
                    "drop table job");

            rset = stmt.executeQuery(
                    "drop table job_category");

            System.out.println("\n" + table + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + table + ": " + e);
        }
    }

    public static void createJobCategory () {
        String table = "job_category";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "CREATE TABLE job_category (\n" +
                            "  cate_code number,\n" +
                            "  parent_cate number,\n" +
                            "  pay_range_high number,\n" +
                            "  pay_range_low number,\n" +
                            "  title varchar(70),\n" +
                            "  ks_code number,\n" +
                            "  primary key (cate_code),\n" +
                            "  foreign key (parent_cate) references job_category(cate_code),\n" +
                            "  foreign key (title) references soc(soc_title),\n" +
                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
                            ")");


            rset = stmt.executeQuery(
                    "CREATE TABLE job (\n" +
                            "  job_code number,\n" +
                            "  pay_rate number,\n" +
                            "  pay_type varchar(20),\n" +
                            "  hours number,\n" +
                            "  cate_code number,\n" +
                            "  job_title varchar(70),\n" +
                            "  primary key (job_code),\n" +
                            "  foreign key (cate_code) references job_category(cate_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_skill (\n" +
                            "  job_code number,\n" +
                            "  ks_code number,\n" +
                            "  importance varchar(30),\n" +
                            "  foreign key (job_code) references job(job_code),\n" +
                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_listing (\n" +
                            "    listing_id number,\n" +
                            "    comp_id number,\n" +
                            "    job_code number,\n" +
                            "    primary key (listing_id),\n" +
                            "    foreign key (comp_id) references company(comp_id),\n" +
                            "    foreign key (job_code) references job(job_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE paid_by(\n" +
                            "  per_id number,\n" +
                            "  listing_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id),\n" +
                            "  unique (listing_id)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_history (\n" +
                            "  start_date varchar(50),\n" +
                            "  end_date varchar(50),\n" +
                            "  listing_id number,\n" +
                            "  per_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id)\n" +
                            ")");

            System.out.println("\n" + table + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + table + ": " + e);
        }
    }

    public static void deletePerson () {
        String table = "person";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "drop table job_history");

            rset = stmt.executeQuery(
                    "drop table paid_by");

            rset = stmt.executeQuery(
                    "drop table phone_number");

            rset = stmt.executeQuery(
                    "drop table person_skill");

            rset = stmt.executeQuery(
                    "drop table takes");

            rset = stmt.executeQuery(
                    "drop table person");

            System.out.println("\n" + table + " has been deleted.\n");
        } catch(Exception e) {
            System.out.println("\nError deleting " + table + ": " + e);
        }
    }

    public static void createPerson () {
        String table = "person";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset;

            rset = stmt.executeQuery(
                    "CREATE TABLE person (\n" +
                            "  per_id number,\n" +
                            "  name varchar(30),\n" +
                            "  city varchar(30),\n" +
                            "  street varchar(30),\n" +
                            "  state varchar(2),\n" +
                            "  zip_code varchar(10),\n" +
                            "  email varchar(30),\n" +
                            "  gender varchar(30),\n" +
                            "  primary key (per_id)\n" +
                            ")");


            rset = stmt.executeQuery(
                    "CREATE TABLE takes (\n" +
                            "  per_id number,\n" +
                            "  sec_id number,\n" +
                            "  foreign key (sec_id) references section(sec_id),\n" +
                            "  foreign key (per_id) references person(per_id)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE person_skill (\n" +
                            "  per_id number,\n" +
                            "  ks_code number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (ks_code) references knowledge_skill(ks_code)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE phone_number (\n" +
                            "  per_id number,\n" +
                            "  home varchar(30),\n" +
                            "  work varchar(30),\n" +
                            "  foreign key (per_id) references person(per_id)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE paid_by(\n" +
                            "  per_id number,\n" +
                            "  listing_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id),\n" +
                            "  unique (listing_id)\n" +
                            ")");

            rset = stmt.executeQuery(
                    "CREATE TABLE job_history (\n" +
                            "  start_date varchar(50),\n" +
                            "  end_date varchar(50),\n" +
                            "  listing_id number,\n" +
                            "  per_id number,\n" +
                            "  foreign key (per_id) references person(per_id),\n" +
                            "  foreign key (listing_id) references job_listing(listing_id)\n" +
                            ")");

            System.out.println("\n" + table + " has been created.\n");
        } catch(Exception e) {
            System.out.println("\nError creating " + table + ": " + e);
        }
    }
}