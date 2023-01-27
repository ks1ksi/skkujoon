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
class ServiceTest {

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

        List<User> users = userService.findAll();
        List<Problem> problems = problemService.findProblems();

        assertEquals(100, users.size());
        assertEquals(100, problems.size());

        for (int i = 0; i < 100; i++) {
            Long userId = users.get(i).getId();
            Long problemId = problems.get(i).getId();
            userService.solveProblem(userId, problemId);

            User user = userService.findById(userId).get();
            Problem problem = problemService.findById(problemId).get();

            assertEquals(1, user.getUserProblems().size());
            assertEquals(1, problem.getUserProblems().size());
            assertEquals(1, problem.getSolvedBySkku());
            assertSame(problem, user.getUserProblems().get(0).getProblem());
        }
    }

    @Test
    @Transactional
    void solveProblemByProblemNumberTest() {
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

        List<User> users = userService.findAll();
        List<Problem> problems = problemService.findProblems();

        assertEquals(100, users.size());
        assertEquals(100, problems.size());

        for (int i = 0; i < 100; i++) {
            Long userId = users.get(i).getId();
            Long problemNumber = problems.get(i).getProblemNumber();
            userService.solveProblemByProblemNumber(userId, problemNumber);

            User user = userService.findById(userId).get();
            Problem problem = problemService.findByProblemNumber(problemNumber).get();

            assertEquals(1, user.getUserProblems().size());
            assertEquals(1, problem.getUserProblems().size());
            assertEquals(1, problem.getSolvedBySkku());
            assertSame(problem, user.getUserProblems().get(0).getProblem());
        }
    }

    @Test
    void countTest() {
        Long problemCount = problemService.countProblems();
        Long solvedProblemCount = problemService.countSolvedProblems();
        Long unsolvedProblemCount = problemService.countUnsolvedProblems();
        Long userCount = userService.countUser();
        System.out.println("problemCount = " + problemCount);
        System.out.println("solvedProblemCount = " + solvedProblemCount);
        System.out.println("unsolvedProblemCount = " + unsolvedProblemCount);
        System.out.println("userCount = " + userCount);
        assertEquals(problemCount, solvedProblemCount + unsolvedProblemCount);
    }

    @Test
    void randomProblemTest() {
        List<Problem> ramdomProblemList = problemService.findRandomUnsolvedProblems(6, 25, 7);
        for (Problem problem : ramdomProblemList) {
            System.out.println(problem.getProblemNumber() + " " + problem.getTitleKo());
        }
        assertEquals(7, ramdomProblemList.size());
        for (Problem problem : ramdomProblemList) {
            assertEquals(0, problem.getSolvedBySkku());
        }
    }

    @Test
     void userSolvedProblemTest() {
        User user = userService.findByHandle("ksi990302").get();
        List<Problem> userSolvedProblems = problemService.findUserSolvedProblems(user.getId());
        for (Problem p : userSolvedProblems) {
            System.out.println(p.getProblemNumber() + " " + p.getTitleKo());
        }
    }

    @Test
    void problemSolvedUserTest() {
        Problem problem = problemService.findByProblemNumber(25904L).get();
        List<User> problemSolvedUsers = userService.findProblemSolvedUser(problem.getId());
        for (User u : problemSolvedUsers) {
            System.out.println(u.getHandle() + " " + u.getRating());
        }
    }

    @Test
    void alreadySolvedProblemTest() {
        Problem problem = problemService.findByProblemNumber(25904L).get();
        User user = userService.findByHandle("ksi990302").get();
        userService.solveProblem(user.getId(), problem.getId());
    }
}