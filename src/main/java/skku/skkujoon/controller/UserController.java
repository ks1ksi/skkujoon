package skku.skkujoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import skku.skkujoon.domain.User;
import skku.skkujoon.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private static final int PAGE_SIZE = 50;

    @GetMapping("/users")
    public String users(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        List<User> users = userService.findUsers(page, PAGE_SIZE);
        int totalPage = (int) Math.ceil(1.0 * userService.countUser() / PAGE_SIZE);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        return "users";
    }
}
