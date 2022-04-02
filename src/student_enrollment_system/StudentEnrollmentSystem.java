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
    boolean delete(Student student, Course course, String semester);
    boolean getOne();
    void getAll();
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
            int credit = Integer.parseInt(attributes[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidEnrollmentAttributes(String[] attributes) {

        if (!Pattern.compile("[0-9]{4}[A-C]").matcher(attributes[6]).find()) return false;
        if (!isValidStudentAttr(Arrays.copyOfRange(attributes, 0, 3))) return false;
        if (!isValidCourseAttr(Arrays.copyOfRange(attributes, 3, 6))) return false;
        return true;
    }

    public boolean add(StudentEnrollment SE) {
        return enrollmentList.add(SE);
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

    private static List<StudentEnrollment> readEnrollmentsFromCSV(String fileName,
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
        return enrollmentsList;
    }

    public static void menu(StudentEnrollmentSystem SES) {
        System.out.println();
        System.out.println(ANSI_CYAN_BACKGROUND + ANSI_BLACK + "-~-~-~-~-~-~-~-~-~-Student Enrollment System-~-~-~-~-~-~-~-~-~-" + ANSI_RESET);
        System.out.println();
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
            label:
            while (!input.equals("4")) {
                if (errorMessage != null) {
                    System.out.println(errorMessage);
                }
                System.out.println("Uploading CSV..." + ANSI_BLUE +
                        "\n(1: Students CSV file, 2: Courses CSV file, 3: Enrollments CSV file)" + ANSI_YELLOW +
                        "\n4: cancel" + ANSI_RESET +
                        "\nData type option: ");
                input = scanner.nextLine();

                switch (input) {
                    case "1":
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readStudentsFromCSV(input, SES.studentList);
                            errorMessage = null;
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                        break;
                    case "2":
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readCoursesFromCSV(input, SES.courseList);
                            errorMessage = null;
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                        break;
                    case "3":
                        System.out.println("Enter the path to your CSV file: ");
                        input = scanner.nextLine();
                        if (isValidPath(input) && input.endsWith(".csv")) {
                            readEnrollmentsFromCSV(input, SES.enrollmentList, SES.courseList, SES.studentList);
                            errorMessage = null;
                        } else {
                            errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                        }
                        break;
                    case "4":
                        break label;
                    default:
                        errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3' or '4'" + ANSI_RESET;
                        break;
                }
            }

        } else {
            SES.enrollmentList = readEnrollmentsFromCSV("src/student_enrollment_system/default.csv",
                    SES.enrollmentList, SES.courseList, SES.studentList);
        }

        System.out.println("Menu goes here");
    }

    public static void main (String[] args){
        StudentEnrollmentSystem SES = new StudentEnrollmentSystem();
        menu(SES);
//        SES.getAll();
//        for (Student student : SES.studentList) {
//            System.out.println(student);
//        }
    }

}
