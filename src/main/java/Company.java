import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Company {

    ArrayList<Person> employees = new ArrayList<Person>();

    private Connection conn;
    private int compId;
    private String compName;
    private int nCode;
    private String city;
    private String state;
    private int zip;
    private String website;
    private String primarySector;

    public Company(int id, String name, int nCode, String city, String state, int zip, String website, String primarySector, Connection conn) {
        this.compId = id;
        this.compName = name;
        this.nCode = nCode;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.website = website;
        this.primarySector = primarySector;
        this.conn = conn;
    }

    /**
     * Gets qualified people according to the company and job.
     *
     * @param jobCode
     * @return qualified people
     */
    public ArrayList<Person> getQualifiedPeople(int jobCode) {
        ArrayList<Person> results = new ArrayList<Person>();

        String name = "";
        String email = "";

        try {
            PreparedStatement pStmt = conn.prepareStatement("SELECT DISTINCT NAME, EMAIL \n" +
                    "FROM PERSON NATURAL JOIN (\n" +
                    "  SELECT PER_ID\n" +
                    "  FROM PERSON_SKILL A\n" +
                    "  WHERE NOT EXISTS (\n" +
                    "  \n" +
                    "    -- get skills of specific job\n" +
                    "    SELECT JOB_SKILL.KS_CODE\n" +
                    "    FROM JOB_SKILL INNER JOIN JOB_LISTING\n" +
                    "    ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                    "    WHERE JOB_SKILL.JOB_CODE=? AND JOB_LISTING.COMP_ID=?\n" +
                    "    \n" +
                    "    MINUS\n" +
                    "    \n" +
                    "    -- get skills of person\n" +
                    "    (SELECT KS_CODE\n" +
                    "    FROM PERSON_SKILL B\n" +
                    "    WHERE A.PER_ID = B.PER_ID)\n" +
                    "  )\n" +
                    ")");

            pStmt.setString(1, jobCode + "");
            pStmt.setString(2, this.compId + "");
            ResultSet rset = pStmt.executeQuery();

            while(rset.next()){
                name = rset.getString(1);
                email = rset.getString(2);

                Person person = new Person(name, email);
                results.add(person);
            }

        } catch(Exception e) {
            System.out.println("\nError in method qualifiedPeople of Company: " + e);
        }

        return results;
    }

    /**
     * A company accepts a new employee.
     *
     * @param person
     * @param paidBy
     * @param takes
     * @param history
     * @param skills
     * @param phoneNumbers
     */
    public void addEmployee(Person person, ArrayList<Integer> paidBy, ArrayList<Integer> takes, ArrayList<JobHistory> history,
                                    ArrayList<Integer> skills, HashMap<String, String> phoneNumbers) {
        try{
            // insert person
            PreparedStatement pStmt = conn.prepareStatement("INSERT INTO person\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

            pStmt.setString(1, person.getPerId() + "");
            pStmt.setString(2, person.getName());
            pStmt.setString(3, person.getCity());
            pStmt.setString(4, person.getStreet());
            pStmt.setString(5, person.getState());
            pStmt.setString(6, person.getZipCode() + "");
            pStmt.setString(7, person.getEmail());
            pStmt.setString(8, person.getGender());
            ResultSet rset = pStmt.executeQuery();

             // insert phone numbers
             pStmt = conn.prepareStatement("INSERT INTO phone_number " +
                    "VALUES (?, ?, ?)");

            pStmt.setString(1, person.getPerId() + "");
            pStmt.setString(2, phoneNumbers.get("home"));
            pStmt.setString(3, phoneNumbers.get("mobile"));
            rset = pStmt.executeQuery();

            // insert skills
            for(Integer skill : skills) {
                pStmt = conn.prepareStatement("INSERT INTO person_skill VALUES (?, ?)");

                pStmt.setString(1, person.getPerId() + "");
                pStmt.setString(2, skill + "");
                rset = pStmt.executeQuery();
            }

            // insert job histories
            for(JobHistory job : history) {
                pStmt = conn.prepareStatement("INSERT INTO job_history VALUES (?, ?, ?, ?)");

                pStmt.setString(1, job.getStartDate());
                pStmt.setString(2, job.getEndDate());
                pStmt.setString(3, job.getJobListing() + "");
                pStmt.setString(4, person.getPerId() + "");
                rset = pStmt.executeQuery();
            }

            // insert sections taken
            for(Integer section : takes) {
                pStmt = conn.prepareStatement("INSERT INTO takes VALUES (?, ?)");

                pStmt.setString(1, person.getPerId() + "");
                pStmt.setString(2, section + "");
                rset = pStmt.executeQuery();
            }

            // add job a person is paid by
            for(Integer jobListing : paidBy) {
                pStmt = conn.prepareStatement("INSERT INTO paid_by VALUES (?, ?)");

                pStmt.setString(1, person.getPerId() + "");
                pStmt.setString(2, jobListing + "");
                rset = pStmt.executeQuery();
            }

            employees.add(person);
            System.out.println("\n" + person.getName() + " added successfully to company " + getCompName());

        } catch(Exception e){
            System.out.println("\nError in method addEmployee: " + e);
        }

    }

    public ArrayList<Person> getEmployees() { return employees; }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public int getnCode() {
        return nCode;
    }

    public void setnCode(int nCode) {
        this.nCode = nCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPrimarySector() {
        return primarySector;
    }

    public void setPrimarySector(String primarySector) {
        this.primarySector = primarySector;
    }
}
