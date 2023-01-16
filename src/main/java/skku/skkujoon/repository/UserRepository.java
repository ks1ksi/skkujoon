package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    public Optional<User> findByHandle(String handle) {
        List<User> resultList = entityManager.createQuery("select u from User u where u.handle = :handle", User.class)
                .setParameter("handle", handle).getResultList();
        return resultList.stream().findAny();
    }

    public Long countUser() {
        return entityManager.createQuery("select count(*) from User u", Long.class).getSingleResult();
    }

}
