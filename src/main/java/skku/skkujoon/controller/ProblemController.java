package skku.skkujoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.service.ProblemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemService problemService;

    private static final int PAGE_SIZE = 100;

    @GetMapping("/problems")
    public String problems(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Problem> problems = problemService.findProblems(page, PAGE_SIZE);
        int totalPage = (int) Math.ceil(1.0 * problemService.countProblems() / PAGE_SIZE);
        model.addAttribute("problems", problems);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "problems";
    }
}
