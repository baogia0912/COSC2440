package student_enrollment_system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

interface StudentEnrollmentManager{
    public boolean add();
    public boolean update();
    public boolean delete();
    public boolean getOne();
    public boolean getAll();
}

public class StudentEnrollmentSystem implements StudentEnrollmentManager {
    public static void main (String args[]){

        List<StudentEnrollment> enrollmentList = new ArrayList<StudentEnrollment>();
        Student bao = new Student("s3805912", "Ha Gia Bao", "11/9/1999");
        Course fp = new Course("ABCD123", "Further programing", 4);
        StudentEnrollment a1 = new StudentEnrollment(bao, fp, "2022A");
        enrollmentList.add(a1);
        enrollmentList.add(a1);
        enrollmentList.add(a1);
        for (StudentEnrollment i : enrollmentList) {
            System.out.println(i);
        }
        enrollmentList.add(1);
    }

    @Override
    public boolean add() {

        return false;
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }

    @Override
    public boolean getOne() {
        return false;
    }

    @Override
    public boolean getAll() {
        return false;
    }
}
