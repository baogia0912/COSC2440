package student_enrollment_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

interface StudentEnrollmentManager{

    boolean add(StudentEnrollment SE);
    boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment);
    boolean delete(String studentID, String courseID, String semester);
    StudentEnrollment getOne(String studentID, String courseID, String semester);
    List<StudentEnrollment> getAll();
}

public class StudentEnrollmentSystem implements StudentEnrollmentManager {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    List<StudentEnrollment> enrollmentList = new ArrayList<StudentEnrollment>();
    List<Course> courseList = new ArrayList<Course>();
    List<Student> studentList = new ArrayList<Student>();

    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidStudentAttr(String[] attributes) {
        if (!Pattern.compile("S[0-9]{6}").matcher(attributes[0]).find()) return false;

        try {
            new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(attributes[2]);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidCourseAttr(String[] attributes) {
        if (!Pattern.compile("[A-Z]{3,4}[0-9]{4}").matcher(attributes[0]).find()) return false;

        try {
            Integer.parseInt(attributes[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidEnrollmentAttributes(String[] attributes) {

        if (!Pattern.compile("[0-9]{4}[A-C]").matcher(attributes[6]).find()) return false;
        if (!isValidStudentAttr(Arrays.copyOfRange(attributes, 0, 3))) return false;
        return isValidCourseAttr(Arrays.copyOfRange(attributes, 3, 6));
    }

    @Override
    public boolean add(StudentEnrollment SE) {
        return enrollmentList.add(SE);
    }

    @Override
    public boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment) {
        return false;
    }

    @Override
    public boolean delete(String studentID, String courseID, String semester) {
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equals(studentID) &&
                    SE.getCourse().getCourseID().equals(courseID) && SE.getSemester().equals(semester)) {

                enrollmentList.remove(SE);
                System.out.println("Enrollment deleted successfully");
                return true;
            }
        }
        System.out.println("Enrollment not found!");
        return false;
    }

    @Override
    public StudentEnrollment getOne(String studentID, String courseID, String semester) {
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equals(studentID) &&
                    SE.getCourse().getCourseID().equals(courseID) && SE.getSemester().equals(semester)) {
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
                System.out.println(ANSI_GREEN + student.getStudentID()+ " - " + student.getStudentName() + ANSI_RESET
                        + " already exist and will not be added");
                return student;
            }
        }
        Student student = new Student(studentID, studentName, birthday);
        studentList.add(student);
        return student;
    }

    private static void readStudentsFromCSV(String fileName, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (isValidStudentAttr(attributes) && attributes.length == 3) {
                    createStudent(attributes, studentList);
                } else {
                    System.out.println(ANSI_RED + "Unable to parse this line to the database: " + ANSI_YELLOW + line + ANSI_RESET);
                }
                line = br.readLine();
            }
            System.out.println(ANSI_PURPLE + "\nYour file has been loaded!\n" + ANSI_RESET);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static Course createCourse(String[] metadata, List<Course> courseList) {
        String courseID = metadata[0];
        String courseName = metadata[1];
        int credit = Integer.parseInt(metadata[2]);
        for (Course course : courseList) {
            if (course.getCourseID().equals(courseID)) {
                System.out.println(ANSI_GREEN + course.getCourseID()+ " - " + course.getCourseName() + ANSI_RESET
                        + " already exist and will not be added");
                return course;
            }
        }
        Course course = new Course(courseID, courseName, credit);
        courseList.add(course);
        return course;
    }

    private static void readCoursesFromCSV(String fileName, List<Course> courseList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (isValidCourseAttr(attributes) && attributes.length == 3) {
                    createCourse(attributes, courseList);
                } else {
                    System.out.println(ANSI_RED + "Unable to parse this line to the database: " + ANSI_YELLOW + line + ANSI_RESET);
                }
                line = br.readLine();
            }
            System.out.println(ANSI_PURPLE + "\nYour file has been loaded!\n" + ANSI_RESET);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static StudentEnrollment createEnrollment(String[] metadata,
                    List<StudentEnrollment> enrollmentsList, List<Course> courseList, List<Student> studentList) {
        Student student = createStudent(Arrays.copyOfRange(metadata, 0, 3), studentList);
        Course course = createCourse(Arrays.copyOfRange(metadata, 3, 6), courseList);
        String studentID = metadata[0];
        String courseID = metadata[3];
        String semester = metadata[6];

        for (StudentEnrollment SE : enrollmentsList) {
            if (SE.getCourse().getCourseID().equals(courseID) && SE.getStudent().getStudentID().equals(studentID)
                    && SE.getSemester().equals(semester)) {
                System.out.println(ANSI_GREEN + SE + ANSI_RESET + " already exist and will not be added");
                return SE;
            }
        }

        StudentEnrollment SE = new StudentEnrollment(student, course, semester);
        enrollmentsList.add(SE);
        return SE;
    }

    private static void readEnrollmentsFromCSV(String fileName,
                   List<StudentEnrollment> enrollmentsList, List<Course> courseList, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (isValidEnrollmentAttributes(attributes) && attributes.length == 7) {
                    createEnrollment(attributes, enrollmentsList, courseList, studentList);
                } else {
                    System.out.println(ANSI_RED + "Unable to parse this line to the database: " + ANSI_YELLOW + line + ANSI_RESET);
                }
                line = br.readLine();
            }
            System.out.println(ANSI_PURPLE + "\nYour file has been loaded!\n" + ANSI_RESET);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void printStudents(List<Student> studentList) {
        System.out.println(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~Student List~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        System.out.println("|   " + ANSI_GREEN + "ID" + ANSI_RESET + "   |          " +
                                    ANSI_YELLOW + "Name" + ANSI_RESET + "          |" +
                                    ANSI_BLUE + "Date of birth" + ANSI_RESET + "|");
        System.out.println("-------------------------------------------------");
        for (Student student : studentList) {
            System.out.printf("|%-17s|%-33s|%-22s|%n", ANSI_GREEN + student.getStudentID()  + ANSI_RESET,
                            ANSI_YELLOW + student.getStudentName()  + ANSI_RESET,
                            ANSI_BLUE + new SimpleDateFormat("dd/MM/yyyy").format(student.getBirthday()) + ANSI_RESET);
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
                    ANSI_BLUE + new SimpleDateFormat("dd/MM/yyyy").format(SE.getStudent().getBirthday()) + ANSI_RESET,
                    ANSI_CYAN + SE.getCourse().getCourseID()  + ANSI_RESET,
                    ANSI_PURPLE + SE.getCourse().getCourseName()  + ANSI_RESET,
                    ANSI_RED + SE.getCourse().getCredit() + ANSI_RESET,
                    ANSI_WHITE + SE.getSemester() + ANSI_RESET);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    public static void menu(StudentEnrollmentSystem SES) {
        System.out.println();
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Student Enrollment System-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you wish to load your own CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET +
                            "\nElse a default file will be loaded.");
        String input = scanner.nextLine();
        while (!Arrays.asList("y", "Y", "n", "N").contains(input)) {
            System.out.println(ANSI_RED + "Input error! Please only enter 'y' or 'n'" + ANSI_RESET);
            System.out.println("Do you wish to load your own CSV file?" + ANSI_BLUE + "(y/n)" + ANSI_RESET +
                                "\nElse a default file will be loaded.");
            input = scanner.nextLine();
        }
        if (input.equalsIgnoreCase("y")) {
            String errorMessage = null;
            while (!input.equals("4")) {
                if (errorMessage != null){
                    System.out.println(errorMessage);
                    errorMessage = null;
                }
                System.out.println("Uploading CSV..." + ANSI_BLUE +
                        "\n(1: Students CSV file, 2: Courses CSV file, 3: Enrollments CSV file)" + ANSI_YELLOW +
                        "\n4: cancel" + ANSI_RESET +
                        "\nData type option: ");
                input = scanner.nextLine();

                switch (input) {
                    case "1" -> {
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readStudentsFromCSV(input, SES.studentList);
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                    }
                    case "2" -> {
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readCoursesFromCSV(input, SES.courseList);
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                    }
                    case "3" -> {
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readEnrollmentsFromCSV(input, SES.enrollmentList, SES.courseList, SES.studentList);
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                    }
                    case "4" -> {}
                    default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3' or '4'" + ANSI_RESET;
                }
            }

        } else {
            readEnrollmentsFromCSV("src/student_enrollment_system/default.csv",
                    SES.enrollmentList, SES.courseList, SES.studentList);
        }
        input = "";
        String errorMessage = null;
        while (!input.equals("6")) {
            System.out.println();
            System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Student Enrollment System-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
            if (errorMessage != null){
                System.out.println(errorMessage);
                errorMessage = null;
            }
            System.out.println("1: view data | 2: | 3: | 4: | 5: | 6: Exit");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    while (!input.equals("4")) {
                        if (errorMessage != null) {
                            System.out.println(errorMessage);
                            errorMessage = null;
                        }
                        System.out.println("1: View student list | 2: View course list | 3: View enrollment list | 4: Back");
                        input = scanner.nextLine();
                        switch (input) {
                            case "1" -> printStudents(SES.studentList);
                            case "2" -> printCourses(SES.courseList);
                            case "3" -> printEnrollment(SES.enrollmentList);
                            case "4" -> {}
                            default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3', or '4'" + ANSI_RESET;
                        }
                    }
                }
                case "2" -> {}

                case "3" -> {}

                case "4" -> {}

                case "5" -> {}

                case "6" -> {}
                default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3', '4', '5' or '6'" + ANSI_RESET;
            }
        }
    }

    public static void main (String[] args){
        StudentEnrollmentSystem SES = new StudentEnrollmentSystem();
        menu(SES);


    }

}
