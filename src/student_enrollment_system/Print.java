package student_enrollment_system;

import java.text.SimpleDateFormat;
import java.util.List;

public class Print {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printStudents(List<Student> studentList) {
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~Student List~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        System.out.println("|   " + ANSI_GREEN + "ID" + ANSI_RESET + "   |          " +
                ANSI_YELLOW + "Name" + ANSI_RESET + "          |" +
                ANSI_BLUE + "Date of birth" + ANSI_RESET + "|");
        System.out.println("-------------------------------------------------");
        for (Student student : studentList) {
            System.out.printf("|%-17s|%-33s|%-22s|%n", ANSI_GREEN + student.getStudentID()  + ANSI_RESET,
                    ANSI_YELLOW + student.getStudentName()  + ANSI_RESET,
                    ANSI_BLUE + new SimpleDateFormat("MM/dd/yyyy").format(student.getBirthday()) + ANSI_RESET);
        }
        System.out.println("-------------------------------------------------");
    }

    public static void printCourses(List<Course> courseList) {
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Course List-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        System.out.println("|   " + ANSI_GREEN + "ID" + ANSI_RESET + "   |             " +
                ANSI_YELLOW + "Name" + ANSI_RESET + "             |" +
                ANSI_BLUE + "Credit " + ANSI_RESET + "|");
        System.out.println("-------------------------------------------------");
        for (Course course : courseList) {
            System.out.printf("|%-17s|%-39s|%-16s|%n", ANSI_GREEN + course.getCourseID()  + ANSI_RESET,
                    ANSI_YELLOW + course.getCourseName()  + ANSI_RESET,
                    ANSI_BLUE + course.getCredit() + ANSI_RESET);
        }
        System.out.println("-------------------------------------------------");
    }

    public static void printEnrollment(List<StudentEnrollment> enrollmentList) {
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-Enrollment List-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        System.out.println("|" + ANSI_GREEN + "Student ID" + ANSI_RESET + "|     " +
                ANSI_YELLOW + "Student name" + ANSI_RESET + "     |" +
                ANSI_BLUE + "Date of birth" + ANSI_RESET + "|" +
                ANSI_CYAN + "Course ID" + ANSI_RESET + "|         " +
                ANSI_PURPLE + "Course name" + ANSI_RESET + "         |" +
                ANSI_RED + "Credit" + ANSI_RESET + "|" +
                ANSI_WHITE + "Semester" + ANSI_RESET + "|");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (StudentEnrollment SE : enrollmentList) {
            System.out.printf("|%-19s|%-31s|%-22s|%-18s|%-38s|%-15s|%-17s|%n", ANSI_GREEN + SE.getStudent().getStudentID()  + ANSI_RESET,
                    ANSI_YELLOW + SE.getStudent().getStudentName()  + ANSI_RESET,
                    ANSI_BLUE + new SimpleDateFormat("MM/dd/yyyy").format(SE.getStudent().getBirthday()) + ANSI_RESET,
                    ANSI_CYAN + SE.getCourse().getCourseID()  + ANSI_RESET,
                    ANSI_PURPLE + SE.getCourse().getCourseName()  + ANSI_RESET,
                    ANSI_RED + SE.getCourse().getCredit() + ANSI_RESET,
                    ANSI_WHITE + SE.getSemester() + ANSI_RESET);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }
}
