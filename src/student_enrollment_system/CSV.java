package student_enrollment_system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSV {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    static void readStudentsFromCSV(String fileName, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (Validation.isValidStudentAttr(attributes) && attributes.length == 3) {
                    Student.createStudent(attributes, studentList);
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


    static void readCoursesFromCSV(String fileName, List<Course> courseList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (Validation.isValidCourseAttr(attributes) && attributes.length == 3) {
                    Course.createCourse(attributes, courseList);
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

    static void readEnrollmentsFromCSV(String fileName,
                                       List<StudentEnrollment> enrollmentList, List<Course> courseList, List<Student> studentList) {
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (Validation.isValidEnrollmentAttributes(attributes) && attributes.length == 7) {
                    StudentEnrollment.createEnrollment(attributes, enrollmentList, courseList, studentList);
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


    public static void saveCoursesToCSV(List<Course> courseList) {
        File file = new File("src/student_enrollment_system/csvFiles/saved course list.csv");
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
        File file = new File("src/student_enrollment_system/csvFiles/saved student list.csv");
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
}
