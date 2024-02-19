package DAO;

import Entities.Warehouse;
import Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class WarehouseDAO {

    public static List<Warehouse> GetAllWarehouses(){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Query<Warehouse> query = session.createQuery("FROM Warehouse ", Warehouse.class);
            return query.list();
        }
    }

    public static void AddWarehosue(Warehouse warehouse){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(warehouse);
        tx1.commit();
        session.close();
    }

    public static void UpdateCapacity(Long warehouseId, int amountOfProduct, String purpose){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Warehouse warehouse = session.get(Warehouse.class, warehouseId);
        if(purpose.equals("add"))
            warehouse.setCapacity(warehouse.getCapacity() - amountOfProduct);
        else
            warehouse.setCapacity(warehouse.getCapacity() + amountOfProduct);
        session.update(warehouse);
        tx1.commit();
        session.close();
    }
}
