package skku.skkujoon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import skku.skkujoon.domain.Problem;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    public List<Problem> findRandomUnsolvedProblems(int limit) {
        String sql = "select p.problem_id, p.level, p.problem_number, p.solved_by_skku, p.title_ko, p.partial, p.solvable from problem p where solved_by_skku = 0 order by rand() limit ?";
        Query nativeQuery = entityManager.createNativeQuery(sql, Problem.class).setParameter(1, limit);
        List<Problem> resultList = new ArrayList<>();
        for (Object o : nativeQuery.getResultList()) {
            Problem p = (Problem) o;
            resultList.add(p);
        }
        return resultList;
    }

    public Long countProblems() {
        return entityManager.createQuery("select count(*) from Problem p", Long.class).getSingleResult();
    }

    public Long countSolvedProblems() {
        return entityManager.createQuery("select count(*) from Problem p where p.solvedBySkku >= 1", Long.class).getSingleResult();
    }

    public Long countUnsolvedProblems() {
        return entityManager.createQuery("select count(*) from Problem p where p.solvedBySkku = 0", Long.class).getSingleResult();
    }

}
