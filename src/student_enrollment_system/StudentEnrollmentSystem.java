package student_enrollment_system;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

interface StudentEnrollmentManager{

    boolean add(StudentEnrollment SE);
    boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment);
    boolean delete(StudentEnrollment SE);
    StudentEnrollment getOne(String ID);
    List<StudentEnrollment> getAll();
}

public class StudentEnrollmentSystem implements StudentEnrollmentManager {

    List<StudentEnrollment> enrollmentList = new ArrayList<>();
    List<Course> courseList = new ArrayList<>();
    List<Student> studentList = new ArrayList<>();

    public static final String defaultDatabasePath = "src/student_enrollment_system/csvFiles/default.csv";
    public static String databasePath = "src/student_enrollment_system/csvFiles/default.csv";

    @Override
    public boolean add(StudentEnrollment SE) {
        File file = new File(databasePath);
        try {
            PrintWriter outputFile = new PrintWriter(new FileOutputStream(file, true));

            outputFile.printf("%s,%s,%s,%s,%s,%d,%s\n",
                    SE.getStudent().getStudentID(), SE.getStudent().getStudentName(),
                    new SimpleDateFormat("MM/dd/yyyy").format(SE.getStudent().getBirthday()),
                    SE.getCourse().getCourseID(), SE.getCourse().getCourseName(),
                    SE.getCourse().getCredit(), SE.getSemester()
            );
            outputFile.close();
            enrollmentList.add(SE);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment) {
        File file = new File(databasePath);
        try {
            enrollmentList.remove(oldEnrollment);
            enrollmentList.add(newEnrollment);
            PrintWriter outputFile = new PrintWriter(file);

            for (StudentEnrollment SE : enrollmentList) {
                outputFile.printf("%s,%s,%s,%s,%s,%d,%s\n",
                        SE.getStudent().getStudentID(), SE.getStudent().getStudentName(),
                        new SimpleDateFormat("MM/dd/yyyy").format(SE.getStudent().getBirthday()),
                        SE.getCourse().getCourseID(), SE.getCourse().getCourseName(),
                        SE.getCourse().getCredit(), SE.getSemester()
                );
            }
            outputFile.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(StudentEnrollment oldSE) {
        File file = new File(databasePath);
        try {
            enrollmentList.remove(oldSE);
            PrintWriter outputFile = new PrintWriter(file);

            for (StudentEnrollment SE : enrollmentList) {
                outputFile.printf("%s,%s,%s,%s,%s,%d,%s\n",
                        SE.getStudent().getStudentID(), SE.getStudent().getStudentName(),
                        new SimpleDateFormat("MM/dd/yyyy").format(SE.getStudent().getBirthday()),
                        SE.getCourse().getCourseID(), SE.getCourse().getCourseName(),
                        SE.getCourse().getCredit(), SE.getSemester()
                );
            }
            outputFile.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public StudentEnrollment getOne(String studentID) {
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID)) {
                return SE;
            }
        }
        System.out.println("Enrollment not found!");
        return null;
    }

    @Override
    public List<StudentEnrollment> getAll() {
        return enrollmentList;
    }

    public static List<Course> findCoursesOfStudent(List<StudentEnrollment> enrollmentList, String studentID, String semester) {
        List<Course> queryList = new ArrayList<>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID) && SE.getSemester().equalsIgnoreCase(semester)
                    && !queryList.contains(SE.getCourse())) {
                queryList.add(SE.getCourse());
            }
        }
        return queryList;
    }

    public static List<Student> findStudentsOfCourse(List<StudentEnrollment> enrollmentList, String courseId, String semester) {
        List<Student> queryList = new ArrayList<>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getCourse().getCourseID().equalsIgnoreCase(courseId) && SE.getSemester().equalsIgnoreCase(semester)
                    && !queryList.contains(SE.getStudent())) {
                queryList.add(SE.getStudent());
            }
        }
        return queryList;
    }

    public static List<StudentEnrollment> findEnrollmentsOfStudent(List<StudentEnrollment> enrollmentList, String studentID) {
        List<StudentEnrollment> queryList = new ArrayList<>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID)) {
                queryList.add(SE);
            }
        }
        return queryList;
    }

    public static List<Course> findCourseInSemester(List<StudentEnrollment> enrollmentList, String semester) {
        List<Course> queryList = new ArrayList<>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getSemester().equalsIgnoreCase(semester) && !queryList.contains(SE.getCourse())) {
                queryList.add(SE.getCourse());
            }
        }
        return queryList;
    }

    public static List<Course> findCourseOfStudentInSemester(List<StudentEnrollment> enrollmentList, String studentID, String semester) {
        List<Course> queryList = new ArrayList<>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID) && SE.getSemester().equalsIgnoreCase(semester)) {
                queryList.add(SE.getCourse());
            }
        }
        return queryList;
    }

    public static List<Course> findCoursesDifferences(List<Course> list1, List<Course> list2) {
        List<Course> queryList = new ArrayList<>();
        for (Course course2 : list2) {
            boolean add = true;
            for (Course course1 : list1) {
                if (course1.equals(course2)) {
                    add = false;
                    break;
                }
            }
            if (add) queryList.add(course2);
        }
        return queryList;
    }

    public static void main (String[] args){
        StudentEnrollmentSystem SES = new StudentEnrollmentSystem();
        Menu.menu(SES);


    }

}
