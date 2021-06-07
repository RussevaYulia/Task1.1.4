package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        Session session = getSessionFactory().openSession();
        String createTable = "CREATE TABLE IF NOT EXISTS `usersdb`.`user` (\n" +
                "`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "`name` VARCHAR(45) NOT NULL,\n" +
                "`lastName` VARCHAR(45) NOT NULL,\n" +
                "`age` INT NOT NULL,\n" +
                "PRIMARY KEY  (`id`));";
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery(createTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Creat failed");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String dropTable = "DROP TABLE if exists usersdb.user";
        Query query = session.createSQLQuery(dropTable).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Save failed");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :id").setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("remove failed");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Transaction transaction = null;
        Session session = getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            allUsers = session.createQuery("from User order by name").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("GetAll failed");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = getSessionFactory().openSession();
        String cleanTable = "delete User";
        try {
            transaction = session.beginTransaction();
            session.createQuery(cleanTable).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("GetAll failed");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}