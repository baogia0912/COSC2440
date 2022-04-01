package student_enrollment_system;

import java.util.ArrayList;

public class Course {

    private String courseName;
    private String courseID;
    private int credit;

    public Course() {
        this.courseID = "c001";
        this.courseName = "default";
        this.credit = 0;
    }

    public Course(String courseID, String courseName, int credit) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credit = credit;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public int getCredit() {
        return credit;
    }

    @Override
    public String toString() {
        return "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit='" + credit + '\'';
    }
}
