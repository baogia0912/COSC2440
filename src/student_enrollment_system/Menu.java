package student_enrollment_system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    public static void menu(StudentEnrollmentSystem SES) {
        System.out.println();
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Student Enrollment System-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String errorMessage = null;
        while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
            if (errorMessage != null) {
                System.out.println(errorMessage);
                errorMessage = null;
            }
            System.out.println("Do you wish to load an use your own CSV file as enrollment database?" + ANSI_BLUE + "(y/n)" + ANSI_RESET +
                    "\nElse a default CSV file will be loaded and use as enrollment database.");
            input = scanner.nextLine();
            if (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                errorMessage = ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET;
            }
            if (input.equalsIgnoreCase("y")) {
                System.out.println(ANSI_BLUE + "Enter your path: ");
                input = scanner.nextLine();
                if (Validation.isValidPath(input) && input.endsWith(".csv") &&
                        CSV.readEnrollmentsFromCSV(input, SES.enrollmentList, SES.courseList, SES.studentList)) {
                    StudentEnrollmentSystem.databasePath = input;
                    while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("Do you wish to load more CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET);
                        input = scanner.nextLine();
                        if (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                            errorMessage = ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET;
                        }
                        if (input.equalsIgnoreCase("y")) {
                            while (!input.equalsIgnoreCase("4")) {
                                if (errorMessage != null) {
                                    System.out.println(errorMessage);
                                    errorMessage = null;
                                }
                                System.out.println("Uploading additional CSV file..." + ANSI_BLUE +
                                        "\n(1: Students CSV file, 2: Courses CSV file, 3: Enrollments CSV file)" + ANSI_YELLOW +
                                        "\n4: cancel" + ANSI_RESET +
                                        "\nData type option: ");
                                input = scanner.nextLine();

                                switch (input) {
                                    case "1" -> {
                                        System.out.println("Enter the path to your CSV file: ");
                                        input = scanner.nextLine();
                                        if (!Validation.isValidPath(input) || !input.endsWith(".csv") || !CSV.readStudentsFromCSV(input, SES.studentList)) {
                                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                                        }
                                    }
                                    case "2" -> {
                                        System.out.println("Enter the path to your CSV file: ");
                                        input = scanner.nextLine();
                                        if (!Validation.isValidPath(input) || !input.endsWith(".csv") || !CSV.readCoursesFromCSV(input, SES.courseList)) {
                                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                                        }
                                    }
                                    case "3" -> {
                                        System.out.println("Enter the path to your CSV file: ");
                                        input = scanner.nextLine();
                                        if (!Validation.isValidPath(input) || !input.endsWith(".csv") ||
                                                !CSV.readEnrollmentsFromCSV(input, SES.enrollmentList, SES.courseList, SES.studentList)) {
                                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                                        }
                                    }
                                    case "4" -> {
                                    }
                                    default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3' or '4'" + ANSI_RESET;
                                }
                            }
                        }
                    }
                    break;
                } else {
                    errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                }
            } else if (input.equalsIgnoreCase("n")) {
                CSV.readEnrollmentsFromCSV(StudentEnrollmentSystem.databasePath, SES.enrollmentList, SES.courseList, SES.studentList);
            }
            if (input.equals("4")) break;
        }
        input = "";
        while (!input.equalsIgnoreCase("6")) {
            System.out.println();
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Student Enrollment System-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
            if (errorMessage != null){
                System.out.println(errorMessage);
                errorMessage = null;
            }
            System.out.println("1: View data | 2: Update an enrollment | 3: View student's courses" +
                    "\n4: View course's students | 5: View courses in semester | 6: Exit");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    while (!input.equalsIgnoreCase("4")) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("1: View student list | 2: View course list | 3: View enrollment list | 4: Back");
                        input = scanner.nextLine();
                        switch (input) {
                            case "1" -> Print.printStudents(SES.studentList);
                            case "2" -> Print.printCourses(SES.courseList);
                            case "3" -> Print.printEnrollment(SES.enrollmentList);
                            case "4" -> {}
                            default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3', or '4'" + ANSI_RESET;
                        }
                    }
                }
                case "2" -> {
                    System.out.println("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    if (!Validation.isValidStudentID(studentID) || Student.findStudent(SES.studentList, studentID) == null) {
                        System.out.println(ANSI_RED + "Invalid student ID" + ANSI_RESET);
                    } else {
                        while (!input.equalsIgnoreCase("4")) {
                            if (errorMessage != null) {
                                System.out.println(errorMessage);
                                errorMessage = null;
                            }
                            System.out.println(ANSI_GREEN + "These are this student's enrollments" + ANSI_RESET);
                            List<StudentEnrollment> thisStudentEnrollments = new ArrayList<>();
                            for (StudentEnrollment SE : SES.enrollmentList) {
                                if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID)) {
                                    thisStudentEnrollments.add(SE);
                                }
                            }
                            Print.printEnrollment(thisStudentEnrollments);
                            System.out.println("1: Enroll a course | 2: Drop a course | 3: Update a course | 4: Back");
                            input = scanner.nextLine();
                            switch (input) {
                                case "1" -> {
                                    System.out.println(ANSI_GREEN + "These are the course list" + ANSI_RESET);
                                    Print.printCourses(SES.courseList);
                                    System.out.println("Enter course ID: ");
                                    String courseID = scanner.nextLine();
                                    if (!Validation.isValidCourseID(courseID) || Course.findCourse(SES.courseList, courseID) == null) {
                                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                        break;
                                    }
                                    System.out.println("Enter semester: ");
                                    String semester = scanner.nextLine();
                                    if (!Validation.isValidSemester(semester)) {
                                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                                        break;
                                    }
                                    StudentEnrollment SE = StudentEnrollment.findEnrollment(SES.enrollmentList, studentID, courseID, semester);
                                    if (SE != null) {
                                        System.out.println(ANSI_GREEN + SE + ANSI_RESET + " already exist and will not be added");
                                        break;
                                    }
                                    SE = new StudentEnrollment(Student.findStudent(SES.studentList, studentID), Course.findCourse(SES.courseList, courseID), semester);
                                    if (SES.add(SE))
                                        System.out.println(ANSI_PURPLE + SE.getStudent().getStudentName() + " have been enrolled in " +
                                                SE.getCourse().getCourseName() + " in semester " + SE.getSemester() + ANSI_RESET);
                                    else System.out.println(ANSI_RED + "Failed to enrolled student!" + ANSI_RESET);
                                }
                                case "2" -> {
                                    System.out.println("Enter course ID: ");
                                    String courseID = scanner.nextLine();
                                    if (!Validation.isValidCourseID(courseID) || Course.findCourse(SES.courseList, courseID) == null) {
                                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                        break;
                                    }
                                    System.out.println("Enter semester: ");
                                    String semester = scanner.nextLine();
                                    if (!Validation.isValidSemester(semester)) {
                                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                                        break;
                                    }
                                    StudentEnrollment SE = StudentEnrollment.findEnrollment(SES.enrollmentList, studentID, courseID, semester);
                                    if (SE == null) {
                                        System.out.println(ANSI_RED + "Enrollment does not exist!" + ANSI_RESET);
                                    } else if (SES.delete(SE))
                                        System.out.println(ANSI_PURPLE + SE.getStudent().getStudentName() + " have dropped " +
                                                SE.getCourse().getCourseName() + " in semester " + SE.getSemester() + ANSI_RESET);
                                    else System.out.println(ANSI_RED + "Failed to drop the course" + ANSI_RESET);
                                }
                                case "3" -> {
                                    System.out.println("Enter semester you want to update: ");
                                    String semester = scanner.nextLine();
                                    if (!Validation.isValidSemester(semester)) {
                                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                                        break;
                                    }
                                    List<Course> courseAvailableForUpdate = StudentEnrollmentSystem.findCoursesOfStudent(SES.enrollmentList, studentID, semester);
                                    if (courseAvailableForUpdate.isEmpty()) {
                                        System.out.println(ANSI_RED + "Student does not have any course in semester " + semester + ANSI_RESET);
                                    } else {
                                        Print.printCourses(courseAvailableForUpdate);
                                        System.out.println("Enter course you want to change: ");
                                        String courseID = scanner.nextLine();
                                        Course oldCourse = Course.findCourse(SES.courseList, courseID);
                                        if (!Validation.isValidCourseID(courseID) || !courseAvailableForUpdate.contains(oldCourse) || oldCourse == null) {
                                            errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                            break;
                                        }
                                        courseAvailableForUpdate = StudentEnrollmentSystem.findCoursesDifferences(courseAvailableForUpdate, SES.courseList);
                                        System.out.println("These are the courses available for enrollment");
                                        Print.printCourses(courseAvailableForUpdate);
                                        System.out.println("Enter course you want to change to: ");
                                        courseID = scanner.nextLine();
                                        Course newCourse = Course.findCourse(SES.courseList, courseID);
                                        if (!Validation.isValidCourseID(courseID) || !courseAvailableForUpdate.contains(newCourse) || newCourse == null) {
                                            errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                            break;
                                        }
                                        StudentEnrollment oldEnrollment = StudentEnrollment.findEnrollment(SES.enrollmentList, studentID, oldCourse.getCourseID(),semester);
                                        StudentEnrollment newEnrollment = new StudentEnrollment(Student.findStudent(SES.studentList, studentID), newCourse, semester);
                                        if (SES.update(oldEnrollment, newEnrollment)) {
                                            System.out.println(ANSI_PURPLE + oldEnrollment + " have been update to " + newEnrollment + ANSI_RESET);
                                        } else {
                                            System.out.println(ANSI_RED + "Failed to update enrollment!" + ANSI_RESET);
                                        }
                                    }
                                }
                                case "4" -> {}
                                default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3' or '4'" + ANSI_RESET;
                            }
                        }
                    }
                }

                case "3" -> {
                    System.out.println("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    if (!Validation.isValidStudentID(studentID) || Student.findStudent(SES.studentList, studentID) == null) {
                        errorMessage = ANSI_RED + "Invalid student ID" + ANSI_RESET;
                        break;
                    }
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!Validation.isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    Print.printCourses(StudentEnrollmentSystem.findCoursesOfStudent(SES.enrollmentList, studentID, semester));
                    while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("Do you wish to save this to a CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET);
                        input = scanner.nextLine();
                        if (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                            errorMessage = ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET;
                        }
                    }
                    if (input.equalsIgnoreCase("y")) {
                        CSV.saveCoursesToCSV(StudentEnrollmentSystem.findCoursesOfStudent(SES.enrollmentList, studentID, semester));
                    }
                }
                case "4" -> {
                    System.out.println("Enter course ID: ");
                    String courseID = scanner.nextLine();
                    if (!Validation.isValidCourseID(courseID) || Course.findCourse(SES.courseList, courseID) == null) {
                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                        break;
                    }
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!Validation.isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    Print.printStudents(StudentEnrollmentSystem.findStudentsOfCourse(SES.enrollmentList, courseID, semester));
                    while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("Do you wish to save this to a CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET);
                        input = scanner.nextLine();
                        if (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                            errorMessage = ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET;
                        }
                    }
                    if (input.equalsIgnoreCase("y")) {
                        CSV.saveStudentsToCSV(StudentEnrollmentSystem.findStudentsOfCourse(SES.enrollmentList, courseID, semester));
                    }
                }
                case "5" -> {
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!Validation.isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    Print.printCourses(StudentEnrollmentSystem.findCourseInSemester(SES.enrollmentList, semester));
                    while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("Do you wish to save this to a CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET);
                        input = scanner.nextLine();
                        if (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
                            errorMessage = ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET;
                        }
                    }
                    if (input.equalsIgnoreCase("y")) {
                        CSV.saveCoursesToCSV(StudentEnrollmentSystem.findCourseInSemester(SES.enrollmentList, semester));
                    }
                }
                case "6" -> {}
                default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3', '4', '5' or '6'" + ANSI_RESET;
            }
        }
    }
}
