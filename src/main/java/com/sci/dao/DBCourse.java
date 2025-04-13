package com.sci.dao;

import com.sci.models.Course;
import org.hibernate.Session;

import java.util.List;

public class DBCourse {

    public List<Course> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.createQuery("FROM Course").list();
//            return session.createQuery("FROM Course", Course.class).list();

        }
    }
}
