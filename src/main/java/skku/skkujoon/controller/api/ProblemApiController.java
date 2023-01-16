package skku.skkujoon.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.dto.ProblemDto;
import skku.skkujoon.service.ProblemService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProblemApiController {

    private final ProblemService problemService;

    @GetMapping("/api/v1/problems/unsolved")
    public List<ProblemDto> unsolvedProblems() {
        return problemService.findUnsolvedProblems().stream().map(
                p -> new ProblemDto(p.getProblemNumber(), p.getTitleKo(), p.getLevel(), p.getSolvedBySkku())
        ).collect(Collectors.toList());
    }

    @GetMapping("/api/v1/problems/unsolved/random")
    public List<ProblemDto> randomUnsolvedProblems(@RequestParam int limit) {
        List<Problem> randomUnsolvedProblems = problemService.findRandomUnsolvedProblems(limit);
        return randomUnsolvedProblems.stream().map(
                p -> new ProblemDto(p.getProblemNumber(), p.getTitleKo(), p.getLevel(), p.getSolvedBySkku())
        ).collect(Collectors.toList());
    }
}
