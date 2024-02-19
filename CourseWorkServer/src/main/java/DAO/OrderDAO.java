package DAO;

import Entities.Order;
import Entities.User;
import Utils.HibernateSessionFactoryUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO {

    public static Long AddOrder(Order order) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Long generatedId = (Long)session.save(order);
        tx1.commit();
        session.close();
        return generatedId;
    }

    public static List<Order> GetAllOrdersOfCustomer(Long customerId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);
        criteria.select(root).where(builder.equal(root.get("customer_id"), customerId));
        Query<Order> query = session.createQuery(criteria);
        List<Order> resultList = query.getResultList();
        session.close();
        return resultList;
    }

    public static void DeleteAllOrdersOfCustomer(Long customerId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            String hql = "DELETE FROM Order WHERE customer_id = :customerId";
            Query query = session.createQuery(hql);
            query.setParameter("customerId", customerId);

            int rowCount = query.executeUpdate();
            System.out.println("Rows affected: " + rowCount);
            tx.commit();
        }catch (Exception e){
            if(tx != null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public static List<Order> GetAllOrders(){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Order> query = session.createQuery("FROM Order ", Order.class);
            return query.list();
        }
    }
}
