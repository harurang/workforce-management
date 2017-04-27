import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class PersonJobHunting{

        private Connection conn;


        public PersonJobHunting(Connection conn){
              this.conn = conn;
        }


        public ArrayList<String> getPersonSkills(int perId){
                 ArrayList<String> results = new ArrayList<String>();

                 String name = "";
                 int ksCode = 0;

                 try{
                     PreparedStatement pStmt = conn.prepareStatement(
                             "SELECT PERSON.NAME, KNOWLEDGE_SKILL.KS_CODE\n" +
                             "FROM PERSON INNER JOIN PERSON_SKILL\n" +
                             "ON PERSON.PER_ID=PERSON_SKILL.PER_ID\n" +
                             "INNER JOIN KNOWLEDGE_SKILL\n" +
                             "ON PERSON_SKILL.KS_CODE = KNOWLEDGE_SKILL.KS_CODE\n" +
                             "WHERE PERSON.PER_ID=?\n");



                   pStmt.setString(1, perId + "");
                   ResultSet rset = pStmt.executeQuery();
              
                   while(rset.next()){
                        name = rset.getString(1);
                        ksCode = rset.getInt(2);

                        String personSkills = "\nName: " + name + "\nPerson Skill: " + ksCode + "\n";
                        results.add(personSkills);
                   }

                 }catch(Exception e){
                     System.out.println("\nError in method getPersonSkills in PersonJobHunting: " + e);
                 }

                 return results;
        }


        public ArrayList<String> getJobSkills(int jobCode, String importance){
                ArrayList<String> results = new ArrayList<String>();

                String title = "";


               try{
                     PreparedStatement pStmt = conn.prepareStatement(
                             "SELECT TITLE \n" +
                             "FROM JOB_SKILL INNER JOIN KNOWLEDGE_SKILL \n" +
                             "ON JOB_SKILL.KS_CODE=KNOWLEDGE_SKILL.KS_CODE\n" +
                             "WHERE JOB_SKILL.JOB_CODE=? AND IMPORTANCE=? \n");
                   

                     pStmt.setString(1, jobCode + "");
                     pStmt.setString(2, importance + "");
                     ResultSet rset = pStmt.executeQuery();

                     while(rset.next()){
                          title = rset.getString(1);
                           
                        String jobSkills = "\n Job Title: " + title + "\n";
                        results.add(jobSkills);
                     }
                     


               }catch(Exception e){
                   System.out.println("\nError in the method getJobSkills in PersonJobHunting: " + e);
               }
               return results;
   
       }
      
}
