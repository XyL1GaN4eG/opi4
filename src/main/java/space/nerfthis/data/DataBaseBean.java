package space.nerfthis.data;

import org.hibernate.SessionFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "dataBase", eager = true)
@SessionScoped
public class DataBaseBean {
    private String result;
    private SessionFactory sessionFactory;
    private List<Point> points;

    public DataBaseBean() {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Инициализация SessionFactory завершилась неудачей: " + ex);
            throw new RuntimeException(ex);
        }
    }


    public String addPoint(Point point) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(point);
            transaction.commit();
            return "added " + point;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "error";
        }
    }


    public List<Point> getPoints() {
        return points;
    }

    public void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public String getAllPoints() {
        try (Session session = sessionFactory.openSession()) {
            points = session.createQuery("FROM Point", Point.class).getResultList();
            result = "goToTablePage";
            return result;
        }

    }
}
