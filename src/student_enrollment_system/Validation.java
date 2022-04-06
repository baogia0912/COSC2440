package student_enrollment_system;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidPath(String path) {
        try {
            Files.newBufferedReader(Paths.get(path), StandardCharsets.US_ASCII);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isValidStudentID(String studentID) {
        return Pattern.compile("^S[0-9]{6,7}$", Pattern.CASE_INSENSITIVE).matcher(studentID).find();
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
        return Pattern.compile("^[A-Z]{3,4}[0-9]{4}$", Pattern.CASE_INSENSITIVE).matcher(courseID).find();
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
        return Pattern.compile("^[0-9]{4}[A-C]$", Pattern.CASE_INSENSITIVE).matcher(semester).find();
    }

    public static boolean isValidEnrollmentAttributes(String[] attributes) {
        if (!isValidSemester(attributes[6])) return false;
        if (!isValidStudentAttr(Arrays.copyOfRange(attributes, 0, 3))) return false;
        return isValidCourseAttr(Arrays.copyOfRange(attributes, 3, 6));
    }
}
