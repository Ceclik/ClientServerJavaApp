package DAO;

import Entities.Customer;
import Entities.User;
import Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CustomerDAO {

    public static Customer GetCustomerByBasicData(String name, String email) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();

        Query<Customer> query = session.createQuery("FROM Customer WHERE name = :name AND email = :email", Customer.class);
        query.setParameter("name", name);
        query.setParameter("email", email);
        Customer foundCustomer = query.uniqueResult();

        transaction.commit();
        session.close();
        return foundCustomer;
    }

    public static Customer GetCustomerById(Long id){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Customer.class, id);
    }
}
