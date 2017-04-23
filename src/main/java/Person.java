import java.sql.Connection;

public class Person {
    private int perId;
    private String name;
    private String city;
    private String street;
    private String state;
    private int zipCode;
    String email;
    String gender;
    Connection conn;

    public Person (int perId, String name, String city, String street, String state, int zipCode, String email, String gender, Connection conn) {
        this.perId = perId;
        this.name = name;
        this.city = city;
        this.street = street;
        this.state = state;
        this.zipCode = zipCode;
        this.email = email;
        this.gender = gender;
        this.conn = conn;
    }

    public Person (String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getPerId() {
        return perId;
    }

    public void setPerId(int perId) {
        this.perId = perId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
