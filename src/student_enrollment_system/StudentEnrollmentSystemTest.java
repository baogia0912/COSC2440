package student_enrollment_system;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

class StudentEnrollmentSystemTest {

    private static Path pathToFile;
    private static File file;
    StudentEnrollmentSystem SES;

    @BeforeAll
    public static void setupAll() {
        pathToFile = Paths.get("src/student_enrollment_system/csvFiles/TestingDatabase.csv");
        file = new File(pathToFile.toString());
        StudentEnrollmentSystem.databasePath = pathToFile.toString();
    }

    @BeforeEach
    public void setup() {
        SES = new StudentEnrollmentSystem();
        CSV.readEnrollmentsFromCSV("src/student_enrollment_system/csvFiles/BackupTestingDatabase.csv",
                SES.enrollmentList, SES.courseList, SES.studentList);
        try {
            PrintWriter outputFile = new PrintWriter(file);
            for (StudentEnrollment SE : SES.enrollmentList) {
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

    @Test
    void add() {
        SES.add(new StudentEnrollment(Student.findStudent(SES.studentList,"S0000001"),
                Course.findCourse(SES.courseList, "COSC2444"), "2022A"));

        Assertions.assertEquals(16, SES.enrollmentList.size());
        Assertions.assertFalse(SES.getAll().isEmpty());
        String line, lastLine = "";
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            Integer fileSize = 0;
            while ((line = br.readLine()) != null) {
                lastLine = line;
                fileSize++;
            }
            Assertions.assertEquals("S0000001,test student1,09/11/1999,COSC2444,test course4,4,2022A", lastLine);
            Assertions.assertEquals(16, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void update() {
        SES.update(StudentEnrollment.findEnrollment(SES.enrollmentList, "S0000001", "COSC2441", "2022A"),
                new StudentEnrollment(Student.findStudent(SES.studentList,"S0000001"),
                Course.findCourse(SES.courseList, "COSC2444"), "2022A"));

        Assertions.assertEquals(15, SES.enrollmentList.size());
        Assertions.assertFalse(SES.getAll().isEmpty());
        String line, lastLine = "";
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            Integer fileSize = 0;
            while ((line = br.readLine()) != null) {
                lastLine = line;
                fileSize++;
            }
            Assertions.assertEquals("S0000001,test student1,09/11/1999,COSC2444,test course4,4,2022A", lastLine);
            Assertions.assertEquals(15, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void delete() {
        SES.delete(StudentEnrollment.findEnrollment(SES.enrollmentList, "s0000001", "COSC2442", "2022B"));

        Assertions.assertEquals(14, SES.enrollmentList.size());
        Assertions.assertFalse(SES.getAll().isEmpty());
        Assertions.assertNull(StudentEnrollment.findEnrollment(SES.enrollmentList, "s0000001", "COSC2442", "2022B"));
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            Integer fileSize = 0;
            while (line != null) {
                fileSize++;
                line = br.readLine();
            }
            Assertions.assertEquals(14, fileSize);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getOne() {
        Assertions.assertEquals(StudentEnrollment.findEnrollment(SES.enrollmentList, "S0000001", "COSC2441", "2022A"), SES.getOne("S0000001"));
        Assertions.assertNull(SES.getOne("s3805912"));
    }

    @Test
    void getAll() {
        Assertions.assertEquals(SES.enrollmentList, SES.getAll());
    }


    @Test
    void findCoursesOfStudent() {
        Assertions.assertEquals("[COSC2441 - test course1(4cr)]", StudentEnrollmentSystem.findCoursesOfStudent(SES.enrollmentList, "S0000001", "2022A").toString());
        Assertions.assertTrue(StudentEnrollmentSystem.findCoursesOfStudent(SES.enrollmentList, "S0000006", "2022A").isEmpty());
    }

    @Test
    void findStudentsOfCourse() {
        Assertions.assertEquals("[S0000001 - test student1(09/11/1999)]", StudentEnrollmentSystem.findStudentsOfCourse(SES.enrollmentList, "COSC2441", "2022A").toString());
        Assertions.assertTrue(StudentEnrollmentSystem.findStudentsOfCourse(SES.enrollmentList, "COSC2440", "2022A").isEmpty());
    }

    @Test
    void findCourseInSemester() {
        Assertions.assertEquals("[COSC2441 - test course1(4cr), COSC2442 - test course2(4cr), COSC2443 - test course3(4cr), COSC2444 - test course4(4cr), COSC2445 - test course5(4cr)]",
                StudentEnrollmentSystem.findCourseInSemester(SES.enrollmentList, "2022A").toString());
        Assertions.assertTrue(StudentEnrollmentSystem.findCourseInSemester(SES.enrollmentList, "2022D").isEmpty());
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