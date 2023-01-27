package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ClearRepository {
    private final EntityManager entityManager;

    public void truncateUserProblem() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("truncate table user_problem").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }

    public void truncateUser() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("truncate table user").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }

    public void truncateProblem() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("truncate table problem").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }
}
