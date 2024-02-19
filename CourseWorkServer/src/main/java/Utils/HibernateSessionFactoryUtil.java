package Utils;

import Entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static void BuildSessionFactory()
    {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Customer.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(OrderItem.class);
                configuration.addAnnotatedClass(Warehouse.class);
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}