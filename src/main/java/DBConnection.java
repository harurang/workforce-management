//Hillary Arurang
//Eresha Polite
//Spring 2017

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String dbLocation;
    final String oraThinProtocol = "jdbc:oracle:thin";
    //final String mySQLProtocol = "jdbc:mysql";
    //final String dbLocation = "@dbsvcs.cs.uno.edu:1521:cs4125";
    public DBConnection (String host, String port, String sID) {
        this.dbLocation = "@" + host + ":" + port + ":" + sID;
    }

    public Connection getDBConnection(String username, String password)
            throws SQLException {
        // register the JDBC driver.
        DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver( ) );
        // Create the connection
        String url = oraThinProtocol + ':' + dbLocation;
        System.out.println("[your class name:] url = " + url);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}