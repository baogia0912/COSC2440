package student_enrollment_system;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

interface StudentEnrollmentManager{

    boolean add(StudentEnrollment SE);
    boolean update(StudentEnrollment oldEnrollment, StudentEnrollment newEnrollment);
    boolean delete(StudentEnrollment SE);
    StudentEnrollment getOne(String ID);
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

    public static final String defaultDatabasePath = "src/student_enrollment_system/default.csv";
    public static String databasePath = "src/student_enrollment_system/default.csv";

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
        return false;
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

    public static void saveCoursesToCSV(List<Course> courseList) {
        File file = new File("saved course list.csv");
        try {
            if (file.createNewFile()) {
                System.out.println(ANSI_PURPLE + "File created: " + file.getName() + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "File already exists." + ANSI_RESET);
                return;
            }
            PrintWriter outputFile = new PrintWriter(file);
            for (Course course : courseList) {
                outputFile.printf("%s,%s,%d\n", course.getCourseID(), course.getCourseName(), course.getCredit());
            }
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStudentsToCSV(List<Student> studentList) {
        File file = new File("saved student list.csv");
        try {
            if (file.createNewFile()) {
                System.out.println(ANSI_PURPLE + "File created: " + file.getName() + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "File already exists." + ANSI_RESET);
                return;
            }
            PrintWriter outputFile = new PrintWriter(file);
            for (Student student : studentList) {
                outputFile.printf("%s,%s,%s\n", student.getStudentID(), student.getStudentName(),
                        new SimpleDateFormat("MM/dd/yyyy").format(student.getBirthday()));
            }
            outputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEnrollmentsToCSV(List<StudentEnrollment> enrollmentList) {
        File file = new File("saved student list.csv");
        try {
            if (file.createNewFile()) {
                System.out.println(ANSI_PURPLE + "File created: " + file.getName() + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "File already exists." + ANSI_RESET);
                return;
            }
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static Course findCourse(List<Course> courseList, String courseID) {
        for (Course course : courseList) {
            if (course.getCourseID().equalsIgnoreCase(courseID)) {
                return course;
            }
        }
        return null;
    }

    public static Student findStudent(List<Student> studentList, String studentID) {
        for (Student student : studentList) {
            if (student.getStudentID().equalsIgnoreCase(studentID)) {
                return student;
            }
        }
        return null;
    }

    public static boolean isValidPath(String path) {
        try {
            Files.newBufferedReader(Paths.get(path), StandardCharsets.US_ASCII);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidStudentID(String studentID) {
        return Pattern.compile("S[0-9]{6}", Pattern.CASE_INSENSITIVE).matcher(studentID).find();
    }

    public static boolean isValidStudentAttr(String[] attributes) {
        if (!isValidStudentID(attributes[0])) return false;

        try {
            new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(attributes[2]);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidCourseID(String courseID) {
        return Pattern.compile("[A-Z]{3,4}[0-9]{4}", Pattern.CASE_INSENSITIVE).matcher(courseID).find();
    }

    public static boolean isValidCourseAttr(String[] attributes) {
        if (!isValidCourseID(attributes[0])) return false;

        try {
            Integer.parseInt(attributes[2]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isValidSemester(String semester) {
        return Pattern.compile("[0-9]{4}[A-C]", Pattern.CASE_INSENSITIVE).matcher(semester).find();
    }

    public static boolean isValidEnrollmentAttributes(String[] attributes) {
        if (!isValidSemester(attributes[6])) return false;
        if (!isValidStudentAttr(Arrays.copyOfRange(attributes, 0, 3))) return false;
        return isValidCourseAttr(Arrays.copyOfRange(attributes, 3, 6));
    }

    public static List<Course> findCoursesOfStudent(List<StudentEnrollment> enrollmentList, String studentID, String semester) {
        List<Course> queryList = new ArrayList<Course>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID) && SE.getSemester().equalsIgnoreCase(semester)
                    && !queryList.contains(SE.getCourse())) {
                queryList.add(SE.getCourse());
            }
        }
        return queryList;
    }

    public static List<Student> findStudentsOfCourse(List<StudentEnrollment> enrollmentList, String courseId, String semester) {
        List<Student> queryList = new ArrayList<Student>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getCourse().getCourseID().equalsIgnoreCase(courseId) && SE.getSemester().equalsIgnoreCase(semester)
                    && !queryList.contains(SE.getStudent())) {
                queryList.add(SE.getStudent());
            }
        }
        return queryList;
    }

    public static List<StudentEnrollment> findEnrollmentsOfStudent(List<StudentEnrollment> enrollmentList, String studentID) {
        List<StudentEnrollment> queryList = new ArrayList<StudentEnrollment>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getStudent().getStudentID().equalsIgnoreCase(studentID)) {
                queryList.add(SE);
            }
        }
        return queryList;
    }

    public static List<Course> findCourseInSemester(List<StudentEnrollment> enrollmentList, String semester) {
        List<Course> queryList = new ArrayList<Course>();
        for (StudentEnrollment SE : enrollmentList) {
            if (SE.getSemester().equalsIgnoreCase(semester) && !queryList.contains(SE.getCourse())) {
                queryList.add(SE.getCourse());
            }
        }
        return queryList;
    }

    private static Student createStudent(String[] metadata, List<Student> studentList) {
        String studentID = metadata[0];
        String studentName = metadata[1];
        Date birthday = null;
        try {
            birthday = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(metadata[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Student student = findStudent(studentList, studentID);
        if (student != null) {
            System.out.println(ANSI_GREEN + student.getStudentID()+ " - " + student.getStudentName() + ANSI_RESET
                    + " already exist and will not be added");
            return student;
        }

        Student newStudent = new Student(studentID, studentName, birthday);
        studentList.add(newStudent);
        return newStudent;
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
                    List<StudentEnrollment> enrollmentList, List<Course> courseList, List<Student> studentList) {
        Student student = createStudent(Arrays.copyOfRange(metadata, 0, 3), studentList);
        Course course = createCourse(Arrays.copyOfRange(metadata, 3, 6), courseList);
        String studentID = metadata[0];
        String courseID = metadata[3];
        String semester = metadata[6];

        StudentEnrollment SE = findEnrollment(enrollmentList, studentID, courseID, semester);
        if (SE != null) {
            System.out.println(ANSI_GREEN + SE + ANSI_RESET + " already exist and will not be added");
            return SE;
        }

        StudentEnrollment newSE = new StudentEnrollment(student, course, semester);
        enrollmentList.add(newSE);
        return newSE;
    }

    private static void readEnrollmentsFromCSV(String fileName,
                   List<StudentEnrollment> enrollmentList, List<Course> courseList, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (isValidEnrollmentAttributes(attributes) && attributes.length == 7) {
                    createEnrollment(attributes, enrollmentList, courseList, studentList);
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
        }
        if (input.equalsIgnoreCase("y")) {
            while (true) {
                if (errorMessage != null) {
                    System.out.println(errorMessage);
                    errorMessage = null;
                }
                System.out.println(ANSI_BLUE + "Enter your path: ");
                input = scanner.nextLine();
                if (isValidPath(input) && input.endsWith(".csv")) {
                    readEnrollmentsFromCSV(input, SES.enrollmentList, SES.courseList, SES.studentList);

                    databasePath = input;
                    break;
                } else {
                    errorMessage = ANSI_RED + "Invalid path!" + ANSI_RESET;
                }
            }
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
                        case "4" -> {
                        }
                        default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3' or '4'" + ANSI_RESET;
                    }
                }
            }
        } else {
            readEnrollmentsFromCSV(defaultDatabasePath, SES.enrollmentList, SES.courseList, SES.studentList);
            databasePath = defaultDatabasePath;
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
                            case "1" -> printStudents(SES.studentList);
                            case "2" -> printCourses(SES.courseList);
                            case "3" -> printEnrollment(SES.enrollmentList);
                            case "4" -> {}
                            default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', '3', or '4'" + ANSI_RESET;
                        }
                    }
                }
                case "2" -> {
                    System.out.println("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    if (!isValidStudentID(studentID) || findStudent(SES.studentList, studentID) == null) {
                        System.out.println(ANSI_RED + "Invalid student ID" + ANSI_RESET);
                    } else {
                        while (!input.equalsIgnoreCase("3")) {
                            if (errorMessage != null) {
                                System.out.println(errorMessage);
                                errorMessage = null;
                            }
                            System.out.println(ANSI_GREEN + "These are this student's enrollments" + ANSI_RESET);
                            printEnrollment(findEnrollmentsOfStudent(SES.enrollmentList, studentID));
                            System.out.println("1: Enroll a course | 2: Drop a course | 3: Back");
                            input = scanner.nextLine();
                            switch (input) {
                                case "1" -> {
                                    System.out.println(ANSI_GREEN + "These are the course list" + ANSI_RESET);
                                    printCourses(SES.courseList);
                                    System.out.println("Enter course ID: ");
                                    String courseID = scanner.nextLine();
                                    if (!isValidCourseID(courseID) || findCourse(SES.courseList, courseID) == null) {
                                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                        break;
                                    }
                                    System.out.println("Enter semester: ");
                                    String semester = scanner.nextLine();
                                    if (!isValidSemester(semester)) {
                                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                                        break;
                                    }
                                    StudentEnrollment SE = findEnrollment(SES.enrollmentList, studentID, courseID, semester);
                                    if (SE != null) {
                                        System.out.println(ANSI_GREEN + SE + ANSI_RESET + " already exist and will not be added");
                                        break;
                                    }
                                    SE = new StudentEnrollment(findStudent(SES.studentList, studentID), findCourse(SES.courseList, courseID), semester);
                                    if (SES.add(SE))
                                        System.out.println(ANSI_PURPLE + SE.getStudent().getStudentName() + " have been enrolled in " +
                                                SE.getCourse().getCourseName() + " in semester " + SE.getSemester() + ANSI_RESET);
                                    else System.out.println(ANSI_RED + "Failed to enrolled student!" + ANSI_RESET);
                                }
                                case "2" -> {
                                    System.out.println("Enter course ID: ");
                                    String courseID = scanner.nextLine();
                                    if (!isValidCourseID(courseID) || findCourse(SES.courseList, courseID) == null) {
                                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                                        break;
                                    }
                                    System.out.println("Enter semester: ");
                                    String semester = scanner.nextLine();
                                    if (!isValidSemester(semester)) {
                                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                                        break;
                                    }
                                    StudentEnrollment SE = findEnrollment(SES.enrollmentList, studentID, courseID, semester);
                                    if (SE == null) {
                                        System.out.println(ANSI_RED + "Enrollment does not exist!" + ANSI_RESET);
                                    } else if (SES.delete(SE))
                                        System.out.println(ANSI_PURPLE + SE.getStudent().getStudentName() + " have dropped " +
                                                SE.getCourse().getCourseName() + " in semester " + SE.getSemester() + ANSI_RESET);
                                    else System.out.println(ANSI_RED + "Failed to drop the course" + ANSI_RESET);
                                }
                                case "3" -> {}
                                default -> errorMessage = ANSI_RED + "Input error! Please only enter '1', '2', or '3'" + ANSI_RESET;
                            }
                        }
                    }
                }

                case "3" -> {
                    System.out.println("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    if (!isValidStudentID(studentID) || findStudent(SES.studentList, studentID) == null) {
                        errorMessage = ANSI_RED + "Invalid student ID" + ANSI_RESET;
                        break;
                    }
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    printCourses(findCoursesOfStudent(SES.enrollmentList, studentID, semester));
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
                        saveCoursesToCSV(findCoursesOfStudent(SES.enrollmentList, studentID, semester));
                    }
                }
                case "4" -> {
                    System.out.println("Enter course ID: ");
                    String courseID = scanner.nextLine();
                    if (!isValidCourseID(courseID) || findCourse(SES.courseList, courseID) == null) {
                        errorMessage = ANSI_RED + "Invalid course ID" + ANSI_RESET;
                        break;
                    }
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    printStudents(findStudentsOfCourse(SES.enrollmentList, courseID, semester));
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
                        saveStudentsToCSV(findStudentsOfCourse(SES.enrollmentList, courseID, semester));
                    }
                }
                case "5" -> {
                    System.out.println("Enter semester: ");
                    String semester = scanner.nextLine();
                    if (!isValidSemester(semester)) {
                        errorMessage = ANSI_RED + "Invalid semester" + ANSI_RESET;
                        break;
                    }
                    printCourses(findCourseInSemester(SES.enrollmentList, semester));
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
                        saveCoursesToCSV(findCourseInSemester(SES.enrollmentList, semester));
                    }
                }
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
