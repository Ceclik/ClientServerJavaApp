package DAO;

import ClientHandlers.ProductsHandler;
import Entities.Product;
import Entities.Warehouse;
import Utils.HibernateSessionFactoryUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO {

    public static List<Product> GetAllProducts() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        }
    }

    public static List<Product> getProductsByWarehouseId(Long warehouseId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            var builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
            Root<Product> root = criteriaQuery.from(Product.class);
            criteriaQuery.select(root).where(builder.equal(root.get("warehouseId"), warehouseId));

            Query<Product> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }

    public static void AddProduct(Product product){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(product);
        tx1.commit();
        session.close();
    }

    public static Product GetById(Long productId){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Product product = session.get(Product.class, productId);
        session.close();
        return product;
    }

    public static void UpdateAmount(Long productId, int amountOfNewProduct, String purpose){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Product product = session.get(Product.class, productId);
        if(purpose.equals("order"))
            product.setAmountInWarehouse(product.getAmountInWarehouse() - amountOfNewProduct);
        else {
            product.setAmountInWarehouse(product.getAmountInWarehouse() + amountOfNewProduct);
            WarehouseDAO.UpdateCapacity(product.getWarehouseId(), amountOfNewProduct, "add");
        }
        session.update(product);
        tx1.commit();
        session.close();
    }

    public static Product GetProductByName(String name){
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
            Root<Product> root = criteria.from(Product.class);
            criteria.select(root).where(builder.equal(builder.lower(root.get("name")), name.toLowerCase()));
            Query<Product> query = session.createQuery(criteria);
            return query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

}
