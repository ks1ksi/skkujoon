package skku.skkujoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.repository.ProblemRepository;

import java.util.List;

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

    public Problem findProblemById(Long problemId) {
        return problemRepository.findById(problemId).orElseThrow(IllegalArgumentException::new);
    }

    public Problem findProblemByProblemNumber(Long problemNumber) {
        return problemRepository.findByProblemNumber(problemNumber).orElseThrow(IllegalArgumentException::new);
    }

    public List<Problem> findProblems() {
        return problemRepository.findAll();
    }

}
