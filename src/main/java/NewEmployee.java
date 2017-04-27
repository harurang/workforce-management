import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class NewEmployee {

    private Connection conn;

    public NewEmployee(Connection conn) {
          this.conn = conn;
    }

    public static  void createNewEmployee(){
         String item = "employee";

         try{
              Statement stmt = conn.createStatement();
              ResultSet rset;


              rset = stmt.executeQuery(
                  "INSERT INTO person\n" +
                    "VALUES(121, 'Xavier', 'Denver', '4635 East St.', 'CO', '80014', 'xavier@yahoo.com', 'male')");
              
              rset = stmt.executeQuery(
                   "INSERT INTO phone_number\n" +
                      "VALUES (121, '347-832-9036', '284-732-8304')");

              rset = stmt.executeQuery(
                    "INSERT INTO person_skill\n" +
                        "VALUES (121, 435786)");

              rset = stmt.executeQuery(
                     "INSERT INTO job_history\n" +
                        "VALUES ('03/24/2013', '09/14/2015', 3, 121)");

              rset = stmt.executeQuery(
                      "INSERT INTO takes\n" +
                         "VALUES (121, 327)");

              rset = stmt.executeQuery(
                     "INSERT INTO paid_by\n" +
                        "VALUES (121, 75)");

              System.out.println("\n A" + item + "has been created.\n");
        }catch(Exception e){
              System.out.println("\nError creating" + item + ": " + e);
        }

   }







}
