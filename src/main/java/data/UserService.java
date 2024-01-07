package data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


import java.util.List;

@AllArgsConstructor
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private EntityManager entityManager;

    public void addUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }


    public void update(User element) {
        entityManager.getTransaction().begin();
        entityManager.merge(element);
        entityManager.getTransaction().commit();
    }


    public boolean deleteById(Long id) throws Exception {
        User byId = findById(id);

        if (byId == null)
            return false;

        entityManager.getTransaction().begin();
        entityManager.remove(byId);
        entityManager.getTransaction().commit();
        return true;
    }

    public User findById(Long id) throws Exception {
        try {
            return entityManager.find(User.class, id);
        } catch (NoResultException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }
}
