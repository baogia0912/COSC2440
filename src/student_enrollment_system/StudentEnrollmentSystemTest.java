package student_enrollment_system;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

class StudentEnrollmentSystemTest {

    private static Path pathToFile;
    private static File file;
    private static Student testStudent;
    private static Course testCourse;
    private static String testSemester;
    StudentEnrollmentSystem SES;
    StudentEnrollment testSE;

    @BeforeAll
    public static void setupAll() {
        pathToFile = Paths.get("src/student_enrollment_system/TestingDatabase.csv");
        file = new File(pathToFile.toString());
        StudentEnrollmentSystem.databasePath = pathToFile.toString();

        try {
            testStudent = new Student("S3805912", "test student", new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse("09/11/1999"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        testCourse = new Course("COSC2440", "Further Programming", 4);
        testSemester = "2022A";
    }

    @BeforeEach
    public void setup() {
        SES = new StudentEnrollmentSystem();
        testSE = new StudentEnrollment(testStudent, testCourse, testSemester);
    }

    @Test
    void add() {
        SES.add(testSE);

        Assertions.assertEquals(1, SES.enrollmentList.size());
        Assertions.assertFalse(SES.getAll().isEmpty());
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            Integer fileSize = 0;
            while (line != null) {
                Assertions.assertEquals("S3805912,test student,09/11/1999,COSC2440,Further Programming,4,2022A", line);
                fileSize++;
                line = br.readLine();
            }

            Assertions.assertEquals(1, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        SES.add(testSE);
        Course newCourse = new Course("COSC1234", "fake course", 4);
        SES.update(testSE, new StudentEnrollment(testStudent, newCourse, "2022B"));

        Assertions.assertEquals(1, SES.enrollmentList.size());
        Assertions.assertFalse(SES.getAll().isEmpty());
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            Integer fileSize = 0;
            while (line != null) {
                Assertions.assertEquals("S3805912,test student,09/11/1999,COSC1234,fake course,4,2022B", line);
                fileSize++;
                line = br.readLine();
            }
            Assertions.assertEquals(1, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        SES.add(testSE);
        SES.delete(testSE);

        Assertions.assertEquals(0, SES.enrollmentList.size());
        Assertions.assertTrue(SES.getAll().isEmpty());
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            Integer fileSize = 0;
            while (line != null) {
                Assertions.assertEquals("", line);
                fileSize++;
                line = br.readLine();
            }
            Assertions.assertEquals(0, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOne() {
        SES.add(testSE);
        Assertions.assertEquals(testSE, SES.getOne(testStudent.getStudentID()));
        Assertions.assertNull(SES.getOne("s3805911"));
    }

    @Test
    void getAll() {
        Assertions.assertEquals(SES.enrollmentList, SES.getAll());
    }

    @Test
    void isValidStudentID() {
        Assertions.assertTrue(StudentEnrollmentSystem.isValidStudentID("s3805912"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidStudentID("s38059122"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidStudentID("s38052"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidStudentID("s380591"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidStudentID("S0000000"));
    }

    @Test
    void isValidCourseID() {
        Assertions.assertTrue(StudentEnrollmentSystem.isValidCourseID("QWER5912"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidCourseID("WDAW59122"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidCourseID("WQDSS8052"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidCourseID("WS8052"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidCourseID("AQW0591"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidCourseID("ACDA0000"));
    }

    @Test
    void isValidSemester() {
        Assertions.assertFalse(StudentEnrollmentSystem.isValidSemester("4123D"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidSemester("1232c"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidSemester("123A"));
        Assertions.assertFalse(StudentEnrollmentSystem.isValidSemester("12345B"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidSemester("1234C"));
        Assertions.assertTrue(StudentEnrollmentSystem.isValidSemester("0000A"));
    }

    @AfterEach
    public void tearDown() {
        try {
            FileWriter fw = new FileWriter(file,false);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}