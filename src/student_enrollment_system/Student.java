package student_enrollment_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Student {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final String studentName;
    private final String studentID;
    private Date birthday;

    public Student() {
        this.studentID = "s001";
        this.studentName = "Default";
        try {
            this.birthday = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse("1/1/2000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Student(String studentID, String studentName, Date birthday) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.birthday = birthday;
    }

    public String getStudentName() {
        return studentName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getStudentID() {
        return studentID;
    }

    public static Student findStudent(List<Student> studentList, String studentID) {
        for (Student student : studentList) {
            if (student.getStudentID().equalsIgnoreCase(studentID)) {
                return student;
            }
        }
        return null;
    }

    static Student createStudent(String[] metadata, List<Student> studentList) {
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

    @Override
    public String toString() {
        return studentID + " - " + studentName +
                "(" + new SimpleDateFormat("MM/dd/yyyy").format(birthday) + ")";
    }
}
