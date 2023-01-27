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

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public List<User> findAll(int page, int limit) {
        return entityManager.createQuery("select u from User u order by u.ranking asc", User.class)
                .setFirstResult(page*limit)
                .setMaxResults(limit)
                .getResultList();
    }

    public Optional<User> findByHandle(String handle) {
        List<User> resultList = entityManager.createQuery("select u from User u where u.handle = :handle", User.class)
                .setParameter("handle", handle).getResultList();
        return resultList.stream().findAny();
    }

    public List<User> findProblemSolvedUser(Long problemId) {
        return entityManager.createQuery("select u from User u join u.userProblems up where up.problem.id = :problemId", User.class)
                .setParameter("problemId", problemId)
                .getResultList();
    }

    public boolean existsUserProblem(Long userId, Long problemId) {
        return entityManager.createQuery("select up from UserProblem up where up.user.id = :userId and up.problem.id = :problemId")
                .setParameter("userId", userId)
                .setParameter("problemId", problemId)
                .getResultList().stream().findFirst().isPresent();
    }

    public Long countUser() {
        return entityManager.createQuery("select count(*) from User u", Long.class).getSingleResult();
    }

}
