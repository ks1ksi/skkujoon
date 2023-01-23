package skku.skkujoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.service.ProblemService;
import skku.skkujoon.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProblemService problemService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        Long solvedCount = problemService.countSolvedProblems();
        Long userCount = userService.countUser();
        List<Problem> randomUnsolvedProblems = problemService.findRandomUnsolvedProblems(6, 25, 7);
        model.addAttribute("solvedCount", solvedCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("randomProblems", randomUnsolvedProblems);
        return "home";
    }
}
