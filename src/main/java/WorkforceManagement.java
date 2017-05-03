import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkforceManagement {
    private static Connection conn;

    public static void main(String args[]) {
        DBConnection dbCon = new DBConnection("dbsvcs.cs.uno.edu", "1521", "orcl");

        try {
            conn = dbCon.getDBConnection("hmangini", "H94F4Phd");

            // Career Planning Service
            CareerPlanningService career =  new CareerPlanningService(conn);
            // get qualified people according to job only
            ArrayList<Person> qualifiedPpl = career.getQualifiedPeople(44);
            System.out.print("\nQualified people: \n");
            for(Person person : qualifiedPpl) {
                System.out.println(person.getName());
            }

            // Training Service
            TrainingService training = new TrainingService(conn);
            // get recommended course
            ArrayList<Course> neededCourse = training.getNeededCourse(91,5);
            System.out.println("\nRecommended course: ");
            for( Course course: neededCourse) {
                System.out.println(course.getTitle());
            }

            // a) Add a person to a company
            Company comp = instantiateCompany(12);
            Person newEmployee = new Person(2349, "Jim", "Escalon", "1311 Carolyn",
                    "CA", 95320, "jim@gmail.com", "male", conn);
            ArrayList<Integer> paidBy = new ArrayList<Integer>() {{
                add(12);
            }};
            ArrayList<Integer> takes = new ArrayList<Integer>() {{
                add(326);
                add(327);
            }};
            ArrayList<JobHistory> history = new ArrayList<JobHistory>() {{
                add(new JobHistory("06/12/2012", "09/12/2013", 14));
                add(new JobHistory("01/06/2012", "06/12/2012", 18));
            }};
            ArrayList<Integer> skills = new ArrayList<Integer>() {{
                add(435785);
                add(435788);
            }};
            HashMap<String, String> phoneNumbers = new HashMap<String, String>() {{
                put("home", "234-345-1324");
                put("mobile", "234-234-356");
            }};
            comp.addEmployee(newEmployee, paidBy, takes, history, skills, phoneNumbers);


	        Company comp2 = instantiateCompany(17);
            Person newEmployee2 = new Person(1231, "Joe", "Gainsville", "2342 Sugar",
            "LA", 95320, "joe@gmail.com", "male", conn);
            ArrayList<Integer> skills2 = new ArrayList<Integer>() {{
                add(435785);
            }};
            HashMap<String, String> phoneNumbers2 = new HashMap<String, String>() {{
                put("home", "251-966-1324");
                put("mobile", "111-234-356");
            }};
            comp.addEmployee(newEmployee2, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<JobHistory>(), skills2, phoneNumbers2);


            // b) a person's job hunting
            JobHunting hunt = new JobHunting(conn);
            hunt.searchMenuRunner(); 

            // c) Get qualifed people according to company and job
            ArrayList<Person> qualPplByCompanyJob = instantiateCompany(18).getQualifiedPeople(44);
            System.out.println("\nQualified people according to a specific job: ");
            for(Person person : qualPplByCompanyJob) {
                System.out.println(person.getName());
            }

            // d) Evaluate the opportunities in all the business sectors for the career planning service
            ArrayList<String> openings = career.getAllOpenings();
            System.out.println("\nOpportunities in all business sectors: ");
            for(String opening : openings) {
                System.out.println(opening);
            }

            DBReset reset = new DBReset(conn);
            reset.removeInsertFromTask8a(newEmployee, paidBy, takes, history, skills);
            reset.removeInsertFromTask8a(newEmployee2, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<JobHistory>(), skills2);

            conn.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static Company instantiateCompany (int compId) {
        String compName = "";
        int nCode = 0;
        String city = "";
        String state = "";
        int zip = 0;
        String website = "";
        String primarySector = "";

        try {
            PreparedStatement pStmt = conn.prepareStatement("select * from company where comp_id=?");

            pStmt.setString(1, compId + "");
            ResultSet rset = pStmt.executeQuery();

            while ( rset.next() ) {
                compName = rset.getString("comp_name");
                nCode = rset.getInt("n_code");
                city = rset.getString("city");
                state = rset.getString("state");
                zip = rset.getInt("zip");
                website = rset.getString("website");
                primarySector = rset.getString("primary_sector");
            }

        } catch(Exception e) {
            System.out.println("\nError at method instantiateCompany() in WorkforceManagement: " + e);
        }

        return new Company(compId, compName, nCode, city, state, zip, website, primarySector, conn);
    }
}
