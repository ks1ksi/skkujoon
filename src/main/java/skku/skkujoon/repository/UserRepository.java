package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

}
