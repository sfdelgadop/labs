package co.edu.unal.software_engineering.labs.pojo;

public class RegisterCoursePOJO {

    private String username;
    private String password;
    private String courseName;
    private Integer durationHours;

    public String getPassword( ){
        return password;
    }

    public void setPassword( String password ){
        this.password = password;
    }

    public String getUsername( ){
        return username;
    }

    public void setUsername( String username ){
        this.username = username;
    }


    public String getCourseName(){
        return courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public Integer getDurationHours(){
        return durationHours;
    }

    public void setDurationHours(Integer durationHours){
        this.durationHours = durationHours;
    }

}
