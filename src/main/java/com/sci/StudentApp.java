package com.sci;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.dao.DBConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sci.dao.DBCourse;
import com.sci.models.Course;
import com.sci.models.Student;
import com.sci.dao.DBStudent;
import org.hibernate.Session;

public class StudentApp {

    public static void testCache1() {

        System.out.println("Test cache scenario 1");

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            System.out.println(session.get(Student.class, 1));

            System.out.println("--------------------------------");

            System.out.println(session.get(Student.class, 2));

            System.out.println("--------------------------------");

            System.out.println(session.get(Student.class, 3));

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void testCache2() {

        System.out.println("Test cache scenario 2");

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            System.out.println(session.get(Student.class, 103));

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {

//    testCache1();
//
//    System.out.println("*****************************************");
//
//    testCache2();
//
//    System.out.println("*****************************************");
//
//    testCache2();

        DBStudent dbStudent = new DBStudent();
//
        List<FilterQuery> filterQueries = new ArrayList<>();
//        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalDate date = LocalDate.now();
        filterQueries.add(new FilterQuery("studentId", 0, Operator.GT)); // get students from Cairo
        filterQueries.add(new FilterQuery("studentName", "Pogba", Operator.EQ));
        filterQueries.add(new FilterQuery("joinedDate", date, Operator.LT));

        List<Student> studentList = dbStudent.getByFilter(filterQueries);
        System.out.println("the size of the list = " + studentList.size());
        for(Student student : studentList) {
            System.out.println(student);
        }

//    List<Student> studentList = dbStudent.get();
//    List<Course> courseList = new DBCourse().get();

//    for(Course course : courseList) {
//      System.out.println(course);
//    }

//    for(Student e : studentList) {
//      System.out.println(e);
//    }

//    System.out.println(dbStudent.get(103));

//    Student student = new Student();
//    student.setStudentName("amir");
//    student.setStudentAddress("Cairo");
////    student.setStudentId(100);
////    student.setStudentAge(0);
////    student.setJoinedDate(new Date());
////
//    System.out.println(dbStudent.insert(student));

//    Student student = dbStudent.get(21);
////
//    student.setJoinedDate(new Date());
//    student.setStudentName("Ali");
//
//    dbStudent.update(student);
//    System.out.println(dbStudent.get(103));


//    dbStudent.delete(21);

    DBConfig.shutdown();

    }
}
