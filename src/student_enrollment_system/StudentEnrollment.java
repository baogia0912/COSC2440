package student_enrollment_system;


public class StudentEnrollment {
    private Student student;
    private Course course;
    private String semester;

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

    @Override
    public String toString() {
        return student + " | " + course +
                " | " + semester;
    }
}
