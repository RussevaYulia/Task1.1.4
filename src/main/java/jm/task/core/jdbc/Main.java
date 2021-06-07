package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService1 = new UserServiceImpl();

        userService1.cleanUsersTable();
        userService1.saveUser("Mary", "Brown", (byte) 56);
        userService1.saveUser("Steve", "Adamson", (byte) 25);
        userService1.saveUser("Smite", "Walter", (byte) 45);
        System.out.println(userService1.getAllUsers());



        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        UserServiceImpl userService2 = new UserServiceImpl();
        userService2.createUsersTable();
        userService2.saveUser("Ramesh", "Flatware", (byte) 25);
        userService2.saveUser("Steve", "Flatware", (byte) 37);
        userService2.saveUser("Smite", "Walter", (byte) 45);
        System.out.println(userService2.getAllUsers());
        userService2.removeUserById(1);
        System.out.println(userService2.getAllUsers());


    }
}
