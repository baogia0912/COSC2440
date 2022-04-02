package student_enrollment_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

interface StudentEnrollmentManager{

    boolean add(StudentEnrollment SE);
    boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment);
    boolean delete(Student student, Course course, String semester);
    boolean getOne();
    void getAll();
}

public class StudentEnrollmentSystem implements StudentEnrollmentManager {
    List<StudentEnrollment> enrollmentList = new ArrayList<StudentEnrollment>();
    List<Course> courseList = new ArrayList<Course>();
    List<Student> studentList = new ArrayList<Student>();

    public boolean add(StudentEnrollment SE) {
        if (enrollmentList.add(SE)) return true;
        return false;
    }

    public boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment) {
        return false;
    }

    public boolean delete(Student student, Course course, String semester) {
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent() == student && SE.getCourse() == course && SE.getSemester() == semester) {
                enrollmentList.remove(SE);
                System.out.println("Enrollment deleted successfully");
                return true;
            }
        }
        System.out.println("Enrollment not found!");
        return false;
    }

    public boolean getOne() {
        return false;
    }

    public void getAll() {
        for (StudentEnrollment i : enrollmentList) {
            System.out.println(i);
        }
    }

    private static Student createStudent(String[] metadata, List<Student> studentList) {
        String studentID = metadata[0];
        String studentName = metadata[1];
        Date birthday = null;
        try {
            birthday = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(metadata[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Student student : studentList) {
            if (student.getStudentID().equals(studentID)) {
                System.out.println(student.getStudentID()+ " - " + student.getStudentName()
                        + " already exist and will not be added");
                return student;
            }
        }
        Student student = new Student(studentID, studentName, birthday);
        studentList.add(student);
        return student;
    }

    private static List<Student> readStudentsFromCSV(String fileName, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                createStudent(attributes, studentList);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return studentList;
    }

    private static Course createCourse(String[] metadata, List<Course> courseList) {
        String courseID = metadata[0];
        String courseName = metadata[1];
        int credit = Integer.parseInt(metadata[2]);
        for (Course course : courseList) {
            if (course.getCourseID().equals(courseID)) {
                System.out.println(course.getCourseID()+ " - " + course.getCourseName()
                        + " already exist and will not be added");
                return course;
            }
        }
        Course course = new Course(courseID, courseName, credit);
        courseList.add(course);
        return course;
    }

    private static List<Course> readCoursesFromCSV(String fileName, List<Course> courseList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                createCourse(attributes, courseList);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return courseList;
    }

    private static void createEnrollment(String[] metadata,
                    List<StudentEnrollment> enrollmentsList, List<Course> courseList, List<Student> studentList) {
        Student student = createStudent(Arrays.copyOfRange(metadata, 0, 3), studentList);
        Course course = createCourse(Arrays.copyOfRange(metadata, 3, 6), courseList);
        String studentID = metadata[0];
        String courseID = metadata[3];
        String semester = metadata[6];

        for (StudentEnrollment SE : enrollmentsList) {
            if (SE.getCourse().getCourseID().equals(courseID) && SE.getStudent().getStudentID().equals(studentID)
                    && SE.getSemester().equals(semester)) {
                System.out.println(SE + " already exist and will not be added");
                return;
            }
        }
        enrollmentsList.add(new StudentEnrollment(student, course, semester));
    }

    private static List<StudentEnrollment> readEnrollmentsFromCSV(String fileName,
                  List<StudentEnrollment> enrollmentsList, List<Course> courseList, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                createEnrollment(attributes, enrollmentsList, courseList, studentList);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return enrollmentsList;
    }

    public static void main (String args[]){
        StudentEnrollmentSystem SES = new StudentEnrollmentSystem();

        SES.studentList = readStudentsFromCSV("src/student_enrollment_system/TestingStudents.csv",
                SES.studentList);
        SES.courseList = readCoursesFromCSV("src/student_enrollment_system/TestingCourses.csv",
                SES.courseList);
        SES.enrollmentList = readEnrollmentsFromCSV("src/student_enrollment_system/default.csv",
                SES.enrollmentList, SES.courseList, SES.studentList);
        SES.getAll();
        for (Student student : SES.studentList) {
            System.out.println(student);
        }
    }

}
