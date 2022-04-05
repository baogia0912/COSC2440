package student_enrollment_system;

public class Course {

    private final String courseName;
    private final String courseID;
    private final int credit;

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
        return courseID + " - " + courseName + "(" + credit + "cr)";
    }
}
