package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.Problem;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProblemRepository {

    private final EntityManager entityManager;

    public void save(Problem problem) {
        entityManager.persist(problem);
    }

    public Optional<Problem> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Problem.class, id));
    }

    public List<Problem> findAll() {
        return entityManager.createQuery("select p from Problem p", Problem.class).getResultList();
    }

    public Optional<Problem> findByProblemNumber(Long problemNumber) {
        List<Problem> resultList = entityManager.createQuery("select p from Problem p where p.problemNumber = :problemNumber", Problem.class)
                .setParameter("problemNumber", problemNumber).getResultList();
        return resultList.stream().findAny();
    }

    public List<Problem> findUnsolvedProblems() {
        return entityManager.createQuery("select p from Problem p where p.solvedBySkku = 0", Problem.class).getResultList();
    }

}
