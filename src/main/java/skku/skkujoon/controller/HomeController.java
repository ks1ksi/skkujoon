package skku.skkujoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import skku.skkujoon.service.ProblemService;
import skku.skkujoon.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProblemService problemService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        Long solvedCount = problemService.countSolvedProblems();
        Long userCount = userService.countUser();
        model.addAttribute("solvedCount", solvedCount);
        model.addAttribute("userCount", userCount);
        return "home";
    }
}
