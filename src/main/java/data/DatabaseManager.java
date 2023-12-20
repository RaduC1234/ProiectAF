package data;

import jakarta.persistence.EntityManager;

public class DatabaseManager {

    //private final static Logger logger = LogManager.getLogger(DatabaseManager.class);
    private static DatabaseManager instance = new DatabaseManager();

    private DatabaseManager() {
    }

    public static synchronized DatabaseManager getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return new UserService(getEntityManager());
    }


    public synchronized EntityManager getEntityManager() {
        return HibernateUtil.getSessionFactory().openSession().getEntityManagerFactory().createEntityManager();
    }

}
