package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.Problem;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemRepository {

    private final EntityManager entityManager;

    public void save(Problem problem) {
        entityManager.persist(problem);
    }

    public Problem findById(Long id) {
        return entityManager.find(Problem.class, id);
    }

    public List<Problem> findAll() {
        return entityManager.createQuery("select p from Problem p", Problem.class).getResultList();
    }

}
