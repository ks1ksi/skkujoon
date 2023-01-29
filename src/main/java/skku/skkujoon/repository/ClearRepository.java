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

    public void deleteUserProblem() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("delete from user_problem").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }

    public void deleteUser() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("delete from user").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }

    public void deleteProblem() {
        entityManager.createNativeQuery("set foreign_key_checks = 0").executeUpdate();
        entityManager.createNativeQuery("delete from problem").executeUpdate();
        entityManager.createNativeQuery("set foreign_key_checks = 1").executeUpdate();
    }
}
