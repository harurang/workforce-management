public class Course{

     int cCode;
     String title;
     String courseLevel;
     String description;
     String status;
     int prereq;


     public Course(int cCode, String title, String courseLevel, String description, String status, int prereq){
          this.cCode = cCode;
          this.title = title;
          this.courseLevel = courseLevel;
          this.description = description;
          this.status = status;
          this.pereq = prereq;
     }

     public int getCCode() {
        return cCode;
     }

     public void setCCode(int cCode) {
           this.cCode = cCode;
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
            this.title = title;
     }
 
     public String getCourseLevel() {
         return courseLevel;
     }

     public void setCourseLevel(String courseLevel) {
            this.courseLevel = courseLevel;
     }


     public String getDescription() {
         return description;
    }

    public void setDescription(String description) {
             this.description = description;
    }

    public String getStatus() {
         return status;
    }

    public void setStatus(String status) {
          this.status = status;
    }

    public int getPrereq() {
         return prereq;
    }

    public void setPrereq(int prereq) {
         this.prereq = prereq;
    }





}
