package com.sci.dao;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.models.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sci.models.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DBStudent {

    public List<Student> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.createQuery("FROM Student").list();

//      return session.createSQLQuery("select * from hr.employees").addEntity(Employee.class).list();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public Student get(Integer studentId) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.get(Student.class, studentId);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


    public Integer insert(Student student) {

        Transaction transaction = null;
        int studentId = 0;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            studentId = (Integer) session.save(student);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }

        return studentId;
    }

    public void update(Student student) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

//            if (student == null) {
//                throw new IllegalArgumentException("Cannot update a null Student object.");
//            }

            transaction = session.beginTransaction();

            session.update(student);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
//            throw new RuntimeException("Failed to update Student: " + ex.getMessage(), ex);
        }
    }

    public void delete(Integer studentId) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

//            if (studentId == null) {
//                throw new IllegalArgumentException("Cannot delete a Student with a null ID.");
//            }

            transaction = session.beginTransaction();

            Student student = get(studentId);

            session.delete(student);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
//            throw new RuntimeException("Failed to delete Student: " + ex.getMessage(), ex);
        }
    }

    public void delete0(Integer studentId) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = DBConfig.SESSION_FACTORY.openSession();
            if (studentId == null) {
                throw new IllegalArgumentException("Cannot delete a Student with a null ID.");
            }
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
//            if (student == null) {
//                throw new IllegalArgumentException("Student with ID " + studentId + " not found, cannot delete.");
//            }
            session.delete(student);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error during delete: " + ex.getMessage());
            throw new RuntimeException("Failed to delete Student with ID " + studentId + ": " + ex.getMessage(), ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Student> getByFilter(List<FilterQuery> filterQueries) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Student> cr = cb.createQuery(Student.class);
            Root<Student> root = cr.from(Student.class);
//      cr.select(root);

            Predicate[] predicates = new Predicate[filterQueries.size()];
            for (int i = 0; i < filterQueries.size(); i++) {
                if (filterQueries.get(i).getOp() == Operator.EQ) {
                    predicates[i] = cb.equal(root.get(filterQueries.get(i).getAttributeName()),
                            filterQueries.get(i).getAttributeValue());
                } else if (filterQueries.get(i).getOp() == Operator.GT) {
                    predicates[i] = cb.gt(root.get(filterQueries.get(i).getAttributeName()),
                            (Integer) filterQueries.get(i).getAttributeValue());
                }
            }
            cr.select(root).where(predicates);

//      for (FilterQuery filterQuery : filterQueries) {
//        if (filterQuery.getOp() == Operator.EQ) {
//          cr.select(root).where(
//              cb.equal(root.get(filterQuery.getAttributeName()), filterQuery.getAttributeValue()));
//        } else if (filterQuery.getOp() == Operator.GT) {
//          cr.select(root).where(
//              cb.gt(root.get(filterQuery.getAttributeName()),
//                  (Integer) filterQuery.getAttributeValue()));
//        }
//      }
            Query<Student> query = session.createQuery(cr);
            return query.getResultList();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return new ArrayList<>();
    }

}

