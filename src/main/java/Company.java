import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Company {

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
                    "FROM PERSON A INNER JOIN PERSON_SKILL B\n" +
                    "ON A.PER_ID = B.PER_ID \n" +
                    "WHERE NOT EXISTS (\n" +
                    "\n" +
                    "  -- get skills of specific job\n" +
                    "  SELECT JOB_SKILL.KS_CODE\n" +
                    "  FROM JOB_SKILL INNER JOIN JOB_LISTING\n" +
                    "  ON JOB_SKILL.JOB_CODE = JOB_LISTING.JOB_CODE\n" +
                    "  WHERE JOB_LISTING.JOB_CODE=? AND JOB_LISTING.COMP_ID = ?\n" +
                    "  \n" +
                    "  MINUS\n" +
                    "  \n" +
                    "  -- get skills of person\n" +
                    "  (SELECT KS_CODE\n" +
                    "  FROM PERSON C INNER JOIN PERSON_SKILL D \n" +
                    "  ON C.PER_ID = D.PER_ID\n" +
                    "  WHERE A.NAME = C.NAME)\n" +
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
            System.out.println("\nError in method qualifiedPeople: " + e);
        }

        return results;
    }

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
