package DAO;

import Entities.OrderItem;
import Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OrderItemDAO {

    public static void AddOrderItem(OrderItem orderItem) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(orderItem);
        tx1.commit();
        session.close();
    }
}
