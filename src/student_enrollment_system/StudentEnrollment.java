package student_enrollment_system;


import java.util.Arrays;
import java.util.List;

public class StudentEnrollment {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final Student student;
    private final Course course;
    private final String semester;

    public StudentEnrollment() {
        this.student = new Student();
        this.course = new Course();
        this.semester = "2022A";
    }

    public StudentEnrollment(Student student, Course course, String semester) {
        this.student = student;
        this.course = course;
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getSemester() {
        return semester;
    }

    public static StudentEnrollment findEnrollment(List<StudentEnrollment> enrollmentList, String studentID, String courseID, String semester) {
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getCourse().getCourseID().equalsIgnoreCase(courseID) && SE.getStudent().getStudentID().equalsIgnoreCase(studentID)
                    && SE.getSemester().equalsIgnoreCase(semester)) {
                return SE;
            }
        }
        return null;
    }


    static void createEnrollment(String[] metadata,
                                 List<StudentEnrollment> enrollmentList, List<Course> courseList, List<Student> studentList) {
        Student student = Student.createStudent(Arrays.copyOfRange(metadata, 0, 3), studentList);
        Course course = Course.createCourse(Arrays.copyOfRange(metadata, 3, 6), courseList);
        String studentID = metadata[0];
        String courseID = metadata[3];
        String semester = metadata[6];

        StudentEnrollment SE = findEnrollment(enrollmentList, studentID, courseID, semester);
        if (SE != null) {
            System.out.println(ANSI_GREEN + SE + ANSI_RESET + " already exist and will not be added");
            return;
        }

        StudentEnrollment newSE = new StudentEnrollment(student, course, semester);
        enrollmentList.add(newSE);
    }

    @Override
    public String toString() {
        return student + " | " + course +
                " | " + semester;
    }
}
