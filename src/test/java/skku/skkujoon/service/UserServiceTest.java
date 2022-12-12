package skku.skkujoon.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import skku.skkujoon.domain.Problem;
import skku.skkujoon.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired ProblemService problemService;

    @Test
    @Transactional
    @Rollback(value = false)
    void solveProblemTest() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setHandle("user" + i);
            user.setBio("bio" + i);
            user.setTier(10);
            user.setRating(1000 + i);

            userService.addUser(user);
        }

        for (int i = 0; i < 100; i++) {
            Problem problem = new Problem();
            problem.setProblemNumber(30000L + i);
            problem.setLevel(20);
            problem.setTitleKo("problem" + i);
            problem.setSolvable(true);
            problem.setPartial(false);

            problemService.addProblem(problem);
        }

        List<User> users = userService.findUsers();
        List<Problem> problems = problemService.findProblems();

        assertEquals(100, users.size());
        assertEquals(100, problems.size());

        for (int i = 0; i < 100; i++) {
            Long userId = users.get(i).getId();
            Long problemId = problems.get(i).getId();
            userService.solveProblem(userId, problemId);

            User user = userService.findUserById(userId);
            Problem problem = problemService.findProblemById(problemId);

            assertEquals(1, user.getUserProblems().size());
            assertEquals(1, problem.getUserProblems().size());
            assertEquals(1, problem.getSolvedBySkku());
            assertSame(problem, user.getUserProblems().get(0).getProblem());
        }
    }
}