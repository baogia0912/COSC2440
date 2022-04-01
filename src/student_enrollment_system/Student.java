package student_enrollment_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Student {

    private String studentName;
    private String studentID;
    private Date birthday;

    public Student() {
        this.studentID = "s001";
        this.studentName = "Default";
        try {
            this.birthday = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("1/1/2000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Student(String studentID, String studentName, String birthday) {
        this.studentID = studentID;
        this.studentName = studentName;
        try {
            this.birthday = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    @Override
    public String toString() {
        return "studentID='" + studentID + '\'' +
                ", studentName='" + studentName + '\'' +
                ", birthday='" + new SimpleDateFormat("dd/MM/yyyy").format(birthday) + '\'';
    }
}
