package skku.skkujoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.repository.ProblemRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;


    public boolean isDuplicate(Problem problem) {
        return problemRepository.findByProblemNumber(problem.getProblemNumber()).isPresent();
    }

}
