import java.util.*;
import java.sql.*;

public class JobHunting {

    private Connection conn;
    private Scanner input = new Scanner(System.in);

    public JobHunting(Connection conn){
        this.conn = conn;
    }

    public void searchMenuRunner(){
        System.out.println("Enter \"0\" to exit");
        System.out.println("Enter \"1\" to search for jobs");
        menu(input.nextInt());
    }

    public void menu(int menuOption){
        if (menuOption != 0){
            if(menuOption == 1) searchJobs();
            System.out.println("\nSearch complete, hit enter to continue");
            input.nextLine();
            input.nextLine();
            searchMenuRunner();

        }
    }

    public void searchJobs(){
        JobHunting jobHunt = new JobHunting(conn);
        jobHunt.searchJobOptions();
    }

    public void searchJobOptions(){
        int option = 0;
        do {

            System.out.println("Select 1: Search by Job Categories");
            System.out.println("Select 2: Search by Job Categories you qualify for");
            System.out.println("Select 3: Find all jobs you qualify for");
            System.out.println("Select 0: to exit");
            option = input.nextInt();
            showSearchedJobs(option);
        } while (option != 0);
    }

    public void showSearchedJobs(int userOption){

        try{
            if (userOption == 1){
                jobCategories();
                jobsList();
            }
            else if(userOption ==2){
                qualifiedCategories();
                jobsList();
            }
            else if(userOption == 3){
                qualifiedJobs();
            }

        } catch (Exception e){
            System.out.println("\nError in method showSearchedJobs in JobHunting" + e);
        }
    }

    public void jobCategories() {
        System.out.println("All Job Categories. Select a category\n\n");

        try {

            PreparedStatement pStmt = conn.prepareStatement(
                    "SELECT CATE_CODE, TITLE\n" +
                            "FROM JOB_CATEGORY");


            ResultSet rset = pStmt.executeQuery();

            System.out.println("Cate Code \tCategory Title\n");
            while (rset.next()) {
                String title = rset.getString("title");
                Integer cateCode = rset.getInt("cate_code");
                System.out.println(cateCode + "\t\t" + title);
            }
        } catch(Exception e) {
            System.out.println("\nError in method jobCategories in JobHunting" + e);
        }
    }

    public void jobsList(){
        System.out.println("Select a cate code to search for jobs");
        int option = input.nextInt();

        try {

            PreparedStatement pStmt = conn.prepareStatement(
                    "SELECT JOB_CODE, JOB_TITLE, PAY_RATE, PAY_TYPE\n" +
                            "FROM JOB NATURAL JOIN JOB_CATEGORY\n " +
                            "WHERE CATE_CODE = ?");

            pStmt.setInt(1, option);
            ResultSet rset = pStmt.executeQuery();

            System.out.printf("%-10s %-20s %-10s %-10s%n%n", "Job Code",
                    "Job Title", "Pay Rate", "Pay Type");
            while (rset.next()) {
                String jobCode = rset.getString("job_code");
                String jobTitle = rset.getString("job_title");
                String payRate = rset.getString("pay_rate");
                String payType = rset.getString("pay_type");

                System.out.printf("%-10.10s %-20.20s %-10.10s %-10.10s%n%n", jobCode, jobTitle, payRate, payType);
            }
        } catch(Exception e) {
            System.out.println("\nError in method jobsList in JobHunting"+ e);
        }
    }

    public void qualifiedCategories(){
        try {
            PreparedStatement pStmt = conn.prepareStatement(
                    "SELECT DISTINCT CATE_CODE, TITLE\n " +
                            "FROM JOB_CATEGORY JC\n " +
                            "WHERE EXISTS (SELECT KS_CODE\n " +
                            "FROM JOB_SKILL JB\n " +
                            "WHERE JC.KS_CODE=JB.KS_CODE\n " +
                            "MINUS\n " +
                            "SELECT KS_CODE\n " +
                            "FROM PERSON_SKILL\n " +
                            "WHERE PER_ID = ?)");

            System.out.println("Enter person ID");
            pStmt.setInt(1, input.nextInt());
            ResultSet rset = pStmt.executeQuery();

            System.out.println("Job Categories you qualify for.\n\n");
            System.out.println("Cate Code \tCategory Title\n");
            while (rset.next()) {
                String jobTitle = rset.getString("title");
                Integer cateCode = rset.getInt("cate_code");
                System.out.println(cateCode + "\t\t" + jobTitle);
            }
        } catch(Exception e) {
            System.out.println("\nError in method qualifiedCategories in JobHunting " + e);
        }

    }

    public void qualifiedJobs() {
        try {
            PreparedStatement pStmt = conn.prepareStatement(
                    "SELECT DISTINCT JOB_CODE, JOB_TITLE, PAY_RATE, PAY_TYPE\n" +
                            "FROM JOB J\n" +
                            "WHERE NOT EXISTS(\n" +
                            "SELECT KS_CODE\n " +
                            "FROM JOB_SKILL JB\n " +
                            "WHERE JB.JOB_CODE = J.JOB_CODE\n " +
                            "MINUS\n " +
                            "SELECT KS_CODE\n " +
                            "FROM PERSON_SKILL\n" +
                            "WHERE PER_ID = ?)");

            System.out.println("Enter person ID");
            pStmt.setInt(1, input.nextInt());
            ResultSet rset = pStmt.executeQuery();

            System.out.printf("%-10s %-20s %-10s %-10s%n%n", "Job Code",
                    "Job Title", "Pay Rate", "Pay Type");
            while (rset.next()) {
                String jobCode = rset.getString("job_code");
                String jobTitle = rset.getString("job_title");
                String payRate = rset.getString("pay_rate");
                String payType = rset.getString("pay_type");

                System.out.printf("%-10.10s %-20.20s %-10.10s %-10.10s%n%n", jobCode, jobTitle, payRate, payType);
            }
        } catch(Exception e) {
            System.out.println("\nError in method qualifiedJobs in JobHunting" + e);
        }
    }
}
