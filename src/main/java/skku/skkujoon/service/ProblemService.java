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

    public boolean isDuplicate(Problem problem) {
        return problemRepository.findByProblemNumber(problem.getProblemNumber()).isPresent();
    }

    @Transactional
    public void addProblem(Problem problem) {
        problemRepository.save(problem);
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

    public List<Problem> findUnsolvedProblems() {
        return problemRepository.findUnsolvedProblems();
    }

    public List<Problem> findRandomUnsolvedProblems(int limit) {
        return problemRepository.findRandomUnsolvedProblems(limit);
    }

    public Long countProblems() {
        return problemRepository.countProblems();
    }

    public Long countUnsolvedProblems() {
        return problemRepository.countUnsolvedProblems();
    }

    public Long countSolvedProblems() {
        return problemRepository.countSolvedProblems();
    }

}
