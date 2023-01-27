package skku.skkujoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public Problem addProblem(Problem problem) {
        return problemRepository.save(problem);
    }

    public Optional<Problem> findById(Long problemId) {
        return problemRepository.findById(problemId);
    }

    public Optional<Problem> findByProblemNumber(Long problemNumber) {
        return problemRepository.findByProblemNumber(problemNumber);
    }

    public List<Problem> findProblems() {
        return problemRepository.findAll();
    }

    public List<Problem> findProblems(int page, int limit) {
        return problemRepository.findAll(page, limit);
    }

    public List<Problem> findByLevel(int page, int limit, int level) {
        return problemRepository.findByLevel(page, limit, level);
    }

    public List<Problem> findUnsolvedProblems() {
        return problemRepository.findSkkuUnsolvedProblems();
    }

    public List<Problem> findRandomUnsolvedProblems(int min, int max, int limit) {
        return problemRepository.findRandomSkkuUnsolvedProblems(min, max, limit);
    }

    public List<Problem> findUserSolvedProblems(Long userId) {
        return problemRepository.findUserSolvedProblems(userId);
    }

    public Long countProblems() {
        return problemRepository.countProblems();
    }

    public Long countUnsolvedProblems() {
        return problemRepository.countSkkuUnsolvedProblems();
    }

    public Long countSolvedProblems() {
        return problemRepository.countSkkuSolvedProblems();
    }

}
