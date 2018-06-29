package dao;
import java.util.List;


import pojo.Student;

public interface StudentDao {
        Student getStudentById(int i);
        Student getStudentByName(String name);
        boolean addStudent(Student user);
        boolean updataStudent(Student user);
        boolean uodatePortrait(int i);
        boolean deleteStudent(int UserId);
        List<Student> allStudent();
    }
