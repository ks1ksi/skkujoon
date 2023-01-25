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

    public List<Problem> findSkkuUnsolvedProblems() {
        return entityManager.createQuery("select p from Problem p where p.solvedBySkku = 0", Problem.class).getResultList();
    }

    public List<Problem> findRandomSkkuUnsolvedProblems(int min, int max, int limit) {
        String sql = "select p.problem_id, p.level, p.problem_number, p.solved_by_skku, p.title_ko, p.partial, p.solvable from problem p where p.solved_by_skku = 0 and p.level between :min and :max order by rand() limit :limit";
        return (List<Problem>) entityManager.createNativeQuery(sql, Problem.class)
                .setParameter("min", min)
                .setParameter("max", max)
                .setParameter("limit", limit).getResultList();
    }

    public List<Problem> findUserSolvedProblems(Long userId) {
        return entityManager.createQuery("select p from Problem p join p.userProblems up where up.user.id = :userId", Problem.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Long countProblems() {
        return entityManager.createQuery("select count(*) from Problem p", Long.class).getSingleResult();
    }

    public Long countSkkuSolvedProblems() {
        return entityManager.createQuery("select count(*) from Problem p where p.solvedBySkku >= 1", Long.class).getSingleResult();
    }

    public Long countSkkuUnsolvedProblems() {
        return entityManager.createQuery("select count(*) from Problem p where p.solvedBySkku = 0", Long.class).getSingleResult();
    }

}
