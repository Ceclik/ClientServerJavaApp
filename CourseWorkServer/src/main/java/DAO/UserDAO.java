package DAO;

import Entities.User;
import Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {

    public static void AddUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public static void UpdateUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public static User FindUserById(long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    public static boolean AuthenticateUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE login = :login AND password = :password", User.class);
        query.setParameter("login", user.getLogin());
        query.setParameter("password", user.getPassword());

        User foundUser = query.uniqueResult();

        transaction.commit();
        session.close();
        return foundUser != null;
    }

    public static User GetUserByBasicData(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();

        Query<User> query = session.createQuery("FROM User WHERE login = :login AND password = :password", User.class);
        query.setParameter("login", user.getLogin());
        query.setParameter("password", user.getPassword());

        User foundUser = query.uniqueResult();

        transaction.commit();
        session.close();
        return foundUser;
    }

    public static void DeleteUser(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public static List<User> GetAllUsers() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }
}
