package student_enrollment_system;

import java.util.List;

public class Course {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
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

    public static Course findCourse(List<Course> courseList, String courseID) {
        for (Course course : courseList) {
            if (course.getCourseID().equalsIgnoreCase(courseID)) {
                return course;
            }
        }
        return null;
    }

    static Course createCourse(String[] metadata, List<Course> courseList) {
        String courseID = metadata[0];
        String courseName = metadata[1];
        int credit = Integer.parseInt(metadata[2]);

        Course course = findCourse(courseList, courseID);
        if (course != null) {
            System.out.println(ANSI_GREEN + course.getCourseID()+ " - " + course.getCourseName() + ANSI_RESET
                    + " already exist and will not be added");
            return course;
        }

        Course newCourse = new Course(courseID, courseName, credit);
        courseList.add(newCourse);
        return newCourse;
    }

    @Override
    public String toString() {
        return courseID + " - " + courseName + "(" + credit + "cr)";
    }
}
