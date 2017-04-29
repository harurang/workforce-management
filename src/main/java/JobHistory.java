/**
 * Created by hillaryarurang on 4/29/17.
 */
public class JobHistory {

    String startDate;
    String endDate;
    int jobListing;

    public JobHistory (String startDate, String endDate, int jobListing) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobListing = jobListing;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getJobListing() {
        return jobListing;
    }

    public void setJobListing(int jobListing) {
        this.jobListing = jobListing;
    }
}
